package javachips;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class TreeNode{ // helper class of TreeNode data structure
	int val;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(int val) {
		this.val=val;
	}
	
}

public class BinaryTreeRightViewer {
	
	private int maxDepth=-1;
	
	public List<Integer> rightTopDown(TreeNode root){
		List<Integer> rightNodes = new ArrayList<Integer>();
        searchNode(rightNodes, root, 0);
        return rightNodes;
	}
	
	private void searchNode(List<Integer> rightNodes, TreeNode root, int depth){
        if(root==null) return;
        
        if(depth>maxDepth){
        	rightNodes.add(root.val);
            maxDepth=depth; 
        }
        
        searchNode(rightNodes, root.right, depth+1);
        searchNode(rightNodes, root.left, depth+1);
    }
	
	
	
	public static void main(String[] args) {
		try {
			File testInputFile = new File("src/practice/BinaryTreeRightViewer_TestInput.txt");
			File testOutputFile = new File("src/practice/BinaryTreeRightViewer_TestOutput.txt");
		
			Scanner inputScan = new Scanner(testInputFile);
			Scanner outputScan = new Scanner(testOutputFile);
			
			while(inputScan.hasNextLine() && outputScan.hasNextLine()) {
				List<TreeNode> nodes = new ArrayList<TreeNode>();
				int currNodeIndex = -1;
				int nodeCount = inputScan.nextInt();
				TreeNode root=null;
				
				System.out.print("Input: [ ");
				int i=0;
				for(i=0; i<nodeCount; i+=2) {
					if(inputScan.hasNextInt()) {
						TreeNode newLeftNode = new TreeNode(inputScan.nextInt());
						System.out.print(newLeftNode.val+" ");
						if(nodes.size()==0) {
							root=newLeftNode;
							nodes.add(newLeftNode);
						}else {
							nodes.get(currNodeIndex).left=newLeftNode;
							nodes.add(newLeftNode);
							
							if(inputScan.hasNextInt()) {
								TreeNode newRightNode=new TreeNode(inputScan.nextInt());
								System.out.print(newRightNode.val+" ");
								nodes.get(currNodeIndex).right=newRightNode;
								nodes.add(newRightNode);
							}else if(inputScan.hasNext()) {
								System.out.print(inputScan.next()+" ");
							}
						}
					}else if(inputScan.hasNext()){
						System.out.print(inputScan.next()+" ");
						if(inputScan.hasNextInt()) {
							TreeNode newRightNode=new TreeNode(inputScan.nextInt());
							System.out.print(newRightNode.val+" ");
							nodes.get(currNodeIndex).right=newRightNode;
							nodes.add(newRightNode);
						}
						
					}
					currNodeIndex++;
				}
				if(i<nodeCount && inputScan.hasNextInt()) {
					TreeNode newLeftNode = new TreeNode(inputScan.nextInt());
					nodes.get(currNodeIndex).left=newLeftNode;
					System.out.print(newLeftNode.val+" ");
				}
				System.out.println("]");
			
				BinaryTreeRightViewer viewer=new BinaryTreeRightViewer();
				List<Integer> rightViewNodes = viewer.rightTopDown(root);
				
				
				boolean isMatch = true;
				
				System.out.print("Output: [ ");
				for(int rightNode: rightViewNodes) {
					if(rightNode==outputScan.nextInt()) System.out.print(rightNode+" ");
					else isMatch=false;
				}
				System.out.println("] \n"+"Compare to test output file if match: "+isMatch);
			
			}
			inputScan.close();
			outputScan.close();
			
		
		}catch(IOException e) {
			System.out.println("FILE ERROR:: "+e);
		}
		
	}
}


