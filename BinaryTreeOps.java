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
	
	// --------------------------------------------------------------------------------------
	// binaryTreePaths find all the root->leaf paths and return a list of strings in format of "nodeValue1->nodeValue2->...->nodeValueN"
	public static List<String> binaryTreePaths(TreeNode root) {
        // preorder dfs; runtime O(tree size+ edges) -> O(n)
        List<String> res=new ArrayList<String>();
        buildPaths(res, new StringBuilder(), root);
        return res;
    }
    // helper function for binaryTreePaths
    private static void buildPaths(List<String> res, StringBuilder path, TreeNode node){
        int valLen=0;
        if(path.length()>0){
            path.append("->");
            valLen+=2;
        }
        path.append(node.val);
        if(node.val<0) valLen++;
        
        valLen+=(int)(Math.log10(Math.abs(node.val))+1);
        
        if(node.left==null && node.right==null){
            res.add(path.toString());
            path.delete(path.length()-valLen, path.length());
            return;
        }
        
        if(node.left!=null) buildPaths(res, path, node.left);
        if(node.right!=null) buildPaths(res, path, node.right);
        
        path.delete(path.length()-valLen, path.length());
    }
	
	// --------------------------------------------------------------------------------------
	// helper local variable for the lowestCommonAncestor_dfs function below
	private static TreeNode lca=null;
    // find the lowest common ancestor for the given 2 nodes in a binary tree
    public static TreeNode lowestCommonAncestor_dfs(TreeNode root, TreeNode p, TreeNode q) {
        // lowest ancestor of each node is itself
        // when reaching a node that has both targets visited as its children on left or right
        //.  that is the lowestCommonAncestor;
        // DFS approach postorder; runtime O(n)
        searchLCA(root, p.val, q.val);
        return lca;
    }
    // helper function for the lowestCommonAncestor_dfs function above
    private static boolean searchLCA(TreeNode node, int pVal, int qVal){
        if(node==null) return false;
        
        boolean left=searchLCA(node.left, pVal, qVal);
        boolean right=searchLCA(node.right, pVal, qVal);
        
        if(node.val==pVal){
            if(left || right) lca=node;
            return true;
        }else if(node.val==qVal){
            if(left || right) lca=node;
            return true;
        }
        
        if(left && right && lca==null) lca=node;
        
        return left || right;
    }
	
    // --------------------------------------------------------------------------------------
	private static int kth = -1; // local variable for the kthSmallest function below
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
    
    // --------------------------------------------------------------------------------------
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
    
    
    // --------------------------------------------------------------------------------------
	public static TreeNode invertTree(TreeNode root) {
        if(root==null) return root;
        TreeNode right=invertTree(root.left);
        root.left=invertTree(root.right);
        root.right=right;
        return root;
    }
}
