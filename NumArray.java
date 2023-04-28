package javachips;

public class NumArray {
	private int[] sumsTillIndex;
    public NumArray(int[] nums) {
        sumsTillIndex=new int[nums.length];
        for(int i=0; i<nums.length; i++){
            if(i==0) sumsTillIndex[i]=nums[i];
            else sumsTillIndex[i]=nums[i]+sumsTillIndex[i-1];
            // System.out.println(sumsTillIndex[i]);
        }
    }
    
    public int sumRange(int left, int right) {
        if(left==0) return sumsTillIndex[right];
        else return sumsTillIndex[right]-sumsTillIndex[left-1];
    }
}
