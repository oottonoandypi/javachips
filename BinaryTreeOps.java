package javachips;

import java.util.List;
import java.util.ArrayList;

public class BinaryTreeOps {
	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	
	private static int kth = -1; // local variable for kthSmallest
	private static int K=1; // local variable for kthSmallest
    // find the kth smallest integer from the Tree
    // DFS without using arraylist to store values in order.
	public static int kthSmallest(TreeNode root, int k) {
		K=k;
        searchKth(root);
        return kth;
    }
	// helper function for kthSmallest
    private static void searchKth(TreeNode root){
        if(root==null) return;
        if(kth==-1){
            searchKth(root.left);
            if(K==1 && kth==-1) {
                kth=root.val;
            }else{
                K--;
                searchKth(root.right);
            }  
        }
    }
    
    // find the kth smallest integer from the Tree
    // DFS without using arraylist to store values in order.
    public static int kthSmallest_arraylist(TreeNode root, int k) {
        List<Integer> inorder=new ArrayList<Integer>();
        treeToArray(root, inorder);
        return inorder.get(k-1);
    }
    // helper function for kthSmallest_arraylist
    private static void treeToArray(TreeNode root, List<Integer> inorder){
        if(root==null) return;
        
        treeToArray(root.left, inorder);
        inorder.add(root.val);
        treeToArray(root.right, inorder);
    }
	
	public static TreeNode invertTree(TreeNode root) {
        if(root==null) return root;
        TreeNode right=invertTree(root.left);
        root.left=invertTree(root.right);
        root.right=right;
        return root;
    }
}
