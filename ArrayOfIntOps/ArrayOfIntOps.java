package practice;

public class ArrayOfIntOps {
	// find the kth largest num in the array
	public int findKthLargest(int[] nums, int k) {
        int rangeStart=0;
        int rangeEnd=nums.length-1;
        int kth=k;
        
        while(rangeStart<=rangeEnd){
            int pivot = findPivot(nums,rangeStart, rangeEnd);
            if(rangeEnd-pivot+1==kth) return nums[pivot];
            else if(rangeEnd-pivot+1>kth) rangeStart=pivot+1;
            else{
                kth-=rangeEnd-pivot+1;
                rangeEnd=pivot-1;
            }
        }
        return -1;
    }
    
	// helper func for findKthLargest
    private int findPivot(int[] nums, int rangeStart, int rangeEnd){
        int randomPivot =  (int) ((Math.random() * (rangeEnd - rangeStart)) + rangeStart);
        // int randomPivot =  (rangeStart+rangeEnd)/2;
        int randomPivot_num = nums[randomPivot];
        
        swap(nums, randomPivot, rangeEnd);
        randomPivot=rangeEnd;
        
        for(int i=rangeStart; i<randomPivot; i++){
            int n=nums[i];
            if(n>randomPivot_num){
                swap(nums, randomPivot, i);
                swap(nums, i, randomPivot-1);
                randomPivot--;
                i--;
            }
        }
        return randomPivot;
    }
    
    // helper func for findKthLargest
    private void swap(int[] nums, int indexa, int indexb){
        // System.out.println("indexa: "+indexa);
        // System.out.println("indexb: "+indexb);
        int temp = nums[indexa];
        nums[indexa] = nums[indexb];
        nums[indexb] = temp;
    }
	
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
