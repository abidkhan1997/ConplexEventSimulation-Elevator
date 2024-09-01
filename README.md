There are 6 floors, 0,1,..5  in a building and 4 elevators. Most people arrive to the system via the zero floor.. They arrive with an exponential distribution with an average of 10/min. The elevators move from floor to floor in 6 sec. Like most buildings there are two buttons on a floor, one for up and one for down. Once an elevator gets to floor everyone that wants to get off on that floor exit the elevator at 2/sec before people 2/sec get on. Each elevator has buttons for all floors. If the floor button is not pressed for the floor someone entering the elevator wants to go to, they press the appropriate button. People on any floor arrive to take the elevator equilikely to want to go any floor at a rate of 5/min exponentially, except floor zero which is half the time the choice. . Elevators will stay at a floor if no one wants in the elevator wants to go in that direction.  A central controller controls all elevators. 


➢	there should be one future events list of all future events in time order. show me where this is in your code.
➢	you should generate one new passenger when a current one "arrives" to the system. show me where in the code this is. 
➢	you should generate passengers arrivals using an exponential distribution. show me where this is.
➢	Output good debugging information for the first twenty clock changes. 
➢	this means: current clock time, all events on the future event list in time order and their type and delay list or how you are keeping track of delays

==== ============================

1.	Future event list- how long it takes for a passenger to arrive their destination floor
2.	generate one new passenger when a current one "arrives" to the destination floor
3.	generate passengers arrivals = exponential distribution
4.	Current time, future event list in time order, delayed time, and how we keep track of the delays
5.	Class: Elevator, Passenger, Main
