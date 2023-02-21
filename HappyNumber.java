package practice;
import java.util.HashSet;
import java.util.Scanner;

public class HappyNumber {
	public static boolean isHappy(int n) {
        int nextN=0;
        HashSet<Integer> visited=new HashSet<Integer>();
        
        while(n>9){
            while(n>0){
                nextN+=(n%10)*(n%10);
                n/=10;
            }
            if(visited.contains(nextN)) return false;
            visited.add(nextN);
            n=nextN;
            nextN=0;
        }
        
        if(n==1 || n==7) return true;
        return false;
    }
	
	public static void main(String[] args) {
		System.out.println("A Happy Number means \n"
				+ "- Starting with any positive integer, replace the number by the sum of the squares of its digits.\n"
				+ "- Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.\n"
				+ "- Those numbers for which this process ends in 1 are happy.\n"
				+ "\n Please enter the integer that you'd like to check if it's a Happy Number.");
		Scanner input = new Scanner(System.in);
		int inputVal=input.nextInt();
		input.close();
		boolean isHappyNum = HappyNumber.isHappy(inputVal);
		
		if(isHappyNum) System.out.println(inputVal+" is a Happy Number.");
		else  System.out.println(inputVal+" is NOT a Happy Number.");
		
	}
}
