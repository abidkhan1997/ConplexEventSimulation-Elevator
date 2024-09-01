public class SimClock {
	
	// class used to represent simulated time
	
	private static int simTime;



	public void SimClock() { 
		simTime = 0;
	}

	public static synchronized void tick() {
		simTime ++;
	}
	
	public static synchronized int getTime(){
		return simTime;
	}
}
