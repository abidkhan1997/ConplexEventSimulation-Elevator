public class FutureEvent {
	private int destination;
	private int expectedArrival;
	
	public FutureEvent() { 
		this.destination = 0;
		this.expectedArrival = 0; 
	}
	public FutureEvent(int destination, int expectedArrival) {
		this.destination = destination;
		this.expectedArrival = expectedArrival; 
	}
	public int getDestination() { return destination;}
	public int getExpectedArrival() { return expectedArrival;
	}
}
