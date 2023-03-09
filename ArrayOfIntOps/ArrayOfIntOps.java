package practice;

public class ArrayOfIntOps {
	// minSubArratLen takes a target val and an array os integers;
	// returns the minimum length of a subarray of the array that has a sum >= target.
	public static int minSubArrayLen(int target, int[] nums) { // runtime O(n)
        int minLen =nums.length+1;
        int startIndex = 0;
        int endIndex = startIndex;
        int sum = nums[startIndex];
        
        while(startIndex<nums.length){
            while(endIndex+1<nums.length && sum+nums[endIndex+1]<target) {
                sum+=nums[++endIndex];
            }
            
            if(endIndex+1<nums.length && sum<target) {
                sum+=nums[++endIndex];
                minLen=Math.min(endIndex-startIndex+1, minLen);
                sum-=nums[startIndex++];
            }else{
                if(sum>=target){
                    minLen=Math.min(endIndex-startIndex+1, minLen);
                    sum-=nums[startIndex++];
                }else startIndex=nums.length;
            }
        }
        
        if(minLen>nums.length) return 0;
        return minLen;
    }
	
	public static void main(String[] args) {
		// test to be updated..
	}
}
