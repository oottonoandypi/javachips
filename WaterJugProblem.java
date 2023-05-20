package javachips;

public class WaterJugProblem {
	public static boolean canMeasureWater_lgn(int jug1Capacity, int jug2Capacity, int targetCapacity) {
		// GCD approach
        if(targetCapacity>jug1Capacity+jug2Capacity) return false;
        else if(targetCapacity==jug1Capacity || targetCapacity==jug2Capacity || targetCapacity==jug1Capacity+jug2Capacity) return true;
        
        int largerJug=Math.max(jug1Capacity, jug2Capacity);
        int smallerJug=Math.min(jug1Capacity, jug2Capacity);
        
        while(smallerJug>0){
            int mod=largerJug%smallerJug;
            largerJug=smallerJug;
            smallerJug=mod;
        }
        
        return targetCapacity%largerJug==0;
    }
	
	public static boolean canMeasureWater_nlgn(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if(targetCapacity>jug1Capacity+jug2Capacity) return false;
        else if(targetCapacity==jug1Capacity || targetCapacity==jug2Capacity || targetCapacity==jug1Capacity+jug2Capacity) return true;
        
        int smallerCapacity=Math.min(jug1Capacity, jug2Capacity);
        int largerCapacity=Math.max(jug1Capacity, jug2Capacity);
        int largerFill= smallerCapacity;
        
        while(largerFill<largerCapacity){
            if(largerFill==targetCapacity || largerFill+smallerCapacity==targetCapacity) return true;
            largerFill+=smallerCapacity;
            if(largerFill>largerCapacity) largerFill-=largerCapacity;
            // if(largerCapacity==fullCapacity-smallerCapacity) return false;
        }
        return false;
    }
}
