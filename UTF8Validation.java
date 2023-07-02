package javachips;

public class UTF8Validation {
	// Check if the given data is a valid UTF-8 encoding
	// Input: an integer array data representing the data,
	// Return: whether it is a valid UTF-8 encoding
	// UTF-8 encoding explanation: A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
			// For a 1-byte character, the first bit is a 0, followed by its Unicode code.
			// For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
	

	public boolean validUtf8(int[] data) {
		// time complexity O(n*8) space complexity O(n)
        return isValidUtf8(data, 0);
    }
    
    private boolean isValidUtf8(int[] data, int startIndex){
        // System.out.println(startIndex);
        if(startIndex==data.length) return true;
        else if(startIndex>data.length) return false;
        
        int byteLength=0;
        for(int i=0; i<8; i++){
            if(((1<<(7-i))&data[startIndex])!=0) byteLength++;
            else break;
        }
        // System.out.println(byteLength);
        // byteLength++;
        if(byteLength==1 || byteLength>4) return false;
        if(byteLength==0) return isValidUtf8(data, startIndex+1);
        for(int i=startIndex+1; i<startIndex+byteLength && i<data.length; i++){
            // System.out.println(Integer.toBinaryString(data[i]));
            if((data[i]&10000000)!=128) return false;
        }
        return isValidUtf8(data, startIndex+byteLength);
    }
}
