package javachips;

public class ArrayOfCharOps {
	public void reverseString(char[] s) {
        int len=s.length;
        for(int i=0; i<=(len-1)/2; i++){
            char t=s[i];
            s[i]=s[len-1-i];
            s[len-1-i]=t;
        }
    }
}
