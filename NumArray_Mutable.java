package javachips;

public class NumArray_Mutable {
	// initiation runtime O(n); update runtime O(1); sumRange runtime O(sqrt(n))
    private int[] nums;
    private int blockLen;
    private int blocks;
    private int[] blockSums;
    
    public NumArray_Mutable(int[] nums) {
        this.nums=nums;
        this.blockLen=(int)Math.floor(Math.sqrt(this.nums.length));
        // System.out.println(this.blockLen);
        this.blocks=(int)Math.ceil((double)nums.length/blockLen);
        this.blockSums=new int[blocks];
        // System.out.println(this.blocks);
        for(int i=0; i<this.nums.length; i++){
            this.blockSums[i/this.blocks]+=this.nums[i];
            // System.out.println(this.blockSums[i/this.blocks]);
        }
    }
    
    public void update(int index, int val) {
        this.blockSums[index/this.blocks]=this.blockSums[index/this.blocks]-this.nums[index]+val;
        // System.out.println(this.blockSums[index/this.blocks]);
        this.nums[index]=val;
    }
    
    public int sumRange(int left, int right) {
        int blockStart=left/this.blocks;
        int blockEnd=right/this.blocks;
        
        int sum=0;
        for(int i=left; i<blockStart*this.blocks+this.blocks && i<=right; i++){
            sum+=this.nums[i];
        }
        
        for(int i=blockStart+1; i<blockEnd; i++){
            sum+=this.blockSums[i];
        }
        
        for(int i=blockEnd*this.blocks; blockStart<blockEnd && i<=right; i++){
            sum+=this.nums[i];
        }
        
        return sum;
    }
    
}


class TreeNode_SumRange{
    TreeNode_SumRange left;
    TreeNode_SumRange right;
    int leftBound;
    int rightBound;
    int val;
    
    public TreeNode_SumRange(int val, int leftBound, int rightBound){
        this.val=val;
        this.leftBound=leftBound;
        this.rightBound=rightBound;
    }
    
    public TreeNode_SumRange(int val, TreeNode_SumRange left, TreeNode_SumRange right, int leftBound, int rightBound){
        this.val=val;
        this.left=left;
        this.right=right;
        this.leftBound=leftBound;
        this.rightBound=rightBound;
    }
}

class NumArray_Mutable_Tree {
	private TreeNode_SumRange numTree;
	
	public NumArray_Mutable_Tree(int[] nums) {
	    this.numTree=buildTree(nums, 0, nums.length-1);
	}
	
	private TreeNode_SumRange buildTree(int[] nums, int left, int right){
	    if(left==right){
	        return new TreeNode_SumRange(nums[left], left, right);
	    }
	    
	    TreeNode_SumRange leftNode=buildTree(nums, left, (left+right)/2);
	    TreeNode_SumRange rightNode=buildTree(nums, (left+right)/2+1, right);
	    return new TreeNode_SumRange(leftNode.val+rightNode.val, leftNode, rightNode, leftNode.leftBound, rightNode.rightBound);
	}
	
	public void update(int index, int val) {
	    updateNumTree(this.numTree, index, val);
	}
	
	private void updateNumTree(TreeNode_SumRange node, int index, int val){
	    if(node.leftBound==node.rightBound) {
	        node.val=val;
	        return;
	    }
	    
	    int midBound=(node.leftBound+node.rightBound)/2;
	    if(index<=midBound) updateNumTree(node.left, index, val);
	    else updateNumTree(node.right, index, val);
	    
	    node.val=node.left.val+node.right.val;
	}
	
	public int sumRange(int left, int right) {
	    // find the closest common parent node
	    return getSumRange(this.numTree, left, right);
	}
	
	private int getSumRange(TreeNode_SumRange node, int left, int right){
	    if(left==node.leftBound && right==node.rightBound) return node.val;
	    
	    int split=(node.leftBound+node.rightBound)/2;
	    if(right<=split) return getSumRange(node.left, left, right);
	    else if(left>split) return getSumRange(node.right, left, right);
	    else return getSumRange(node.left, left, split)+getSumRange(node.right, split+1, right);
	 }
}