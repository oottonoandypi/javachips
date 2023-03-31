package javachips;

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
    
    private static TreeNode findLCA(TreeNode root, int min, int max){
        if(root==null) return null;
        if(root.val>=min && root.val<=max) return root;
        if(root.val<min) return findLCA(root.right, min, max);
        else return findLCA(root.left, min, max);
    }
}
