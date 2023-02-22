package practice;
import java.io.File;
import java.util.Scanner;


class ListNode{
	int val;
	ListNode next;
	
	public ListNode() {}
	
	public ListNode(int val) {
		this.val=val;
	}
	
}

public class LinkedListOps {
	public static ListNode removeElementsWithVal(ListNode head, int val) {
		ListNode prev=new ListNode();
        prev.next=head;
        
        ListNode iter=prev;
        
        while(iter.next!=null){
            if(iter.next.val!=val) iter=iter.next;
            else iter.next=iter.next.next;
        }
        return prev.next;
	}
	
	public static void main (String[] args) {
		try {
			File inputFile = new File("src/practice/LinkedListOps_RemoveElementsWithVal_TestInput.txt");
			File outputFile = new File("src/practice/LinkedListOps_RemoveElementsWithVal_TestOutput.txt");
		
			Scanner inputScan = new Scanner(inputFile);
			Scanner outputScan = new Scanner(outputFile);
			
			while(inputScan.hasNextLine()) {
				int val = inputScan.nextInt();
				int listSize = inputScan.nextInt();
				int count = 0;
				ListNode head=null;
				ListNode iter=null;
				System.out.print("List: [ ");
				while(inputScan.hasNextInt() && count<listSize) {
					if(head==null) {
						head=new ListNode(inputScan.nextInt());
						iter=head;
					} else {
						iter.next = new ListNode(inputScan.nextInt());
						iter=iter.next;
					}
					count++;
					System.out.print(iter.val+", ");
				}
				System.out.println("] "+val);
				
				ListNode afterRemoval = LinkedListOps.removeElementsWithVal(head, val);
				boolean isMatchOutput = true;
				
				System.out.print("After removing "+val+", the list looks like this: [ ");
				int countOutput = 0;
				if(outputScan.hasNextLine()) {
					int outputCountExpected = outputScan.nextInt();
					
					while(afterRemoval!=null) {
						if(countOutput<=outputCountExpected) {
							if(outputScan.hasNextInt() && afterRemoval.val != outputScan.nextInt()) isMatchOutput=false;
						}else isMatchOutput=false;
						System.out.print(afterRemoval.val+", ");
						afterRemoval=afterRemoval.next;
						countOutput++;
					}
				}
				
				System.out.print("] ");
				if(isMatchOutput) System.out.println("MATCH");
				else System.out.println("NOT MATCH");
			}
			
			inputScan.close();
			outputScan.close();
		}catch(Exception e) {
			System.out.println("ERROR:: "+e);
		}
		
	
	}
}
