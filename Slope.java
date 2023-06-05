package javachips;

public class Slope {
	// ---------------------------------------------------------
	// Returns if the given coordinates are on a straight line
	public static boolean checkStraightLine(int[][] coordinates) {
		// runtime O(n) memory O(1)
        int[] diff=new int[]{coordinates[1][0]-coordinates[0][0], coordinates[1][1]-coordinates[0][1]};
        for(int i=2; i<coordinates.length; i++){
            double[] diffPrev=new double[]{(double)(coordinates[i][0]-coordinates[0][0]), (double)(coordinates[i][1]-coordinates[0][1])};
            
            if(diff[0]!=0 && diff[1]!=0){
                if(diffPrev[0]/diff[0]!=diffPrev[1]/diff[1]) return false;
            }else if(diff[0]==0 && diffPrev[0]!=0) return false;
            else if(diff[1]==0 && diffPrev[1]!=0) return false;
        }
        
        return true;
        
    }
}
