package javachips;

public class ArrayOfCharOps {
	// ---------------------------------------------------------
	// Given a non-decreasing array of characters and a character target
	// Return the smallest letter in the array that is larger than target
	// If no such letter, return the first letter in the array
	public char smallestLargerLetter(char[] letters, char target) {
		// runtime O(lgn) memory O(1)
        int l=0;
        int r=letters.length-1;
        if(letters[r]<=target) return letters[0];
        int m;
        
        while(l<=r){
            m=l+(r-l)/2;
            if(letters[m]<=target) l=m+1;
            else r=m-1;
        }
        
        return letters[l];
    }
	
	// ---------------------------------------------------------
	public void reverseString(char[] s) {
        int len=s.length;
        for(int i=0; i<=(len-1)/2; i++){
            char t=s[i];
            s[i]=s[len-1-i];
            s[len-1-i]=t;
        }
    }
}
