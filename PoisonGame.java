package javachips;

public class PoisonGame {
	// Given an non-decreasing ordered array timeSeries that stores all the times when a poison attack happens
	// and an integer duration that is how long each attack will last
	// Return the total poison duration within the given timeSeries
	// EX. timeSeries = [1,4], duration = 2 => totalPoisonDuration= 4
	// EX. timeSeries = [1,2], duration = 2 => totalPoisonDuration= 3
	public int findPoisonedDuration(int[] timeSeries, int duration) {
		// runtime O(n); memory usage O(1)
        int totalDuration=duration;
        for(int i=1; i<timeSeries.length; i++){
            totalDuration+=duration;
            if(timeSeries[i]<=timeSeries[i-1]+duration-1) totalDuration-= timeSeries[i-1]+duration-timeSeries[i];
        }
        return totalDuration;
    }
}
