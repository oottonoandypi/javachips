package javachips;

import java.io.File;
import java.util.Scanner;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ArrayOfIntOps {
	// find all the majority numbers that appear more than Math.floor(nums.length/3)
	public static List<Integer> findMajorityNums_optimized(int[] nums){
		// // runtime O(n), additional memory usage O(1)
		List<Integer> majorities = new ArrayList<Integer>();
        
        int appearAtLeast = nums.length/3+1;
        // maxPossibleMajorityNums = nums.length/appearAtLeast, which is 2 in this case
        int[] majorityNums = new int[2];
        int[] countMajorityNums=new int[2];
        
        for (int i=0; i<nums.length; i++){
            if(nums[i]==majorityNums[0]) countMajorityNums[0]++;
            else if(nums[i]==majorityNums[1]) countMajorityNums[1]++;
            else if(countMajorityNums[0]==0) {
                majorityNums[0]=nums[i];
                countMajorityNums[0]=1;
            }else if(countMajorityNums[1]==0) {
                majorityNums[1]=nums[i];
                countMajorityNums[1]=1;
            }else{
                countMajorityNums[1]--;
                countMajorityNums[0]--;
            }
        }
        
        countMajorityNums[0]=0;
        countMajorityNums[1]=0;
        
        for(int num: nums){
            if(num==majorityNums[0]) countMajorityNums[0]++;
            else if(num==majorityNums[1]) countMajorityNums[1]++;
        }
        
        if(countMajorityNums[0]>=appearAtLeast) majorities.add(majorityNums[0]);
        if(countMajorityNums[1]>=appearAtLeast) majorities.add(majorityNums[1]);
        
        return majorities;
	}
	
	// find all the majority numbers that appear more than Math.floor(nums.length/3)
	public static List<Integer> findMajorityNums_hashmap(int[] nums){
		// // runtime O(n), additional memory usage O(n)
		HashMap<Integer, Integer> numAppeared = new HashMap<Integer, Integer>();
        List<Integer> majorityNums = new ArrayList<Integer>();
        int minReq = nums.length/3;
        for(int num: nums){
            if(numAppeared.containsKey(num)) {
                if(numAppeared.get(num)==minReq) majorityNums.add(num);
                numAppeared.put(num, numAppeared.get(num)+1);
            }else{
                if(minReq==0) majorityNums.add(num);
                numAppeared.put(num, 1);
            }
        }
        
        return majorityNums;
	}
	
	// find all the majority numbers that appear more than Math.floor(nums.length/3)
	public static List<Integer> findMajorityNums_sort(int[] nums){
		// with sort, runtime O(nlgn+n), additional memory usage O(1)
		Arrays.sort(nums);
        List<Integer> majorityNums = new ArrayList<Integer>();
        if(nums.length==0) return majorityNums;
        else if(nums.length==1){
            majorityNums.add(nums[0]);
            return majorityNums;
        }
        int startRange = 0;
        int minReq = nums.length/3;
        for(int i=1; i<nums.length; i++){
            if(nums[i]>nums[startRange]) {
                if(i-startRange>minReq) majorityNums.add(nums[startRange]);
                startRange = i;
            }
        }
        if(nums.length-startRange>minReq) majorityNums.add(nums[startRange]);
        return majorityNums;
	}
	
	// find numbers that appear more than math.floor(nums.length) times.
	public static List<Integer> findMajorityInts_nlng(int[] nums){
		// with sort, runtime O(nlgn+n), memory usage O(result.size+1)
        Arrays.sort(nums);
        List<Integer> majorityNums = new ArrayList<Integer>();
        if(nums.length==0) return majorityNums;
        else if(nums.length==1){
            majorityNums.add(nums[0]);
            return majorityNums;
        }
        int startRange = 0;
        int minReq = nums.length/3;
        for(int i=1; i<nums.length; i++){
            if(nums[i]>nums[startRange]) {
                if(i-startRange>minReq) majorityNums.add(nums[startRange]);
                startRange = i;
            }
        }
        if(nums.length-startRange>minReq) majorityNums.add(nums[startRange]);
        return majorityNums;
	}
	
	// Summarize ranges (inclusive) into range strings
	public static List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<String>();
        if(nums.length==0) return res;
        int rangeStart = 0;
        
        for(int i=1; i<nums.length; i++){
            if(nums[i]-1>nums[i-1]) {
                if(i-1==rangeStart) res.add(""+nums[rangeStart]);
                else res.add(""+nums[rangeStart]+"->"+nums[i-1]);
                rangeStart=i;
            }
        }
        if(rangeStart==nums.length-1) res.add(""+nums[rangeStart]);
        else res.add(""+nums[rangeStart]+"->"+nums[nums.length-1]);
        return res;
    }
	
	// check if any dupliated numbers in the array
	// test cases to be added...
	public static boolean containsDuplicate(int[] nums) { // runtime O(nums.length); memory O(nums.length)
		HashSet<Integer> existed=new HashSet<Integer>();
        for(int i=0; i<nums.length; i++){
        	if(!existed.add(nums[i])) return true;
        }
        return false;
	}
	
	public boolean containsDuplicateInDistance(int[] nums, int k) { // runtime O(n); memory O(n)
        HashSet<Integer> visited=new HashSet<Integer>();
        for(int i=0; i<nums.length; i++){
            if(i>k) visited.remove(nums[i-k-1]);
            if(!visited.add(nums[i])) return true;
        }
        return false;
    }
	
	// find the kth largest num in the array
	public static int findKthLargest(int[] nums, int k) {
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
	
	private static void findKthLargest_test(Scanner inScan, Scanner outScan) {
		while(inScan.hasNextLine() && outScan.hasNextLine()) {
			int k = inScan.nextInt();
			int[] nums=new int[inScan.nextInt()];
			for(int i=0; i<nums.length; i++) nums[i]=inScan.nextInt();
			
			int result = findKthLargest(nums, k);
			int expected = outScan.nextInt();
			
			System.out.print("Result: "+result+"; ");
			System.out.print("Expected: "+expected+"; ");
			if(result== expected) System.out.println("CORRECT.");
			else System.out.println("NOT CORRECT.");
		}
	}
    
	// helper func for findKthLargest
    private static int findPivot(int[] nums, int rangeStart, int rangeEnd){
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
    private static void swap(int[] nums, int indexa, int indexb){
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
	
	private static void minSubArrayLen_test(Scanner inScan, Scanner outScan) {
		while(inScan.hasNextLine() && outScan.hasNextLine()) {
			int target = inScan.nextInt();
			int[] nums = new int[inScan.nextInt()];
			for(int i=0; i<nums.length; i++) nums[i]=inScan.nextInt();
			
			int result = minSubArrayLen(target, nums);
			int expected = outScan.nextInt();
			
			System.out.print("Result: "+result+"; ");
			System.out.print("Expected: "+expected+"; ");
			if(result== expected) System.out.println("CORRECT.");
			else System.out.println("NOT CORRECT.");
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("Please select the alphabetic number of the operation you want to perform: \n"
				+ "1 minSubArrayLen\n"
				+ "2 findKthLargest\n");
		Scanner scanUserInput = new Scanner(System.in);
		int userinput = scanUserInput.nextInt();
		if(userinput<1 || userinput>2) System.out.println("Invalid selection.");
		else {
			System.out.println("Please enter the input file directory path: ");
			String inputFileDir = scanUserInput.next();
			try {
				File testinputFile =new File(inputFileDir);
				Scanner inScan = new Scanner(testinputFile);
				System.out.println("Please enter the output file directory path: ");
				try {
					String outputFileDir = scanUserInput.next();
					File testoutputFile =new File(outputFileDir);
					Scanner outScan = new Scanner(testoutputFile);
					scanUserInput.close();
					
					if(userinput==1) {
						minSubArrayLen_test(inScan, outScan);
					}else if(userinput==2) {
						findKthLargest_test(inScan, outScan);
					}
					outScan.close();
					inScan.close();
				}catch(Exception e) {
					System.out.println("ERROR:: INVALID OUTPUT FILE. "+e);
				}
			}catch(Exception e) {
				System.out.println("ERROR:: INVALID INPUT FILE. "+e);
			}
		}
		
	}
}
