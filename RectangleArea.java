package javachips;
import java.util.Scanner;
import java.io.File;

public class RectangleArea {
	public static int getTotalArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int total = (ax2-ax1)*(ay2-ay1)+(bx2-bx1)*(by2-by1);
        int overlapX=Math.min(ax2, bx2)-Math.max(ax1, bx1);
        int overlapY=Math.min(ay2, by2)-Math.max(ay1, by1);
        if(overlapX>0 && overlapY>0 && overlapX*overlapY>=0) return total-overlapX*overlapY;
        else return total;
	}
	
	public static void main(String[] args) {
		System.out.println("Please enter the directory path of the input file: ");
		Scanner userInputScan = new Scanner(System.in);
		try {
			File inputFile = new File(userInputScan.next());
			Scanner inputFileScan = new Scanner(inputFile);
			System.out.println("Please enter the directory path of the output file: ");
			
			try {
				File outputFile = new File(userInputScan.next());
				Scanner outputFileScan = new Scanner(outputFile);
				userInputScan.close();
				
				while(inputFileScan.hasNextLine() && outputFileScan.hasNextLine()) {
					int ax1=inputFileScan.nextInt();
					int ay1=inputFileScan.nextInt();
					int ax2=inputFileScan.nextInt();
					int ay2=inputFileScan.nextInt();
					int bx1=inputFileScan.nextInt();
					int by1=inputFileScan.nextInt();
					int bx2=inputFileScan.nextInt();
					int by2=inputFileScan.nextInt();
					
					int area = getTotalArea(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
					int expectedArea = outputFileScan.nextInt();
					
					System.out.println("Total area is "+area);
					System.out.println("Expected total area is: "+expectedArea);
					System.out.println((area==expectedArea)+"\n");
				}
				outputFileScan.close();
				inputFileScan.close();
			}catch(Exception e) {
				System.out.println("INVALID OUTPUT FILE:: "+e);
			}
		}catch(Exception e) {
			System.out.println("INVALID INPUT FILE:: "+e);
		}
	}
}
