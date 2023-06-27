package javachips;

public class EliminationGame {
	// Input: an integer n representing a list arr of all integers in the range [1, n] sorted in a strictly increasing order.
	// Return: the last number that remains in arr
	
	// Rule: Apply the following algorithm on arr:
	/*
	 * Starting from left to right, remove the first number and every other number afterward until you reach the end of the list.
	 * Repeat the previous step again, but this time from right to left, remove the rightmost number and every other number from the remaining numbers.
	 * Keep repeating the steps again, alternating left to right and right to left, until a single number remains.
	 */
	
	public int lastRemainingInteger(int n) {
		// time complexity O(lgn) memory O(1)
		int left=1;
        int gap=2;
        boolean startLeft=true;
        
        while(left<n){
            if(startLeft){
                left+=gap/2;
                n=left+(n-left)/gap*gap;
            }else{
                n-=gap/2;
                left=n-(n-left)/gap*gap;
            }
            
            gap*=2;
            startLeft=!startLeft;
        }
        
        return left;
    }
}
