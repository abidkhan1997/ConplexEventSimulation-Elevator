*   Elevator Project
*   CSCI 381
*/

public class BuildingFloor {
    private int[] totalDestinationRequests;
    private int[] arrivedPassengers;
    private int[] passengerRequests;
    private int approachingElevator;

    public BuildingFloor() {
        totalDestinationRequests = new int[5];
        arrivedPassengers = new int[5];
        passengerRequests = new int[5];
        approachingElevator = -1;
    }

    public void setApproachingElevator(int n) { this.approachingElevator = n;}

    public void increaseArrivedPassengers(int from, int n) { this.arrivedPassengers[from] = this.arrivedPassengers[from] + n;}

    public void increasePassengersRequests(int floor, int n) {
        this.passengerRequests[floor] = this.passengerRequests[floor] + n;
        this.totalDestinationRequests[floor] = this.totalDestinationRequests[floor] + n;
    }

    public int [] getTotalDestinationRequests (){
        return this.totalDestinationRequests;
    }

    public int [] getArrivedPassengers() {
        return this.arrivedPassengers;
    }

    public int getApproachingElevator() {return this.approachingElevator;}

    public int[] getPassengerRequests() {
        return this.passengerRequests;
    }

    public void setPassengerRequests(int[] passengerRequests) {
        this.passengerRequests = passengerRequests;
    }

    public boolean getIsPassengerRequesting() {
        for (int i = 0; i < passengerRequests.length ; i++) {
            if (passengerRequests[i] != 0) {
                return true;
            }
        }
        return false;
    }
}
