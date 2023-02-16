package practice;
import java.util.Scanner;
import java.io.IOException;

public class ReverseBitCalculator {
	public int reverse32Bit(int n) {
		int res =0;
		for(int i=0; i<32; i++) {
			res=res*2+(n&1);
			n>>=1;
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scan=new Scanner(System.in);
		
		System.out.println("Please enter the Integer that you'd like to convert to its reversed-bits integer. \n(Note: the converter will use 32-bit binary number system.)");
        // Reading input using readLine
        int num = scan.nextInt();
        scan.close();
        
        ReverseBitCalculator newConverter=new ReverseBitCalculator();
        
        int reversedNum=newConverter.reverse32Bit(num);
        System.out.println("The reversed Integer is: "+reversedNum);
	}
}
