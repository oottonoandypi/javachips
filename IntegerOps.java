package javachips;

import java.util.List;
import java.util.ArrayList;

public class IntegerOps {
	// ---------------------------------------------------------
	// You have n coins and you want to build a staircase with these coins. 
	// The staircase consists of k rows where the ith row has exactly i coins. 
	// The last row of the staircase may be incomplete.
	// Given the integer n, return the number of complete rows of the staircase you will build.
	public static int maxFullStairs_sqrt(int n){
        // (1+k)*k<=2n
        // (1/2+k)^2=k^2+k+1/4=(1+k)*k+1/4
        // (1/2+k)^2-1/4<=2n
        // (1/2+k)^2<=2n+1/4
        // 1/2+k<=sqrt(2n+1/4)
        // k<=sqrt(2n+1/4)-1/2
        
        return (int)(Math.sqrt(((long)n+(long)n+0.25))-0.5);
    }
	public static int maxFullStairs_lgn(int n) {
        // (1+k)*k<=2n
        long l=1;
        long r=n;
        long m;
        
        while(l<=r){
            m=l+(r-l)/2;
            if((m*m+m)/2==(long)n) return (int)m;
            else if((m*m+m)/2<(long)n) l=m+1;
            else r=m-1;
        }
        
        return (int)(l-1);
    } 
	
	// ---------------------------------------------------------
	// Given an integer n, return a string array answer (1-indexed) where:

	// answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
	// answer[i] == "Fizz" if i is divisible by 3.
	// answer[i] == "Buzz" if i is divisible by 5.
	// answer[i] == i (as a string) if none of the above conditions are true.
	public static List<String> fizzBuzz_3515(int n) {
        List<String> res=new ArrayList<String>();
        
        for(int i=1; i<=n; i++){
            if(i%15==0) res.add("FizzBuzz");
            else if(i%3==0) res.add("Fizz");
            else if(i%5==0) res.add("Buzz");
            else res.add(Integer.toString(i));
        }
        
        return res;
    }
	
	
	// ---------------------------------------------------------
	// Convert an Integer to Hexadecimal without using any built-in methods
	public static String toHex(int num) {
		// runtime O(lgn); additional memory usage O(lgn)
        if(num==0) return "0";
        
        StringBuilder hexOfNum=new StringBuilder();
        boolean isPositive=true;
        if(num<0) isPositive=false;
        
        int carry=1;
        if(num==Integer.MIN_VALUE) {
            num=Integer.MAX_VALUE;
            carry--;
        }
        
        num=Math.abs(num);
        
        while(num>0){
            int nextHex=num%16;
            if(nextHex>9) hexOfNum.append((char)(nextHex%10+'a'));
            else hexOfNum.append((char)(nextHex+'0'));
            num/=16;
        }
        
        if(!isPositive){
            for(int i=0; i<hexOfNum.length(); i++){
                char c=hexOfNum.charAt(i);
                int hexVal;
                
                if(c>'9') hexVal=(int)(c-'a'+10);
                else hexVal=(int)(c-'0');
                
                hexVal^=15;
                hexVal+=carry;
                // System.out.println(hexVal);
                carry=hexVal/16;
                hexVal%=16;
                if(hexVal>9) hexOfNum.setCharAt(i, (char)(hexVal%10+'a'));
                else hexOfNum.setCharAt(i, (char)(hexVal+'0'));
                
            }
            
            while(hexOfNum.length()<8) hexOfNum.append('f');
        }
        
        return hexOfNum.reverse().toString();
        
    }
	
	// ---------------------------------------------------------
	public static boolean isPerfectSquare(int num) {
		// runtime O(lgn)
        if(num==1) return true;
        
        long small=1;
        long big=num/2;
        
        while(small<=big){
            long mid=(small+big)/2;
            long sq=mid*mid;
            if(sq==(long)num) return true;
            else if(sq>(long)num) big=mid-1;
            else small=mid+1;
        }
        return false;
    }
	
	
	// ---------------------------------------------------------
	// Given an integer n, return the count of all numbers with unique digits, x, where 0 <= x < 10^n
	public static int countNumbersWithUniqueDigits(int n) {
		// Runtime O(n)
		// Approach: count possibilities cumulatively when digits are 0,1,2...,n 
        int count=1;
        if(n==0) return count;
        count+=9;
        int lastCount=9;
        n--;
        int options=9;
        
        while(n>0){
            lastCount*=options;
            count+=lastCount;
            n--;
            options--;
        }
        
        return count;
        
    }
	
	// ---------------------------------------------------------
	// Given an integer n, break it into the sum of k positive integers, where k >= 2, and maximize the product of those integers.
	// Return the maximum product you can get.
	public static int integerBreak(int n) {
		// Approach: takes O(n) additional memory space using an array, record max products for every integers from 2...n
		// Base cases: [2]=1; [3]=2; [4]=4;
		// Starting at 4, all the max products are >= the integer itself; 
		// so it is safe to propose that [i-3]*3 is the max product for integer i (but because [i-3] could reach base cases, so take max([i-3], i-3)*3 instead
		// runtime O(n)
        int[] maxProds=new int[n+1];
        maxProds[2]=1;
        if(n==2) return 1;
        maxProds[3]=2;
        if(n==3) return 2;
        maxProds[4]=4;
        
        for(int i=5; i<=n; i++){
            maxProds[i]=Math.max(i-3, maxProds[i-3])*3;
            //System.out.print(i+": ");
            //System.out.println(maxProds[i]);
        }
        
        return maxProds[n];
    }
	
	// ---------------------------------------------------------
	// Given an integer n, return true if it is a power of four. Otherwise, return false.
	public static boolean isPowerOfFour_recur(int n){
        if(n==1) return true;
        if(n==0 || n%4!=0) return false;
        return isPowerOfFour_recur(n/4);
    }
	
	public static boolean isPowerOfFour_loop(int n){
        if(n==0) return false;
        while(n%4==0) n/=4;
        return n==1;
    }
	
	public static boolean isPowerOfFour_math(int n) {
        return n!=0 && (int)Math.pow(4, (int)(Math.log(n)/Math.log(4)))==n;
    }
	
	// ---------------------------------------------------------
	// Given an integer n, return an array ans of length n + 1 such that 
	// for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i
	public static int[] countBits_nMemory_optimized(int n) {
		// Time complexity: O(n)
        int[] res=new int[n+1];
        if(n==0) return res;
        res[1]=1;
        int end=1;
        int iter=0;
        
        for(int i=2; i<=n; i++){
            res[i]=1+res[iter++];
            if(iter>end){
                iter=0;
                end=i;
            }
        }
        return res;
    }
	
	public static int[] countBits_nlgnMemory_mathLog(int n) {
		// Time complexity: O(nlgn)
		int[] res=new int[n+1];
        if(n==0) return res;
        res[1]=1;
        
        for(int i=2; i<=n; i++){
            res[i]=1+res[i-(int)Math.pow(2, (int)(Math.log(i)/Math.log(2)))];
        }
        return res;
	}
	
	public static int[] countBits_nlgn(int n) {
		// Time complexity: O(nlgn)
		int[] res=new int[n+1];
        for(int i=1; i<=n; i++){
            int count1s=0;
            int init=i;
            while(init>0){
                count1s+=init%2;
                init/=2;
            }
            res[i]=count1s;
        }
        
        return res;
	}
	
	
	// ---------------------------------------------------------
	// return if a given integer is power of 3
	public static boolean isPowerOfThree_math(int n) {
        if(n<1) return false;
        return (Math.log10(n)/Math.log10(3))%1==0;
    }
	
	public static boolean isPowerOfThree_lgn(int n) {
        if(n<1) return false;
        
        while(n>=3 && n%3==0){
            n/=3;
        }
        
        return n==1;
    }
	
	// ---------------------------------------------------------
	public static int nthIntLimitedToPrimeFactors(int n, int[] primes) {
		// runtime O(n*primes.length)
        if(n==1) return 1;
        
        long[] sequence=new long[n];
        sequence[0]=1;
        n--;
        
        int[] records=new int[primes.length];
        
        while(n>0){
            long minNext=primes[0]*sequence[records[0]];
            
            for(int i=1; i<primes.length; i++){
                minNext=Math.min(minNext, primes[i]*sequence[records[i]]);
            }
            sequence[sequence.length-n]=minNext;
            
            for(int i=0; i<records.length; i++){
                if(minNext==primes[i]*sequence[records[i]]) records[i]++;
            }
            n--;
        }
        
        return (int)sequence[sequence.length-1];
    }
	
	// ---------------------------------------------------------
	// Initially, there is a heap of stones on the table.
	// You and your friend will alternate taking turns, and you go first.
	// On each turn, the person whose turn it is will remove 1 to 3 stones from the heap.
	// The one who removes the last stone is the winner.
	
	// Given n, the number of stones in the heap, return true if you can win the game assuming both you and your friend play optimally, 
	// otherwise return false
	public static boolean canWinNimGame(int n) {
		// General Idea: case n=1, you win by taking 1; case n=2, you win by taking 2; case n=3, you win by taking 3
		// case 4, no matter you take 1, 2, or 3, your friend can take the last 3, 2, or 1 stone(s) to win; you lose any way
		// case 5, you can take 1 then leave 4 for your friend; case 6, you can take 2 then leave 4 to friend; same as case 7
		// when case 8, you take 1, friend can take 3 and that will leave you a 4 then you lose; you take 2, friend can take 2 and again 4 for you; same as when you take 3 will leave you a 4
		// Assumption: any number of stones that is a multiply of 4 will make you lose
		// Proof: when 4*k=n where k is an integer, your friend has opportunity to leave you a 4 when you have to lose because your friend can choose to leave only 4 stones after his 2nd to last turn; no matter how you played before that.
        return n%4!=0;
    }
	
	
	// ---------------------------------------------------------
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
