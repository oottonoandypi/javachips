package javachips;

import java.util.List;
import java.util.ArrayList;

public class ArrayAndBinarySearchTreeOps {
	/*
	 * Given an array nums that represents a permutation of integers from 1 to n. 
	 * Construct a binary search tree (BST) by inserting the elements of nums in order into an initially empty BST. 
	 * Find the number of different ways to reorder nums so that the constructed BST is identical to that formed from the original array nums.
	 * 
	 * Note: The result is returned modulo 10^9 + 7
	 */
	private long[][] records; // store the permutations of count1 and count2
    private int mod=1000000007;
    
    // Approach #2: Same idea as #1 but building a BST tree using input data
    // More efficient than building arraylist in every recursive call of findNumOfWays_
    public int numOfWaysToSameBST_BST(int[] nums) {
        BSTNode root=new BSTNode(nums[0]);
        for(int i=1; i<nums.length; i++) root.insert(new BSTNode(nums[i]));
        records=new long[nums.length+1][nums.length+1];
        return findNumOfWays_BST(root)-1;
    }
    
    private int findNumOfWays_BST(BSTNode node){
        if(node==null) return 1;
        
        long smallerCount=(long)findNumOfWays_BST(node.left);
        long largerCount=(long)findNumOfWays_BST(node.right);
        
        int smallerTreeSize=0;
        int largerTreeSize=0;
        if(node.left!=null) smallerTreeSize=node.left.size;
        if(node.right!=null) largerTreeSize=node.right.size;
        
        countPermutations(smallerTreeSize, largerTreeSize);
        
        long combinedCount=((((smallerCount%mod)*(largerCount%mod))%mod)*(records[smallerTreeSize][largerTreeSize]%mod))%mod;
        // System.out.println("combined: "+combinedCount);
        return (int)(combinedCount%mod);
    }
    
    
    // Intuition:
    // Approach #1: 
	public int numOfWaysToSameBST_arraylist(int[] nums) {
        records=new long[nums.length+1][nums.length+1];
        List<Integer> numsArray=new ArrayList<Integer>();
        for(int n: nums) numsArray.add(n);
        return findNumOfWays_arraylist(numsArray)-1;
    }
    
    private int findNumOfWays_arraylist(List<Integer> list){
        if(list.size()==0) return 1;
        int p=list.get(0);
        
        List<Integer> smallerList=new ArrayList<Integer>();
        List<Integer> largerList=new ArrayList<Integer>();
        int integer;
        for(int i=1; i<list.size(); i++){
            integer=list.get(i);
            if(integer<p) smallerList.add(integer);
            else largerList.add(integer);
        }
        int smallerListSize=smallerList.size();
        int largerListSize=largerList.size();
        //System.out.println(smallerList.size());
        //System.out.println(largerList.size());
        
        long smallerCount=(long)findNumOfWays_arraylist(smallerList);
        long largerCount=(long)findNumOfWays_arraylist(largerList);
        countPermutations(smallerListSize, largerListSize);
        // System.out.println("countWays: "+countWays);
        long combinedCount=((((smallerCount%mod)*(largerCount%mod))%mod)*(records[smallerListSize][largerListSize]%mod))%mod;
        // System.out.println("combined: "+combinedCount);
        return (int)(combinedCount%mod);
    }
    
    private void countPermutations(int count1, int count2){
        if(records[count1][count2]>0) return;
        else if(count1==0 || count2==0) {
            records[count1][count2]=1;
            records[count2][count1]=1;
        }else{
        	countPermutations(count1-1,count2);
        	countPermutations(count1,count2-1);

            records[count1][count2]=(records[count1-1][count2]+records[count1][count2-1])%mod;
            records[count2][count1]=records[count1][count2];
        }
        
    }
}

class BSTNode{
    int val;
    BSTNode left;
    BSTNode right;
    int size;
    public BSTNode(int val){
        this.val=val;
        this.left=null;
        this.right=null;
        this.size=1;
    }
    
    public void insert(BSTNode node){
        if(node.val<this.val){
            if(this.left==null) this.left=node;
            else this.left.insert(node);
        }else{
            if(this.right==null) this.right=node;
            else this.right.insert(node);
        }
        
        this.size++;
    }
}