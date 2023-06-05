package javachips;

import java.util.List;

// helper class N_aryTreeNode that every node has a value and a list of children
class N_aryTreeNode {
    public int val;
    public List<N_aryTreeNode> children;

    public N_aryTreeNode() {}

    public N_aryTreeNode(int _val) {
        val = _val;
    }

    public N_aryTreeNode(int _val, List<N_aryTreeNode> _children) {
        val = _val;
        children = _children;
    }
};

public class N_aryTreeOps {
	// Given the root of a n-ary tree
	// Returns the max. depth
	public int maxDepth(N_aryTreeNode root) {
        return searchMaxDepth(root);
    }
    // main work for the maxDepth method ^^
    private int searchMaxDepth(N_aryTreeNode node){
    	// runtime O(n) Memory O(n)
        if(node==null) return 0;
        if(node.children.isEmpty()) return 1;
        
        int longestDepth=0;
        for(int i=0; i<node.children.size(); i++){
            longestDepth=Math.max(longestDepth, searchMaxDepth(node.children.get(i)));
        }
        
        return longestDepth+1;
    }
}