/*  Elevator Project
*   CSCI 381
*
*
*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Elevator implements Runnable {
	
	//Class representing an elevator and its behavior 
	
	private int elevatorID; 
	private int currentFloor;  
	private int numPassengers; 
	private int totalLoadedPassengers; 
	private int totalUnloadedPassengers; 
	private ArrayList<FutureEvent> moveQueue;
	private int[] passengerDestinations; // array of integers 
	private BuildingManager manager;

	public Elevator(int elevatorID, BuildingManager manager) { 
		this.elevatorID = elevatorID;
		this.manager = manager;
		this.currentFloor = 0;
		this.numPassengers = 0;
		this.totalLoadedPassengers = 0;
		this.totalUnloadedPassengers =0;
		this.moveQueue = new ArrayList<>();
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public int getTotalLoadedPassengers() {
		return totalLoadedPassengers;
	}

	public ArrayList<FutureEvent> getMoveQueue() {
		return this.moveQueue;
	}

	public int getTotalUnloadedPassengers() {
		return totalUnloadedPassengers;
	}

	public void increaseTotalLoadedPassengers(int newLoaded) {
		this.totalLoadedPassengers += newLoaded;
	}

	public void increaseTotalUnloadedPassengers(int newUnloadedPassengers) {
		this.totalUnloadedPassengers += newUnloadedPassengers;
	}

	public void addToMoveQueue(FutureEvent event) {
		if (event != null) {
			this.moveQueue.add(event);
		}
	}
	public void addToMoveQueue(List<FutureEvent> events) {
		this.moveQueue.addAll(events);
	}

	public FutureEvent loadedPassenger(int[] requests) {
		List<FutureEvent> newEvents = new ArrayList<>();
		int timeToLoad = 0;
		int totalNewPassenger = 0;
		for (int i = this.currentFloor; i < 5; i ++) {
			if (requests[i] != 0) {
				newEvents.add(new FutureEvent(i, SimulationClock.getTime() + Math.abs(i - this.currentFloor) * 5 + timeToLoad));
				timeToLoad += 10;
				totalNewPassenger += requests[i];
				System.out.println(String.format("At %s seconds, loading %s passengers on elevator %s at floor %s ", SimulationClock.getTime(), totalNewPassenger, this.elevatorID, i));
				System.out.println(String.format("Waiting Time -  At %s seconds, on elevator %s at floor %s, expected time of arrival is %s seconds", SimulationClock.getTime(),
		        		this.elevatorID, this.currentFloor, SimulationClock.getTime() + Math.abs(i - this.currentFloor) * 5 + timeToLoad));
				
			}
		}
		
		if (newEvents.isEmpty()) {
			for (int i = currentFloor - 1; i >= 0; i --) {
				if (requests[i] != 0) {
					newEvents.add(new FutureEvent(i, SimulationClock.getTime() + Math.abs((i - currentFloor)) * 5 + timeToLoad));
					timeToLoad += 10;
					totalNewPassenger += requests[i];
					System.out.println(String.format("At %s seconds, loading %s passengers on elevator %s at floor %s ", SimulationClock.getTime(), totalNewPassenger, this.elevatorID, i));
					System.out.println(String.format("Waiting Time -  At %s seconds, on elevator %s at floor %s, expected time of arrival is %s seconds", SimulationClock.getTime(),
			        		this.elevatorID, this.currentFloor, (SimulationClock.getTime() + Math.abs(i - currentFloor) * 5 + timeToLoad)));
				}
			}
		}
		this.addToMoveQueue(newEvents);
		
		
		this.passengerDestinations = requests;
		this.numPassengers = totalNewPassenger;
		this.increaseTotalLoadedPassengers(totalNewPassenger);
		return newEvents.get(0);
	}

	public void unloadedPassenger(int floor) {
		System.out.println(String.format("At %s seconds, unloading %s passengers on elevator %s at floor %s ", SimulationClock.getTime(), this.passengerDestinations[floor],this.elevatorID, floor));
		this.moveQueue.remove(0);
		this.increaseTotalUnloadedPassengers(passengerDestinations[floor]);
		this.numPassengers -= passengerDestinations[floor];
		this.passengerDestinations[floor] = 0;
	}

	public BuildingManager getBuildingManager(){
		return this.manager;
	}

	@Override
	public void run() {

		while(true) {
			if (this.getNumPassengers() == 0 && this.getMoveQueue().isEmpty()) {
				// empty so get next event
				FutureEvent event = this.getBuildingManager().getNextRequest(this.getCurrentFloor(), this.elevatorID);
				if (event != null) {
					this.addToMoveQueue(event);
					System.out.println(String.format(
							"At %d seconds, elevetor %d head to floor %d to pickup",
							SimulationClock.getTime(),
							this.elevatorID,
							event.getDestination()
					));
					System.out.println();
					System.out.println("*************************************");
					System.out.println();
				}
			} else if (!this.getMoveQueue().isEmpty()){
				
				FutureEvent event = this.getMoveQueue().get(0);

				if (event.getExpectedArrival() <= SimulationClock.getTime()) {
					System.out.println(String.format("At %d seconds, elevator %d reach floor %d ",  SimulationClock.getTime(), this.elevatorID, event.getDestination()));
					if (this.getNumPassengers() == 0) {
						
						this.currentFloor = event.getDestination();
						this.getMoveQueue().remove(0);
						int[] requests = this.getBuildingManager().loadPassengers(this.currentFloor, this.elevatorID);
						System.out.println(String.format("At %d seconds, elevator %d reach floor %d to pickup passengers with following destsinations %s", SimulationClock.getTime(), this.elevatorID, event.getDestination(), Arrays.toString(requests)));
						FutureEvent firstEvent = this.loadedPassenger(requests);
						System.out.println(String.format("At %d seconds, elevator %d head to floor %d to unload", SimulationClock.getTime(), this.elevatorID, firstEvent.getDestination()));
					} else {
						
					//	System.out.println(String.format("At %d, elevator %d reach floor %d to unload %d passengers", SimClock.getTime(), this.elevatorID, event.getDestination(), this.passengerDestinations[event.getDestination()])); //this.getMoveQueue().get(0)
						this.getBuildingManager().unloadPassengers(event.getDestination(), this.passengerDestinations[event.getDestination()], this.currentFloor);
						this.unloadedPassenger(event.getDestination());
						if (!this.getMoveQueue().isEmpty()) {

							FutureEvent nextEvent = this.getMoveQueue().get(0);
							System.out.println("###################################################################");
							System.out.println(String.format("At %d seconds, elevator %d head to floor %d to unload", SimulationClock.getTime(), this.elevatorID, nextEvent.getDestination()));
						}
					}
				}
			}
		}
	}
}
