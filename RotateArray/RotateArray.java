package javachips;
import java.io.File;
import java.util.Scanner;

public class RotateArray {
	
	private static void solutionOne(int[] nums, int k) { // linear runtime (nums.length+k%nums.length);
		if(k>nums.length) k=k%nums.length;
        if(k>0){
            int rotateStart = nums.length-k;
            int rotateLen=k;
            int remainStart=0;
            int remainLen=nums.length-k;

            if(remainLen>0 && rotateLen>remainLen) {
                int tempLen=rotateLen;
                int tempStart=rotateStart;
                rotateStart=remainStart;
                rotateLen=remainLen;
                remainStart=tempStart;
                remainLen=tempLen;
            }

            while(remainLen>0){
                if(remainStart<rotateStart){
                    int i=remainStart+remainLen-1;
                    while(i-rotateLen+1>=remainStart){
                        for(int j=i; j>=i-rotateLen+1; j--) {
                            swap(nums, j, rotateStart+(j-i+rotateLen-1));
                        }
                        i-=rotateLen;
                        remainLen-=rotateLen;
                        rotateStart-=rotateLen;
                    }
                    if(remainLen>0){
                        int tempRotateStart=rotateStart;
                        int tempRotateLen=rotateLen;
                        rotateStart=remainStart;
                        rotateLen=i+1-rotateStart;
                        remainStart=tempRotateStart;
                        remainLen=tempRotateLen;
                    }
                }else{// remainStart>rotateStart
                    while(rotateLen<=remainLen){
                        for(int m=remainStart; m<remainStart+rotateLen; m++){
                            swap(nums, rotateStart+(m-remainStart), m);
                        }
                        rotateStart+=rotateLen;
                        remainStart+=rotateLen;
                        remainLen-=rotateLen;
                    }
                    if(remainLen>0){
                        int tempRotateStart=rotateStart;
                        int tempRotateLen=rotateLen;
                        rotateStart=remainStart;
                        rotateLen=remainLen;
                        remainStart=tempRotateStart;
                        remainLen=tempRotateLen;
                    }
                }
            }
        }
        
    }
	
	private static void solutionTwo(int[] nums, int k) { // same approach as solutionOne, but recursive
		int numsLen=nums.length;
        k%=numsLen;
        if(k>0) recursiveRotate(nums, 0, numsLen-1-k, numsLen-k, numsLen-1);
		
	}
	
	private static void recursiveRotate(int[] nums, int remainStart, int remainEnd, int rotateStart, int rotateEnd) {
		int remainLen=remainEnd-remainStart+1;
        if(remainLen==0) return;
        
        int rotateLen=rotateEnd-rotateStart+1;
        
        
        if(remainLen<rotateLen){
            int tempRotateStart=rotateStart;
            int tempRatateEnd=rotateEnd;
            rotateStart=remainStart;
            rotateEnd=remainEnd;
            remainStart=tempRotateStart;
            remainEnd=tempRatateEnd;
            remainLen=remainEnd-remainStart+1;
            rotateLen=rotateEnd-rotateStart+1;
        }
        
        while(remainLen>=rotateLen){
            for(int i=rotateStart; i<=rotateEnd; i++) {
                swap(nums, i, remainStart+i-rotateStart);
            }
            remainLen-=rotateLen;
            remainStart+=rotateLen;
            if(rotateStart<remainStart) {
                rotateStart+=rotateLen;
                rotateEnd+=rotateLen;
            }
        }
        
        
        recursiveRotate(nums, remainStart, remainEnd, rotateStart, rotateEnd);
	}
    
    private static void swap(int[] nums, int index1, int index2){
        int temp = nums[index1];
        nums[index1]=nums[index2];
        nums[index2]=temp;
    }
    
    private static void solutionThree(int[] nums, int k) { // reverse reverse approach; runtime(nums.length/2+k/2+(nums.length-k)/2)=nums.length
    	k%=nums.length;
        if(k>0){
            reverseArray(nums,0,nums.length-1);
            reverseArray(nums,0,k-1);
            reverseArray(nums, k,nums.length-1);
        }
    }
    
    private static void reverseArray(int[] nums, int start, int end){
        for(int i=start; i<=(start+end)/2; i++){
            swap(nums, i, end-(i-start));
        }
    }
	
	public static void main(String[] args) {
		try {
			// test input
			File testfile = new File("src/javachips/RotateArray_TestInput.txt");
			Scanner scan = new Scanner(testfile);
			// test output
			File testanswer=new File("src/javachips/RotateArray_TestOutput.txt");
			Scanner scan2=new Scanner(testanswer);
			
			// for every test case, print out input data; output data; output match with test output
			while(scan.hasNextLine() && scan2.hasNextLine()) {
				int[] arr = new int[scan.nextInt()];
				int k = scan.nextInt();
				String arrStr = scan.next();
				
				int arrIndex=0;
				int currInt = 0;
				
				System.out.print("Input: [");
				for(int i=1;i<arrStr.length();i++) {
					char currChar = arrStr.charAt(i);
					if(currChar==',' || currChar==']') {
						arr[arrIndex++]=currInt;
						if(currChar==',') System.out.print(currInt+",");
						if(currChar==']') System.out.print(currInt+"]");
						currInt=0;
					}else if(currChar>='0' && currChar<='9') {
						currInt = currInt*10+(currChar-48);
					}else currInt=0;
				}
				
				System.out.println(" "+k);
				
				// solutionOne(arr, k);
				// solutionTwo(arr, k);
				solutionThree(arr, k);
				
				System.out.print("Output: [");
				boolean outputMatch=true;
				boolean[] outputMatchArr=new boolean[arr.length];
				
				for(int i=0; i<arr.length; i++) {
					System.out.print(arr[i]);
					if(scan2.nextInt()==arr[i]) outputMatchArr[i]=true;
					if(i<arr.length-1) System.out.print(",");
				}
				
				System.out.println("]");
				System.out.print("Output Match: [");
				for(int i=0; i<outputMatchArr.length; i++) {
					System.out.print(outputMatchArr[i]);
					if(i<arr.length-1) System.out.print(",");
				}
				System.out.print("] ");
				System.out.println(outputMatch);
			}
			scan.close();
			scan2.close();
		}catch(Exception err) {
			System.out.println(err);
		}
		
	}
}
