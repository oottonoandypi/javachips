package javachips;

public class Sprinkles {
	public static int greatestLessThanK(int[] arr, int left, int right, int k){
        int m;
        while(left<right){
            m=left+(right-left)/2;
            if(arr[m]==k) return m;
            else if(arr[m]>k) right=m-1;
            else left=m+1;
        }
        
        if(arr[left]>k) return -1;
        return left;
    }
	
	public static int[][] stringTo2DArrayOfInt(String str, int rows, int cols){
		int[][] _2DArray = new int[rows][cols];
    	int index = 0;
		int countInt = 0;
		int intNum = 0;
		
		
		for(int i=0; i<str.length() && index<rows; i++) {
			char c = str.charAt(i);
			
			if(c>='0' && c<='9') {
				intNum=intNum*10+(int)(c-48);
			}else if((c==',' || c==']') && i-1>=0 && str.charAt(i-1)>='0'&& str.charAt(i-1)<='9') {
				_2DArray[index][countInt++]=intNum;
				intNum=0;
				if(countInt==cols) {
					countInt=0;
					index++;
				}
			}
		}
		
		return _2DArray;
	}
	
	public static char[][] stringTo2DArrayOfChar(String str, int rows, int cols){
		char[][] _2DArray = new char[rows][cols];
    	int indexRow = 0;
    	int indexCol = 0;
		
		for(int i=0; i<str.length() && indexRow<rows; i++) {
			char c = str.charAt(i);
			
			if(c!=',' && c!='[' && c!=']' && c!=' ') {
				if(indexCol==cols) {
					indexCol=0;
					indexRow++;
				}
				_2DArray[indexRow][indexCol++]=c;
			}
		}
		
		return _2DArray;
	}
	
	public static void main(String[] args) {
		// test cases for stringTo2DArrayOfChar
		String[] charStrings = new String[]{
				"[[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]",
				"[[0,1],[1,0]]",
				"[[0]]",
				"[[1,1],[1,1]]"
		};
		
		int[] charStrings_rows = new int[]{4, 2, 1, 2};
		int[] charStrings_cols = new int[]{5, 2, 1, 2};
		
		for (int i=0; i<charStrings.length; i++) {
			String chars = charStrings[i];
			char[][] array = stringTo2DArrayOfChar(chars, charStrings_rows[i], charStrings_cols[i]);
			
			System.out.print("[");
			for(int j=0; j<array.length; j++) {
				System.out.print("[");
				for(int k=0; k<array[j].length; k++) {
					if(k>0) System.out.print(",");
					System.out.print(array[j][k]);
				}
				System.out.print("]");
			}
			System.out.println("]\n");
		}
	}
		
}
