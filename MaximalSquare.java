package javachips;

import java.util.Scanner;
import java.io.File;

public class MaximalSquare {
	
	public static int getMaximalSquareSpace_1Space(char[][] matrix) { // runtime O(matrix rows * cols) space O(1)
        int maxLen = 0;
        int len = 0;
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(matrix[i][j]=='1'){
                    if(maxLen<1) maxLen=1;
                    len = 0;
                    if(i-1>=0 && j-1>=0) len = Math.min(matrix[i-1][j]-48, matrix[i][j-1]-48);
                    
                    //System.out.println(len);
                    if(len>0){
                        if(matrix[i-len][j-len]>'0') len++;
                        matrix[i][j]=(char)(len+48);
                        
                        if(len>maxLen) maxLen=len;
                    }
                }
            }
        }
        
        return maxLen*maxLen;
    }
	
	public static int getMaximalSquareSpace_3DArraySpace(char[][] matrix) { // iterative approach with additional space usage of a 3D array; a cleaner code
		// runtime O(matrix rows * cols) space O(matrix rows * cols * 2)
        // the 3rd dimension is the [left, top]
        int[][][] marks = new int[matrix.length][matrix[0].length][2];
        int maxLen = 0;
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(matrix[i][j]=='1'){
                    if(maxLen<1) maxLen=1;
                    
                    if(i-1>=0) marks[i][j][1]=marks[i-1][j][1];
                    if(j-1>=0) marks[i][j][0]=marks[i][j-1][0];
                    
                    int len = Math.min(marks[i][j][0], marks[i][j][1]);
                    if(len==0){
                        marks[i][j][0]=1;
                        marks[i][j][1]=1;
                    }else{
                        if(matrix[i-len][j-len]>'0') len++;
                        marks[i][j][0]=len;
                        marks[i][j][1]=len;
                        
                        if(len>maxLen) maxLen=len;
                    }
                }
            }
        }
        
        return maxLen*maxLen;
    }
	
	
	/* public static int getMaximalSquareSpace_3DArraySpace_deprecated(char[][] matrix) { // iterative approach with additional space usage of a 3D array; has debug prints
        // the 3rd dimension is the [left, top]
        int[][][] marks = new int[matrix.length][matrix[0].length][2];
        int maxLen = 0;
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                //System.out.println("i: "+i);
                //System.out.println("j: "+j);
                //System.out.println("matrix[i][j]: "+matrix[i][j]);
                
                if(matrix[i][j]=='1'){
                    if(maxLen<1) maxLen=1;
                    
                    if(i-1>=0) marks[i][j][1]=marks[i-1][j][1];//+(int)(matrix[i-1][j]-48);
                    if(j-1>=0) marks[i][j][0]=marks[i][j-1][0];//+(int)(matrix[i][j-1]-48);
                    
                    //System.out.println("marks[i][j][0]: "+marks[i][j][0]);
                    //System.out.println("marks[i][j][1]: "+marks[i][j][1]);
                    
                    if(marks[i][j][0]>0 && marks[i][j][1]>0){
                        int len = Math.min(marks[i][j][0], marks[i][j][1]);
                        marks[i][j][0]=len;
                        marks[i][j][1]=len;
                        
                        if(matrix[i-len][j-len]>'0'){
                            marks[i][j][0]++;
                            marks[i][j][1]++;
                        }
                        
                        if(marks[i][j][0]>maxLen) maxLen=marks[i][j][0];
                        //System.out.println(maxLen);
                    }else{
                        marks[i][j][0]=1;
                        marks[i][j][1]=1;
                    }
                }
            }
        }
        
        return maxLen*maxLen;
    } */
	
	public static void main(String[] args) {
		try {
			File inputFile = new File("txt/MaximalSquare/MaximalSquare_Testinput.txt");
			File outputFile = new File("txt/MaximalSquare/MaximalSquare_Testoutput.txt");
			Scanner inputScan = new Scanner(inputFile);
			Scanner outputScan = new Scanner(outputFile);
			
			while(inputScan.hasNextLine() && outputScan.hasNextLine()) {
				int rows = inputScan.nextInt();
				int cols = inputScan.nextInt();
				String str = inputScan.next();
				
				int space = getMaximalSquareSpace_1Space(Sprinkles.stringTo2DArrayOfChar(str, rows, cols));
				int expected_space = outputScan.nextInt();
				
				System.out.println("Result: "+space+"; Expected: "+expected_space);
				if(space==expected_space) System.out.println("CORRECT\n");
				else System.out.println("NOT CORRECT\n");
			}
			inputScan.close();
			outputScan.close();
		}catch(Exception e) {
			System.out.println("ERROR:: INVALID FILE. "+e);
		}
	}
	
}
