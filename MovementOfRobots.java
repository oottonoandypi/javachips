package javachips;

import java.util.Arrays;

public class MovementOfRobots {
	/* Some robots are standing on an infinite number line with their initial coordinates given by a 0-indexed integer array nums and will start moving once given the command to move. The robots will move a unit distance each second.

	You are given a string s denoting the direction in which robots will move on command. 'L' means the robot will move towards the left side or negative side of the number line, whereas 'R' means the robot will move towards the right side or positive side of the number line.
	
	If two robots collide, they will start moving in opposite directions.
	
	Return the sum of distances between all the pairs of robots d seconds after the command. Since the sum can be very large, return it modulo 10^9 + 7.
	*/
	
	/* Note: 
	 * For two robots at the index i and j, pair (i,j) and pair (j,i) are considered the same pair.
		When robots collide, they instantly change their directions without wasting any time.
		Collision happens when two robots share the same place in a moment.
		For example, if a robot is positioned in 0 going to the right and another is positioned in 2 going to the left, the next second they'll be both in 1 and they will change direction and the next second the first one will be in 0, heading left, and another will be in 2, heading right.
		For example, if a robot is positioned in 0 going to the right and another is positioned in 1 going to the left, the next second the first one will be in 0, heading left, and another will be in 1, heading right.
	 */
	public int sumDistance(int[] nums, String s, int d) {
		// runtime O(nlgn) memory O(1)
        int robots=nums.length;
        
        // boolean[] moveLeft=new boolean[s.length()];
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)=='R') nums[i]+=d;
            else nums[i]-=d;
        }
        //boolean isCollide=false;
        /*
        while(d>0){
            for(int i=0; i<nums.length; i++){
                if(moveLeft[i]) nums[i]--;
                else nums[i]++;
                
                if(i-1>=0 && nums[i]==nums[i-1]){
                    moveLeft[i-1]=!moveLeft[i-1];
                    moveLeft[i]=!moveLeft[i];
                }
                System.out.print(nums[i]+ " ");
                /*if(moveLeft[i]) {
                    if(i-1>=0 && nums[i]==nums[i-1]) isCollide=true;
                    nums[i]--;
                    if(i-1>=0 && nums[i]==nums[i-1]) isCollide=true;
                }else nums[i]++;
                
                if(isCollide){
                    moveLeft[i-1]=!moveLeft[i-1];
                    moveLeft[i]=!moveLeft[i];
                }
                isCollide=false;*
            }
            System.out.println();
            d--;
        }*/
        
        Arrays.sort(nums);
        
        int sum=0;
        int mod=1000000007;
        
        for(int i=0; i<robots; i++){
            sum=(int)((sum+(i*(long)nums[i])-(robots-i-1)*(long)nums[i])%mod);
            // System.out.println(sum);
        }
        
        return sum;
    }
}
