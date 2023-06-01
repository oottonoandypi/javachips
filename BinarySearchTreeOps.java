package javachips;

import java.util.ArrayList;

public class BinarySearchTreeOps {
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
	
	// ---------------------------------------------------------
	// Find Mode: The value(s) that appear most frequent in a BST
	private static int mostFreq=0;
    private static int count=0;
    private static TreeNode prev=null;
    private static ArrayList<Integer> modes=new ArrayList<Integer>();
    
    // in-order DFS approach
    // runtime O(n); Memory usage O(1)
    public static int[] findMode_InOrder(TreeNode root) {
    	searchModes_InOrder(root);
        
        if(prev!=null && count>=mostFreq){
            if(count>mostFreq) {
                mostFreq=count;
                modes.clear();
            }
            modes.add(prev.val);
        }
        
        int[] res=new int[modes.size()];
        for(int i=0; i<modes.size(); i++){
            res[i]=modes.get(i);
        }
        return res;
    }
    
    private static void searchModes_InOrder(TreeNode node){
        if(node==null) return;
        searchModes_InOrder(node.left);
        
        if(prev!=null){
            if(prev.val==node.val) count++;
            else{
                if(count>=mostFreq){
                    if(count>mostFreq) {
                        mostFreq=count;
                        modes.clear();
                    }
                    modes.add(prev.val);
                }
                prev=node;
                count=1;
            }
        }else{
            prev=node;
            count=1;
        }
        
        searchModes_InOrder(node.right);
    }
	
    
    // post-order DFS apprach
    // runtime O(n); memory usage O(n)
    public static int[] findMode_PostOrder(TreeNode root) {
    	searchModes_PostOrder(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        int[] res=new int[modes.size()];
        for(int i=0; i<modes.size(); i++){
            res[i]=modes.get(i);
        }
        return res;
    }
    
    private static int[] searchModes_PostOrder(TreeNode node, int smallest, int largest){
        if(node==null) return new int[2];
        
        int[] leftModes=searchModes_PostOrder(node.left, smallest, node.val);
        int[] rightModes=searchModes_PostOrder(node.right, node.val, largest);
        
        int[] counts=new int[2];
        if(node.val>smallest && node.val<largest){
            int count=1;
            count+=leftModes[1];
            count+=rightModes[0];
            if(count>=mostFreq) {
                if(count>mostFreq){
                    modes.clear();
                    mostFreq=count;
                }
                modes.add(node.val);
            }
        }
        counts[0]=leftModes[0];
        counts[1]=rightModes[1];
        
        if(node.val==smallest) counts[0]+=rightModes[0]+1;
        if(node.val==largest) counts[1]+=leftModes[1]+1;
        
        return counts;
    }
    
	// ---------------------------------------------------------
	
	public static TreeNode lowestCommonAncestor_iter(TreeNode root, TreeNode p, TreeNode q) {
        // binery search; runtime O(lgn) iteration approach using while loop; O(1) additional memory usage
        int min = Math.min(p.val, q.val);
        int max = Math.max(p.val, q.val);
        
        while(root!=null && (root.val<min || root.val>max)){
            if(root.val<min) root=root.right;
            else if(root.val>max) root=root.left;
        }
        
        return root;
    }
	
	public static TreeNode lowestCommonAncestor_recur(TreeNode root, TreeNode p, TreeNode q) {
        // binery search O(lgn) using recursion -> DFT preorder
        int min = Math.min(p.val, q.val);
        int max = Math.max(p.val, q.val);
        
        return findLCA(root, min, max);
    }
	
	// ---------------------------------------------------------
    
    private static TreeNode findLCA(TreeNode root, int min, int max){
        if(root==null) return null;
        if(root.val>=min && root.val<=max) return root;
        if(root.val<min) return findLCA(root.right, min, max);
        else return findLCA(root.left, min, max);
    }
}
