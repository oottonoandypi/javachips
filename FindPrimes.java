package practice;
import java.util.Scanner;

public class FindPrimes {
	public static int countPrimes_nlgn(int n) {// time complexity O(nlgn)
		boolean[] nonPrimes=new boolean[n];
        int count=0;
        for(int i=2; i<n; i++){
            if(!nonPrimes[i]){
                count++;
                for(int j=2; j*i<n; j++) nonPrimes[j*i]=true;
            }
        }
        
        return count;
	}
	
	public static int countPrimes_n(int n) { // time complexity O(lgn+n)
        boolean[] nonPrimes=new boolean[n];
        int count=0;
        for(int i=2; i<Math.sqrt(n); i++){
            if(!nonPrimes[i]){
                for(int j=2; j*i<n; j++) nonPrimes[j*i]=true;
            }
        }
        
        for(int i=2; i<nonPrimes.length; i++){
            if(!nonPrimes[i]) count++;
        }
        
        return count;
    }
	
	public static void main(String[] args) {
		System.out.print("FindPrimes finds all the prime numbers smaller than the integer you enter below.\n"
				+ "Please enter an integer: ");
		Scanner in = new Scanner(System.in);
		int userInput = in.nextInt();
		in.close();
		
		System.out.println("There are "+FindPrimes.countPrimes_n(userInput)+" prime numbers smaller than "+userInput+".");
	}
}
