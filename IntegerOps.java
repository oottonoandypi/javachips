package javachips;

import java.util.List;
import java.util.ArrayList;

public class IntegerOps {
	public static int numSquares_nSqrtn_array(int n) { 
        int[] countSqs = new int[n+1];
        for(int i=1; i<=n; i++){
            countSqs[i]=i;
            for(int j=1; j*j<=i; j++){
                countSqs[i]=Math.min(countSqs[i], countSqs[i-j*j]+1);
            }
        }
        
        return countSqs[n];
    }
	
	// ---------------------------------------------------------
	// Find the least number of square integers that sum up to the target n
	// square integers ex: 1, 4, 9, 16,... 
	public static int numSquares_nSqrtn_arraylist(int n) {
		List<Integer> perfectSquares=new ArrayList<Integer>(); 
        return minNumSquares(perfectSquares, n, 0);
	}
	private static int minNumSquares(List<Integer> perfectSquares, int n, int maxSq){
        if (Math.pow(maxSq+1, 2) > n) return n;
        
        perfectSquares.add((int)Math.pow(maxSq+1, 2));
        int minCount= minNumSquares(perfectSquares, n, maxSq+1);
        
        int countMaxSq=n/perfectSquares.get(maxSq);
        int remaining=n%perfectSquares.get(maxSq);
        int countRemaining = remaining;
        
        for(int i=maxSq-1; i>=0; i--){
            // countRemaining=Math.min(minNumSquares(perfectSquares, visited, remaining, i),countRemaining);
            int index=i;
            int countMinRemaining=0;
            int calcRemaining=remaining;
            while(calcRemaining>0){
                countMinRemaining+=calcRemaining/perfectSquares.get(index);
                calcRemaining%=perfectSquares.get(index--);
            }
            countRemaining=Math.min(countRemaining, countMinRemaining);
        }
        
        return Math.min(minCount, countMaxSq+countRemaining);
    }
	
	// ---------------------------------------------------------
	// Find the nth positive integer that is limited to only 3 prime factors: 2, 3, 5
	public static int nthIntLimitedToPrimeFactors_235(int n) {
		// runtime O(n)
		// Approach: since prime numbers are only divisible by 1 and itself, 
		// all the integers that we are looking for are only divisible by these 3 primes: 2, 3, 5 if they have at least one prime factor
		// therefore, the numbers are 2*(...visitedResults), 3*(...visitedResults), 5*(...visitedResults)
		// EX. 2*[1,2,3,4,5,6,8,10,...], 3*[1,2,3,4,5,6,8,10,...], 5*[1,2,3,4,5,6,8,10,...]
		// Place them in order until the nth of these integers is reached.
		if(n<0) return -1;
		
        int[] primes=new int[]{2,3,5};
        int[] primeIndex = new int[3];
        
        int[] res=new int[n];
        res[0]=1;
        
        for(int i=1; i<n; i++){
            int nextIf2=primes[0]*res[primeIndex[0]];
            int nextIf3=primes[1]*res[primeIndex[1]];
            int nextIf5=primes[2]*res[primeIndex[2]];
            
            int next=Math.min(Math.min(nextIf2,nextIf3), nextIf5);
            res[i]=next;
            
            if(next==nextIf2) primeIndex[0]++;
            if(next==nextIf3) primeIndex[1]++;
            if(next==nextIf5) primeIndex[2]++;
        }
        
        return res[res.length-1];
        
    }
	
	
	// ---------------------------------------------------------
	// check if a given positive integer is limited to only 3 prime factors: 2, 3, 5
	public static boolean isLimitedToPrimeFactors_235(int n) {
		// runtime O(lgn)
		// Approach: since a prime number is only divisible by 1 and itself
		// when an integer is limited to a prime factor, its division result is divisible to this prime factor until reaching 1
		// Ex. 8/2=4 8%2=0; 4/2=2 4%2=0; 2/2=1 2%2=0; then we can say 8 is limited to only prime factor 2
		// we have 2, 3, 5 so we find division result by dividing n by 2 until n%2!=0 then n%3!=0 then n%5!=0
		// if the final n we get is 1 then it is True; or else, False
		
        if(n<0) return false;
        
        int[] primes=new int[]{2,3,5};
        for(int p: primes){
            while(n>1) {
                if(n%p==0) n/=p;
                else break;
            }
        }
        
        return n==1;
    }
	
	// ---------------------------------------------------------
	// check if an integer is power of 2; using binary knowledge
	public static boolean isPowerOfTwo_bin(int n) {
		// runtime O(1)
        return n>0 && (n&(n-1))==0;
    }
	// check if an integer is power of 2; using loop
	public static boolean isPowerOfTwo(int n) {
        // runtime O(lgn)
        if(n<=0) return false;
        while(n>1){
            if(n%2!=0) return false;
            n/=2;
        }
        return true;
    }
}
