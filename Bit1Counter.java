package javachips;
import java.util.Scanner;

public class Bit1Counter {
	public int countBit1(int n) {
		int count=0;
        for(int i=0; i<32; i++){
            if((n&1)==1) count++;
            n>>=1;
        }
        return count;
	}
	
	public static void main(String[] args) {
		System.out.println("Please enter the integer that you'd like to count 1's.\n(Note: Bit1Counter counts 32-bit integer.)");
		Scanner scan = new Scanner(System.in);
		Bit1Counter counter = new Bit1Counter();
		try {
			int count = counter.countBit1(scan.nextInt());
			scan.close();
			System.out.println("Result: "+count+" bits of 1's.");
		}catch(Exception e) {
			System.out.println("ERROR:: "+e);
		}
		
	}
}
