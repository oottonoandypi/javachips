package javachips;
import java.util.Scanner;

public class BitWiseCalculator {
	// ---------------------------------------------------------
	// Given integer a, b, and c, Find minimum flips to make a|b=c
	public int minFlipsMakeAorBequalsC(int a, int b, int c) {
		// runtime O(lg(Max(a, b, c))) memory O(1)
		int digitC;
        int digitA;
        int digitB;
        int count=0;
        
        while(c>0 || b>0 || a>0){
            digitA=a%2;
            digitB=b%2;
            digitC=c%2;
            
            if((digitA|digitB)!=digitC){
                if(digitC==1) count++;
                else{
                    if((digitA^digitB)==0) count+=2;
                    else count++;
                }
            }
            
            c/=2;
            b/=2;
            a/=2;
        }
        return count;
	}
	
	// return complement of a given integer
	public int findComplement(int num) {
		// runtime O(lgn)
        int xor=0;
        int numCopy=num;
        
        while(numCopy>0){
            xor=(xor<<1)+1;
            numCopy/=2;
        }
        
        return num^xor;
    }
	
	// ---------------------------------------------------------
	// count different bits of 2 integers
	public int countBitDiff(int x, int y) {
		// runtime O(lgn)
        int diff=x^y;
        int count=0;
        while(diff>0){
            count+=diff%2;
            diff/=2;
        }
        return count;
        
        /* 
         * int count=0;
        
        while(x>0 && y>0){
            if(x%2!=y%2) count++;
            x/=2;
            y/=2;
        }
        
        while(x>0){
            count+=x%2;
            x/=2;
        }
        
        while(y>0){
            count+=y%2;
            y/=2;
        }
        
        return count;
         */
    }
	
	// ---------------------------------------------------------
	public int getSum(int a, int b) {
		// Approach: 0+1 or 1+0 is 1 with no carry; 1+1 is 0 with carry; 0+0 is 0 with no carry => xor can do the sum part
		// and can do the carry part because only 1+1 has carry; so whenever the carry continues, carry move forward a digit to the left
		// update a with the latest sum; b carries the carry
		// repeat until carry is 0
        while(b!=0){
            int aDuplicate=a;
            a=a^b;
            b=(aDuplicate&b)<<1;
        }
        return a;
    }
	
	// ---------------------------------------------------------
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
