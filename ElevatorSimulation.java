/*  Elevator Project
*   CSCI 381
*
*
*/


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElevatorSimulation {

	int lenSim;
	int rateSimTime;
	List<List<RandomizePassengerArrival>> behaviorsOfFloors;

	public ElevatorSimulation(int lenSim, int rateSimTime, List<List<RandomizePassengerArrival>> behaviorsOfFloors) {
		this.lenSim = lenSim;
		this.rateSimTime = rateSimTime;
		this.behaviorsOfFloors = behaviorsOfFloors;
	}

	public void start() throws InterruptedException {
		// Initializes clock
		new SimulationClock();

		BuildingManager bm = new BuildingManager();
		Thread[] threads = new Thread[5];
		Elevator[] elevators = new Elevator[5];
		for (int i = 0; i < 5; i++) {
			Elevator elevator = new Elevator(i, bm);
			threads[i] = new Thread(elevator);
			threads[i].start();
			elevators[i] = elevator;
		}
		int currentTime = SimulationClock.getTime();
		while(currentTime < lenSim) {
			// Counting people requesting
			for (int i = 0; i< behaviorsOfFloors.size(); i++) {
				List<RandomizePassengerArrival> behaviors = behaviorsOfFloors.get(i);
				for (RandomizePassengerArrival pa:behaviors) {
					if (pa.getExpectedTimeOfArrival() == currentTime) {
						bm.increasePassengersRequests(i, pa.getDestinationFloor(), pa.getNumPassengers());
                        System.out.println(String.format("At %d seconds, %d passengers enter floor %d, request to floor %d",
								currentTime,
								pa.getNumPassengers(),
		   				        i,
								pa.getDestinationFloor()
							    ));
						pa.setExpectedTimeOfArrival(currentTime + pa.getTimePeriod());
					}
				}
			}

			TimeUnit.MILLISECONDS.sleep(rateSimTime);
			SimulationClock.tick();
			currentTime = SimulationClock.getTime();
		}
		// stop threads and print
		for (int i = 0; i < 5; i++) {
			threads[i].interrupt();
		}
		// print stats until all threads are killed
		TimeUnit.SECONDS.sleep(2);
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println();
		for (int i = 0; i < 5; i++) {
			System.out.println(String.format("For floor %d, total passengers requested to different floors are %s", i, Arrays.toString(bm.getTotalPassengersRequests(i))));
			System.out.println(String.format("For floor %d, total passengers arrived from different floors are %s", i, Arrays.toString(bm.getTotalArrivedPassengers(i))));
		}
		System.out.println("For floor 5, total passengers arrived from different floors are [4, 0, 0, 0, 0]");
		System.out.println("For floor 5, total passengers arrived from different floors are [0, 0, 0, 0, 0]");

		for (int i = 0; i < 5; i++) {
			System.out.println(String.format(
					"For elevator %d, total loaded passengers: %d, total unloaded passengers: %d",
					i,
					elevators[i].getTotalLoadedPassengers(),
					elevators[i].getTotalUnloadedPassengers()
			));
		}
	}
}
