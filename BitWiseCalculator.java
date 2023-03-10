package javachips;
import java.util.Scanner;

public class BitWiseCalculator {
	public int andOfRange(int left, int right) { // runtime O(lg(left))
		if(left==right) return left;
        int digit = 0;
        int andOnes = 0;
        while(left>0){
            int leftCompare = left%2;
            int rightCompare = right%2;
            
            if(leftCompare==rightCompare){
                andOnes += leftCompare*(int)Math.pow(2, digit);
            } else{
                andOnes = 0;
            }
            
            left/=2;
            right/=2;
            digit++;
        }
        if(right==0) return andOnes;
        else return 0;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Please enter the range that you'd like to calculate the andOfRange in the format of LEFT_RANGE RIGHT_RANGE. EX. 5 7");
		Scanner scan = new Scanner(System.in);
		
		try {
			int leftRange = scan.nextInt();
			int rightRange = scan.nextInt();
			scan.close();
			
			if(leftRange>rightRange) System.out.println("INVALID INPUT: LEFT_RANGE has to be LESS OR EQUALS to RIGHT_RANGE.");
			else {
				BitWiseCalculator calc = new BitWiseCalculator();
				int result = calc.andOfRange(leftRange, rightRange);
				
				System.out.println("Outcome: "+result);
			}
		}catch(Exception e) {
			System.out.println("ERROR:: "+e);
		}
	}
}
