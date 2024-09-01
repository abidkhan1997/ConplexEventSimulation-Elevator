/*  Elevator Project
*   CSCI 381
*
*/

public class BuildingManager {
    private BuildingFloor[] buildingFloors;

    public BuildingManager() {
        buildingFloors = new BuildingFloor[5];
        for (int i = 0; i < 5; i ++) {
            buildingFloors[i] = new BuildingFloor();
        }
    }

    public synchronized void increasePassengersRequests(int floor, int dest,int num) {
        this.buildingFloors[floor].increasePassengersRequests(dest, num);
    }

    public synchronized FutureEvent getNextRequest(int floor, int elevatorId) {
        // 1. check if any valid event
       for (int i = 0; i < 5; i++) {
           BuildingFloor fl = buildingFloors[i];
           if (fl.getApproachingElevator() == -1 && fl.getIsPassengerRequesting()) {
               fl.setApproachingElevator(elevatorId);
               return new FutureEvent(i, SimulationClock.getTime() + Math.abs(i - floor) * 5 + 10);
           }
       }
        return null;
    }

    public synchronized int[] loadPassengers(int floor, int elevatorId) {
        this.buildingFloors[floor].setApproachingElevator(-1);
        // load all up first, if none, load down
        int[] loaded = new int[5];
        int[] requests = this.buildingFloors[floor].getPassengerRequests();
        boolean isGoingUp = false;
        for (int i = floor; i < requests.length; i ++) {
            if (requests[i] != 0) {
//                events.add(new ElevatorEvent(i, Math.abs(i - floor) * 5 + timeToLoad));
                loaded[i] = requests[i];
                requests[i] = 0;
                isGoingUp = true;
            }
        }
        // load down
        if (!isGoingUp) {
            this.buildingFloors[floor].setPassengerRequests(loaded);
            return requests;
        }
        this.buildingFloors[floor].setPassengerRequests(requests);
        return loaded;
    }

    public synchronized void unloadPassengers(int floor, int num, int from) {
        this.buildingFloors[floor].increaseArrivedPassengers(from, num);
    }

    public synchronized int[] getTotalPassengersRequests(int floor) {
        return this.buildingFloors[floor].getTotalDestinationRequests();
    }
    public synchronized int[] getTotalArrivedPassengers(int floor) {
        return this.buildingFloors[floor].getArrivedPassengers();
    }
}
