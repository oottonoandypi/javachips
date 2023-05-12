package javachips;

public class GreedyHouseRobber2 {
	// Utility inner class
	class TreeNode {
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
	
	// There is only one entrance to this area, called root.
	// Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses were broken into on the same night.
	// Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.
	public int maxStealAmount_recur(TreeNode root) {
		// Recursion approach: For every house, the robber could choose to skip and rob its left and right neighbors OR rob this house and its neighbors' neighbors
		// Bottom Up, record combined max steal amount when the robber stopped at the house
		// To avoid double visits, record visited steal amount in negative values
		// Time complexity: O(6N)
		safeRobRecur(root);
        return root.val*(-1);
    }
    
    private void safeRobRecur(TreeNode node){
        if(node.val<0) return;
        
        int includeNode=0;
        int skipNode=0;
        
        if(node.left!=null){
            if(node.left.left!=null){
            	safeRobRecur(node.left.left);
                includeNode+=Math.abs(node.left.left.val);
            }
            if(node.left.right!=null){
            	safeRobRecur(node.left.right);
                includeNode+=Math.abs(node.left.right.val);
            }
            safeRobRecur(node.left);
            skipNode+=Math.abs(node.left.val);
        }
        
        if(node.right!=null){
            if(node.right.left!=null){
            	safeRobRecur(node.right.left);
                includeNode+=Math.abs(node.right.left.val);
            }
            if(node.right.right!=null){
            	safeRobRecur(node.right.right);
                includeNode+=Math.abs(node.right.right.val);
            }
            safeRobRecur(node.right);
            skipNode+=Math.abs(node.right.val);
        }
        
        node.val=Math.max(includeNode+node.val, skipNode)*(-1);
    }
}
