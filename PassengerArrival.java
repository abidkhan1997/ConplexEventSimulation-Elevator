public class RandomizePassengerArrival {

	private int numPassengers; 
	private int destinationFloor;
	private int timePeriod; 
	private int expectedTimeOfArrival; 

	public RandomizePassengerArrival(int numOfPassengers, int destinationFloor, int timePeriod, int expectedTimeOfArrival) { 
		this.numPassengers = numOfPassengers;
		this.destinationFloor = destinationFloor; 
		this.timePeriod = timePeriod; 
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	
	public void setNumPassengers(int n) { this.numPassengers = n;} 
	public void setDestinationFloor(int n) { this.destinationFloor = n;} 
	public void setTimePeriod(int n) { this.timePeriod = n;} 
	public void setExpectedTimeOfArrival(int n) { this.expectedTimeOfArrival = n;}
	
	public int getNumPassengers(){ return numPassengers;} 
	public int getDestinationFloor(){ return destinationFloor;} 
	public int getTimePeriod() { return timePeriod;}
	public int getExpectedTimeOfArrival() { return expectedTimeOfArrival;}
	
 	}
	
