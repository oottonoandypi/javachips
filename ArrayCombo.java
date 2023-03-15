package javachips;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class ArrayCombo {
	public static List<List<Integer>> getComboSum(int k, int n) { // runtime O((9-k+1)!*(9-k+2)!*...*9)
        List<List<Integer>> combos = new ArrayList<>();
        findCombos(combos, new ArrayList<Integer>(), k, n);
        return combos;
    }
    
    private static void findCombos(List<List<Integer>> res, List<Integer> combo, int k, int n){
        if(k==0) {
            if(n==0) res.add(new ArrayList<Integer>(combo));
        }else{
            int startNum=1;
            if(combo.size()>0) startNum=combo.get(combo.size()-1)+1;
            
            while(startNum<=9-k+1 && startNum<=n){
                combo.add(startNum);
                findCombos(res, combo, k-1, n-startNum);
                combo.remove(combo.size()-1);
                startNum++;
            }
        }
    }
    
    private static void getComboSum_test(Scanner inputScan, Scanner outputScan) {
    	while(inputScan.hasNextLine() && outputScan.hasNextLine()) {
    		int k=inputScan.nextInt();
    		int n=inputScan.nextInt();
    		
    		List<List<Integer>> res = getComboSum(k, n);
    		
    		int rows = outputScan.nextInt();
    		int cols = outputScan.nextInt();
    		String expected_str = outputScan.next();
    		int[][] expected = Sprinkles.stringTo2DArrayOfInt(expected_str, rows, cols);
    		
    		boolean ifCorrect = true;
    		
    		System.out.print("Result: [");
    		for(List<Integer> res_rows: res) {
    			System.out.print("[");
    			for(int i=0; i<res_rows.size(); i++) {
    				if(i>0) System.out.print(" ,");
    				System.out.print(res_rows.get(i));
    			}
    			System.out.print("]");
    		}
    		System.out.println("]");
    		
    		System.out.print("Expected: [");
    		for(int j=0; j<expected.length; j++) {
    			System.out.print("[");
    			for(int i=0; i<expected[0].length; i++) {
    				if(i>0) System.out.print(" ,");
    				System.out.print(expected[j][i]);
    				if(expected[j][i]!=res.get(j).get(i)) ifCorrect=false;
    			}
    			System.out.print("]");
    		}
    		System.out.println("]");
    		
    		if(ifCorrect) System.out.println("CORRECT\n");
    		else System.out.println("NOT CORRECT\n");
    	}
    	
    }
    
    public static void main(String[] args) {
    	System.out.println("Please enter the directory path of your input file: ");
    	Scanner userinputScan = new Scanner(System.in);
    	String inputFilePath = userinputScan.next();
    	try {
    		File inputfile = new File(inputFilePath);
    		Scanner inputScan = new Scanner(inputfile);
    		System.out.println("Please enter the directory path of your output file: ");
    		String outputFilePath = userinputScan.next();
    		userinputScan.close();
    		try {
    			File outputfile = new File(outputFilePath);
        		Scanner outputScan = new Scanner(outputfile);
        		
        		getComboSum_test(inputScan, outputScan);
        		
        		outputScan.close();
    		}catch(Exception e) {
        		System.out.println("ERROR:: INVALID OUTPUT FILE PATH. "+e);
        	}
    		
    		inputScan.close();
    	}catch(Exception e) {
    		System.out.println("ERROR:: INVALID INPUT FILE PATH. "+e);
    	}
    	
    }
}
