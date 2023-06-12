package javachips;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

// utility class
class ListNode{
	int val;
	ListNode next;
	
	public ListNode() {}
	
	public ListNode(int val) {
		this.val=val;
	}
	
}

public class LinkedListOfIntOps {
	// ---------------------------------------------------------
	// Given a singly linked list, 
	// return a random node's value from the linked list. 
	// Each node must have the same probability of being chosen.
	public static int getRandom_n(ListNode head) {
		// runtime O(n) memory O(1)
		int range=1;
        int index=0;
        int randomPick;
        int num=head.val;
        
        while(head!=null){
            randomPick=(int)(Math.random()*range++);
            if(randomPick<=index){
                index=randomPick;
                num=head.val;
            }
            head=head.next;
        }
        
        return num;
	}
	
	public static int getRandom_n_array(ListNode head) {
		// runtime O(n) memory O(n)
		int count=0;
        ListNode curr=head;
        while(curr!=null){
            count++;
            curr=curr.next;
        }
        int[] values=new int[count];
        curr=head;
        
        while(curr!=null){
            values[--count]=curr.val;
            curr=curr.next;
        }
        return values[(int)(Math.random()*values.length)];
    }
	
	public static int getRandom_n_arraylist(ListNode head) {
		// runtime O(n) memory O(n)
		ArrayList<Integer> values=new ArrayList<Integer>();
		while(head!=null){
            values.add(head.val);
            head=head.next;
        }
        return values.get((int)(Math.random()*values.size()));
    }
	
	// ---------------------------------------------------------
	// Given a linkedlist, return the linkedlist with all the odd nodes in front of the even nodes;
	// EX. Input: head = [1,2,3,4,5] Output: [1,3,5,2,4]
	public static ListNode oddEvenList(ListNode head) {
		// runtime O(n)
        if(head==null || head.next==null) return head;
        
        ListNode lastOdd=head;
        ListNode lastEven=head.next;
        ListNode firstEven=lastEven;
        if(lastEven!=null) {
            lastOdd.next=lastEven.next;
            lastEven.next=null;
        }
        
        while(lastOdd.next!=null){
            lastOdd=lastOdd.next;
            if(lastOdd.next!=null){
                lastEven.next=lastOdd.next;
                lastEven=lastEven.next;
                lastOdd.next=lastEven.next;
                lastEven.next=null;
            }
        }
        
        lastOdd.next=firstEven;
        return head;
    }
	
	// ---------------------------------------------------------
	public static void deleteNode(ListNode node) {
		// given a node in a linkedlist, with no head information, delete the given node from the linkedlist
		// the given node is guaranteed NOT the tail node of the list  
		node.val=node.next.val;
		node.next=node.next.next;
	}
	
	// check if a linkedlist is Palindrome
	// approach: modify first half of the list to be reversed order then compare with the second half
	// runtime O(n+n/2) additional memory usage O(1)
	public static boolean isPalindrome_reverselist(ListNode head) {
        ListNode reversedHalfStart = null;
        ListNode reversedHalfEnd = null;
        
        while(head!=null && head.next!=null){
            if(reversedHalfStart==null) {
                reversedHalfStart = head;
                reversedHalfEnd = head;
            }else{
                ListNode newHead = reversedHalfEnd.next;
                reversedHalfEnd.next = newHead.next;
                newHead.next = reversedHalfStart;
                reversedHalfStart = newHead;
            }
            head=head.next.next;
        }
        
        if(reversedHalfEnd==null) return true;
        
        if(head==null) head = reversedHalfEnd.next;
        else head = reversedHalfEnd.next.next;
        
        while(head!=null){
            if(reversedHalfStart.val!=head.val) return false;
            reversedHalfStart=reversedHalfStart.next;
            head=head.next;
        }
        return true;
        
    }
	
	// check if a linkedlist is Palindrome
	// only applicable when integers are between [0, 9]
	// runtime O(5n/2) additional memory usage O(n)
	public static boolean isPalindrome_stringbuilder(ListNode head) {
        StringBuilder str = new StringBuilder();
        
        while(head!=null){ // O(n)
            str.append(head.val);
            head=head.next;
        }
        
        int mid = (str.length()-1)/2;
        return str.substring(0, mid+1).equals(str.reverse().substring(0, mid+1)); // O(3n/2)
    }
	
	
	public static ListNode reverseList(ListNode head) {
		ListNode reversed = null;
        while(head!=null){
            ListNode newHead = head;
            head=head.next;
            newHead.next = reversed;
            reversed=newHead;
        }
        return reversed;
	}
	
	private static void reverseList_test(String testInput, String testOutput) {
		try {
			File inputFile = new File("src/javachips/"+testInput);
			File outputFile = new File("src/javachips/"+testOutput);
			
			Scanner inScan = new Scanner(inputFile);
			Scanner outScan = new Scanner(outputFile);
			
			while(inScan.hasNextLine() && outScan.hasNextLine()) {
				int listSize = inScan.nextInt();
				ListNode head = null;
				ListNode iter = head;
				
				System.out.print("Original List: ");
				while(listSize>0) {
					if(head==null) {
						head=new ListNode(inScan.nextInt());
						iter=head;
						System.out.print(iter.val);
					}else {
						iter.next=new ListNode(inScan.nextInt());
						iter=iter.next;
						System.out.print(", "+iter.val);
					}
					listSize--;
				}
				
				System.out.print("\nReversed List: ");
				ListNode reversedList = LinkedListOfIntOps.reverseList(head);
				boolean isCorrect = true;
				int reversedListSize = outScan.nextInt();
				
				while(reversedListSize>0 && reversedList!=null) {
					if(reversedListSize>1) System.out.print(reversedList.val+", ");
					else System.out.print(reversedList.val);
					
					if(outScan.nextInt()!=reversedList.val) isCorrect=false;
					
					reversedListSize--;
					reversedList=reversedList.next;
				}
				
				System.out.println();
				
				if(isCorrect) System.out.println("CORRECT\n");
				else System.out.println("NOT CORRECT\n");
				
			}
			
			inScan.close();
			outScan.close();
			
		}catch(Exception e) {
			System.out.println("ERROR:: INVALID FILE "+e);
		}
	}
	
	
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
	
	
	private static void removeElementsWithVal_test(String testInput, String testOutput) {
		try {
			File inputFile = new File("src/practice/"+testInput);
			File outputFile = new File("src/practice/"+testOutput);
		
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
				
				ListNode afterRemoval = LinkedListOfIntOps.removeElementsWithVal(head, val);
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
			System.out.println("ERROR:: INVALID FILE "+e);
		}
	}
	
	public static void main (String[] args) {
		System.out.println("LinkedListOps has the following functions: \n"
				+ "1. reverseList \n"
				+ "2. removeElementsWithVal \n"
				+ "\nPlease enter the numeric selection: ");
		Scanner inScan = new Scanner(System.in);
		
		try {
			int opSelection = inScan.nextInt();
			System.out.println("Please enter the input test .txt file name (include the .txt extension): ");
			
			try {
				String inputFileName = inScan.next();
				System.out.println("Now please enter the output test .txt file name (include the .txt extension): ");
				
				try {
					String outputFileName = inScan.next();
					if(opSelection==1) LinkedListOfIntOps.reverseList_test(inputFileName, outputFileName);
					else if(opSelection==2) LinkedListOfIntOps.removeElementsWithVal_test(inputFileName, outputFileName);
					
					inScan.close();
				}catch(Exception e) {
					System.out.println("ERROR:: INVALID OUTPUT TEST FILE. "+e);
				}
				
				
			}catch(Exception e) {
				System.out.println("ERROR:: INVALID INPUT TEST FILE. "+e);
			}
			
			
		}catch(Exception e) {
			System.out.println("ERROR:: INVALID SELECTION. "+e);
		}
	
	}
}
