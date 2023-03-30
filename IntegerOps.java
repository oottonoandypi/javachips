package javachips;

public class IntegerOps {
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
