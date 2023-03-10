package javachips;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GreedyHouseRobber {
	public int maxAmount_circularHouses(int[] nums) {
        if(nums.length==1) return nums[0];
        if(nums.length<3) return Math.max(nums[0], nums[1]);
        
        int houses = nums.length;
        int[] noReachEnd=new int[houses];
        noReachEnd[houses-1]=0;
        noReachEnd[houses-2]=nums[houses-2];
        noReachEnd[houses-3]=nums[houses-3];
        nums[houses-3]+=nums[houses-1];
        
        for(int i=houses-4; i>=0; i--){
            noReachEnd[i]=nums[i]+Math.max(noReachEnd[i+2], noReachEnd[i+3]);
            nums[i]+=Math.max(nums[i+2], nums[i+3]);
        }
        
        return Math.max(Math.max(nums[1], nums[2]), Math.max(noReachEnd[0], noReachEnd[1]));
       
    }
	
	public int maxAmount(int[] nums) {
		// robbing adjacent houses will alert the police.
		// maxAmount that could steal without alerting the police.
		if(nums.length==1) return nums[0];
        else if(nums.length==2) return Math.max(nums[0], nums[1]);
        
        nums[nums.length-3]+= nums[nums.length-1];
        for(int i=nums.length-4; i>=0; i--){
            nums[i]+=Math.max(nums[i+2], nums[i+3]);
        }
        
        return Math.max(nums[0], nums[1]);
	}
	
	public static void main(String[] args) {
		try {
			File testInputFile = new File("src/practice/GreedyHouseRobber_TestInput.txt");
			File testOutputFile = new File("src/practice/GreedyHouseRobber_TestOutput.txt");
		

			Scanner inputScan = new Scanner(testInputFile);
			Scanner outputScan = new Scanner(testOutputFile);
			GreedyHouseRobber robber = new GreedyHouseRobber();
			
			while(inputScan.hasNextLine() && outputScan.hasNextLine()) {
				int[] houses = new int[inputScan.nextInt()];
				for(int i=0; i<houses.length; i++) {
					try {
						houses[i]=inputScan.nextInt();
					}catch(Exception e) {
						System.out.println("ERROR:: "+e);
					}
					
				}
				int maxDollars = robber.maxAmount(houses);
				System.out.println("Robber could rob a max. of "+maxDollars+" dollars without alerting the police.");
				if(maxDollars==outputScan.nextInt()) System.out.println("MATCH expectation.");
				else System.out.println("DOESN'T match expectation.");
			}
			
			inputScan.close();
			outputScan.close();
		
		
		}catch(IOException e) {
			System.out.println("ERROR:: "+e);
		}
		
	}
}
