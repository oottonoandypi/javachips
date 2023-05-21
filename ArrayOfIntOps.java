package javachips;

import java.io.File;
import java.util.Scanner;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayOfIntOps {
	// ---------------------------------------------------------
	// Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i], answer[j]) of elements in this subset satisfies:

	//	answer[i] % answer[j] == 0, or
	//	answer[j] % answer[i] == 0
	//	If there are multiple solutions, return any of them.
	public static List<Integer> largestDivisibleSubset(int[] nums) {
		// Same approach as thought process below but uses less time and memory
		// runtime O(n^2(+nlgn+n)) ; additional memory usage O(n)
		// reduced runtime of copying subArray by taking and retrieving memory of visited num's largest divisible subset
        // also reduced additional memory usage
		Arrays.sort(nums);
        
        int[][] longestSubset=new int[nums.length][2];
        int longestIndex=nums.length-1;
        int longest=0;
        
        for(int i=nums.length-1; i>=0; i--){
            longestSubset[i][0]=i;
            for(int j=i+1; j<nums.length; j++){
                if(nums[j]%nums[i]==0){
                    if(longestSubset[j][1]>longestSubset[i][1]){
                        longestSubset[i][1]=longestSubset[j][1];
                        longestSubset[i][0]=j;
                    }
                }
            }
            longestSubset[i][1]++;
            if(longestSubset[i][1]>=longest) {
                longest=longestSubset[i][1];
                longestIndex=i;
            }
        }
        
        List<Integer> res=new ArrayList<Integer>();
        while(longestSubset[longestIndex][0]>longestIndex){
            res.add(nums[longestIndex]);
            longestIndex=longestSubset[longestIndex][0];
        }
        res.add(nums[longestIndex]);
        return res;
    }
	// Thought process, first attempt
	/* public static List<Integer> largestDivisibleSubset_attempt1(int[] nums) {
	 	// Approach: if x is a multiple of y where y<x; z is a multiple of y where z>x
	 	// Observation: however, there could be another k<z or k>z where is a multiple of y but not a multiple of x or z
        // too slow; O(n^3+nlgn+n)
        // additional memory usage: 
        Arrays.sort(nums);
        List<List<Integer>> choices=new ArrayList<>();
        List<Integer> res;
        
        for(int k=nums.length-1; k>=0; k--){
            int n=nums[k];
            boolean foundList=false;
            for(int i=choices.size()-1; i>=0; i--){
                List<Integer> list=choices.get(i);
                int smallest=list.get(list.size()-1);
                if(smallest%n==0) {
                    List<Integer> newList=new ArrayList<Integer>(list);
                    newList.add(n);
                    choices.add(newList);
                    foundList=true;
                }
            }
            
            if(!foundList){
                List<Integer> newList=new ArrayList<Integer>();
                newList.add(n);
                choices.add(newList);
            }
        }
        
        res=choices.get(0);
        for(int i=1; i<choices.size(); i++){
            if(choices.get(i).size()>res.size()) res=choices.get(i);
        }
        
        return res;
    } */
	
	// ---------------------------------------------------------
	// Given two integer arrays nums1 and nums2, return an array of their intersection. 
	// Each element in the result must appear as many times as it shows in both arrays 
	public static int[] findIntersectionsOfTwoArraysII_array(int[] nums1, int[] nums2) {
		// runtime O(n)
        int[] nums1Counts=new int[1001];
        for(int n: nums1) nums1Counts[n]++;
        
        int lastIntersection=-1;
        for(int i=0; i<nums2.length; i++){
            int n=nums2[i];
            if(nums1Counts[n]>0){
                nums2[++lastIntersection]=n;
                nums1Counts[n]--;
            }
        }
        if(lastIntersection==-1) return new int[0];
        return Arrays.copyOfRange(nums2, 0, lastIntersection+1);
    }
	
	public static int[] findIntersectionsOfTwoArraysII_hashmap(int[] nums1, int[] nums2) {
		// runtime O(n)
        HashMap<Integer, Integer> nums1Counts=new HashMap<Integer, Integer>();
        for(int n: nums1){
            if(nums1Counts.containsKey(n)) nums1Counts.put(n, nums1Counts.get(n)+1);
            else nums1Counts.put(n, 1);
        }
        
        int lastIntersection=-1;
        for(int i=0; i<nums2.length && !nums1Counts.isEmpty(); i++){
            int n=nums2[i];
            if(nums1Counts.containsKey(n)){
                nums2[++lastIntersection]=n;
                if(nums1Counts.get(n)==1) nums1Counts.remove(n);
                else nums1Counts.put(n, nums1Counts.get(n)-1);
            }
        }
        if(lastIntersection==-1) return new int[0];
        return Arrays.copyOfRange(nums2, 0, lastIntersection+1);
    }
	
	
	// ---------------------------------------------------------
	// Given two integer arrays nums1 and nums2, return an array of their intersection. 
	// Each element in the result must be unique and you may return the result in any order.
	public static int[] findIntersectionsOfTwoArrays(int[] nums1, int[] nums2) {
        HashSet<Integer> nums1Set=new HashSet<Integer>();
        for(int n: nums1){
            nums1Set.add(n);
        }
        
        ArrayList<Integer> intersections=new ArrayList<Integer>();
        for(int n: nums2){
            if(nums1Set.contains(n)) {
                intersections.add(n);
                nums1Set.remove(n);
            }
        }
        
        int[] res=new int[intersections.size()];
        for(int i=0; i<intersections.size(); i++){
            res[i]=intersections.get(i);
        }
        
        return res;
    }
	
	// ---------------------------------------------------------
	// Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
	public static int[] topKFrequent_solution2(int[] nums, int k) {
        // runtime O(n)
		HashMap<Integer, Integer> visitedNums=new HashMap<Integer, Integer>();
        ArrayList<Integer> uniqueNums=new ArrayList<Integer>();
        int maxFreq=1;
        
        for(int n: nums){
            if(!visitedNums.containsKey(n)) {
                visitedNums.put(n, 1);
                uniqueNums.add(n);
            }else{
                int newFreq=visitedNums.get(n)+1;
                visitedNums.put(n, newFreq);
                if(newFreq>maxFreq) maxFreq=newFreq;
            }
        }
        
        ArrayList<Integer>[] freq=new ArrayList[maxFreq];
        for(int i=0; i<uniqueNums.size(); i++){
            int n=uniqueNums.get(i);
            int f=visitedNums.get(n);
            if(freq[f-1]==null) freq[f-1]=new ArrayList<Integer>();
            freq[f-1].add(n);
        }
        
        int[] res=new int[k];
        for(int i=freq.length-1; i>=0 && k>0; i--){
            ArrayList<Integer> f=freq[i];
            if(f==null) continue;
            for(int j=0; j<f.size() && k>0; j++){
                res[--k]=f.get(j);
            }
        }
        
        return res;
    }
	
	public static int[] topKFrequent_solution1(int[] nums, int k) {
		// Runtime O(n)
        HashSet<Integer>[] frequencies=new HashSet[nums.length/k+3];
        HashMap<Integer, Integer> visitedNums=new HashMap<Integer, Integer>();
        
        for(int n: nums){
            if(visitedNums.containsKey(n)){
                int lastFreq=visitedNums.get(n);
                frequencies[lastFreq].remove(n);
                if(frequencies[lastFreq+1]==null) frequencies[lastFreq+1]=new HashSet<Integer>();
                frequencies[lastFreq+1].add(n);
                visitedNums.put(n, lastFreq+1);
            }else{
                visitedNums.put(n, 1);
                if(frequencies[1]==null) frequencies[1]=new HashSet<Integer>();
                frequencies[1].add(n);
            }
        }
        
        int[] res=new int[k];
        int index=0;
        
        for(int i= frequencies.length-1; i>=0 && index<k; i--){
            HashSet<Integer> numWithFreq=frequencies[i];
            if(numWithFreq==null) continue;
            Iterator<Integer> it=numWithFreq.iterator();
            while(it.hasNext() && index<k){
                res[index++]=it.next();
            }
        }
        
        return res;
    }
	
	// ---------------------------------------------------------
	// Given an unordered array
	// Return true if there is a triplet indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]; 
	// Otherwise, false.
	public static boolean hasIncreasingTriplet_n_optimized(int[] nums) {
		// Same approach as the hasIncreasingTriplet_n
		// but with less additional memory usage and no inner loop
        int triplet_1=Integer.MAX_VALUE;
        int triplet_2=Integer.MAX_VALUE;
        
        for(int n: nums){
            if(n<=triplet_1) triplet_1=n;
            else if(n<=triplet_2) triplet_2=n;
            else return true;
        }
        return false;
    }
	
	public static boolean hasIncreasingTriplet_n(int[] nums) {
		// Approach: keep past nums' increasing order in the triplet; when a new num is smaller than any of the past num, replace the past num with it
		// so that when there is a num in the remaining array that is greater than this replaced num, it can still build a valid triplet
		// Time complexity O(2n)
        int[] triplet=new int[3];
        triplet[0]=nums[0];
        int index=0;
        for(int i=1; i<nums.length; i++){
            int nextNum=nums[i];
            
            int j=index;
            while(j>=0){
                if(nextNum>triplet[j]) break;
                j--;
            }
            triplet[++j]=nextNum;
            index=Math.max(index, j);
            if(index==2) return true;
        }
        return false;
    }
	
	// ---------------------------------------------------------
	// Reorder array such that nums[0] < nums[1] > nums[2] < nums[3]....
	public static void wiggleSort(int[] nums) {
        // runtime O(nlgn)
        Arrays.sort(nums);
        int[] copyNums=new int[nums.length];
        for(int i=0; i<nums.length; i++){
            copyNums[i]=nums[i];
        }
        
        int index=0;
        for(int i=(nums.length-1)/2; i>=0 && index<nums.length; i--){
            nums[index++]=copyNums[i];
            if(i+(nums.length)/2<nums.length && i+(nums.length)/2>(nums.length-1)/2) nums[index++]=copyNums[i+(nums.length)/2];
        }
    }
	
	// ---------------------------------------------------------
	// Assume there are infinite counts of each integer in an array of nums,
	// find the minimum count of integers that will sum to target
	public static int minCountToTarget_dp_iter(int[] nums, int target) {	
		// iterative dp approach: for every possible sums <= target, record minimum counts of nums that make to sums
		// then at sums i, its counts could be counts[i-nums[k]] where counts[i-nums[k]] was recorded
		// for sums i, record the min counts out of 1+counts[i-nums[...k]]
		// on and on until reach sums i==target
		// time complexity O(nums.length*target)
		
        int[] visitedAmount=new int[target+1];
        Arrays.sort(nums);
        
        for(int i=1; i<=target; i++){
            int minCounts=-1;
            for(int n: nums){
                if(n<=i){
                    if(visitedAmount[i-n]>-1){
                        if(minCounts==-1) minCounts=1+visitedAmount[i-n];
                        else minCounts=Math.min(minCounts, 1+visitedAmount[i-n]);
                    }
                }else break;
            }
            visitedAmount[i]=minCounts;
        }
        
        return visitedAmount[target];
		
	}
	
	public static int minCountToTarget_dp_recur(int[] nums, int target) {
		// Similar idea as the iterative approach but recursive
		// time complexity O(nums.length*target)
		return minCountToTarget_recursionHelper(nums, target, new int[target+1]);
	}
	
	private static int minCountToTarget_recursionHelper(int[] nums, int remainTarget, int[] visitedAmount){
        if(remainTarget==0) return 0;
        if(visitedAmount[remainTarget]!=0) return visitedAmount[remainTarget];
        
        int minCoins=-1;
        for(int n: nums){
            if(n<=remainTarget) {
                int countCoins=1+minCountToTarget_recursionHelper(nums, remainTarget-n, visitedAmount);
                if(countCoins>0) {
                    if(minCoins==-1) minCoins=countCoins;
                    else minCoins=Math.min(minCoins, countCoins);
                }
            }
        }
        
        visitedAmount[remainTarget] = minCoins;
        return minCoins;
    }
	// ---------------------------------------------------------
	// Longest Increasing Subsequence problem:
	// Given an integer array nums, return the length of the longest strictly increasing subsequence
	// Ex. Input: nums = [10,9,2,5,3,7,101,18]
	// Output: 4
	public static int lengthOfLIS_optimized(int[] nums) {
		// runtime O(nlgn)
        int[] lis=new int[nums.length];
        lis[0]=nums[0];
        int endOfLis=1;
        int maxLen=1;
        
        for(int i=1; i<nums.length; i++){
            int n=nums[i];
            int toInsert=searchLargestSmaller(lis, endOfLis, n);
            lis[toInsert]=n;
            if(toInsert==endOfLis) endOfLis++;
            
            if(endOfLis>maxLen) maxLen=endOfLis;
        }
        
        return maxLen;
    }
    // helper function for lengthOfLIS_optimized
    private static int searchLargestSmaller(int[] lis, int endOfLis, int target){
        int l=0;
        int r=endOfLis-1;
        while(l<=r){
            int m=(l+r)/2;
            if(target>lis[m]) l=m+1;
            else if(target<lis[m]) r=m-1;
            else return m;
        }
        
        return l;
    }
	
	public static int lengthOfLIS_1stAttempt(int[] nums) {
		// runtime O(n^2*lgn)
        List<List<Integer>> paths = new ArrayList<>();
        paths.add(new ArrayList<Integer>());
        paths.get(0).add(nums[0]);
        
        for(int n: nums){
            int pL=0;
            int largestSmaller=searchLargestSmaller_lengthOfLIS_1stAttempt(paths.get(pL), n);

            while(pL<paths.size() && largestSmaller>=0){
                if(n>paths.get(pL).get(largestSmaller)){
                    pL++;
                    if(pL<paths.size()) largestSmaller=searchLargestSmaller_lengthOfLIS_1stAttempt(paths.get(pL), n);
                }else break;
            }
            if(largestSmaller==-1 && n==paths.get(pL).get(0)) continue;
            if(pL==paths.size()) {
                paths.add(new ArrayList<Integer>());
                paths.get(pL).add(n);
            }else{
                paths.get(pL).add(largestSmaller+1, n);
            }
            
        }
        
        return paths.size();
    }
	// helper function for lengthOfLIS_1stAttempt
	private static int searchLargestSmaller_lengthOfLIS_1stAttempt(List<Integer> level, int target){
        int l=0;
        int r=level.size()-1;
        while(l<=r){
            int m=(l+r)/2;
            if(target>level.get(m)) l=m+1;
            else if(target<level.get(m)) r=m-1;
            else return m-1;
        }
        
        return l-1;
    }
	
	
	// ---------------------------------------------------------
	// Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
	// There is only one repeated number in nums, return this repeated number.
	public static int findDuplicate(int[] nums) {
        int next=nums[0];
        
        while(next!=nums[next]){
            int temp=nums[next];
            nums[next]=next;
            next=temp;
        }
        return next;
    }
	
	// ---------------------------------------------------------
	public static void moveZeroes(int[] nums) {
		// General Idea: move all zeros to the end of the array, there will be an index where all the numbers after index are 0's;
		// Approach: find the start index of the zeros; when there is a non-zeros, swap zero at startIndex of zeros with the non-zero;
		// new start index of zeros are now incremented by 1
		// repeat until the end of nums
		// runtime: O(n); Additional memory O(0)
        int startZero=-1;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=0){
                if(startZero>=0){
                    nums[startZero]=nums[i];
                    nums[i]-=nums[startZero];
                    startZero++;
                }
            }else if(startZero<0) startZero=i;
        }
    }
	
	// ---------------------------------------------------------
	// The h-index is defined as the maximum value of h such that 
	// the given researcher has published at least h papers that have each been cited at least h times.
	public int hIndex_n(int[] citations){ 
		// General idea: highest index where everything towards to the right (including itself) has a value>=index+1
		// Approach: count number of values that are >= each index+1; then find the biggest index+1 where count is >=index+1, and that index+1 is the hIndex.
		// runtime O(n); memory O(n)
		int len=citations.length;
        int[] countCitations=new int[len+1];
        for(int c: citations){
            if(c<len){
                countCitations[c]++;
            }else{
                countCitations[len]++;
            }
        }
        
        for(int i=len; i>=1; i--){
            if(i<len) countCitations[i]+=countCitations[i+1];
            if(countCitations[i]>=i) return i;
        }
        
        return 0;
    }
	
	public int hIndex_nlgn(int[] citations) { 
		// General idea: highest index where everything towards to the right (including itself) has a value>=index+1
		// Approach: have all the values sorted in ascending order, so everything to the right of a given index are >= [index]; 
		// when at an index that its value is >= the remaining length (because the remaining will be >=[index].
		// then the remaining length is the hIndex.
		// use binary search to locate that index and return citations.length-index;
		// runtime O(nlgn+lgn)
        Arrays.sort(citations);
        int left=0;
        int right=citations.length-1;
        while(left<=right){
            int mid=(left+right)/2;
            if(citations[mid]==citations.length-mid) return citations.length-mid;
            else if(citations[mid]>citations.length-mid) right=mid-1;
            else left=mid+1;
        }
        
        return citations.length-left;
    }
	
	// ---------------------------------------------------------
	// find product of nums except each num itself
	public static int[] productExceptSelf(int[] nums) {
        int countOfZeros = 0;
        int prodWithNoZeros = 1;
        int[] res = new int[nums.length];
        
        for (int num: nums){
            if(num==0) countOfZeros++;
            else prodWithNoZeros*=num;
        }
        
        if(countOfZeros>1) return res;
        
        for (int i=0; i<nums.length; i++){
            if (nums[i]!=0){
                if(countOfZeros==0) res[i]=prodWithNoZeros/nums[i];
            }else res[i]=prodWithNoZeros;
        }
        
        return res;
    }
	
	// ---------------------------------------------------------
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
	
	// ---------------------------------------------------------
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
	
	// ---------------------------------------------------------
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
	
	// ---------------------------------------------------------
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
	
	// ---------------------------------------------------------
	// check if any dupliated numbers in the array
	// test cases to be added...
	public static boolean containsDuplicate(int[] nums) { // runtime O(nums.length); memory O(nums.length)
		HashSet<Integer> existed=new HashSet<Integer>();
        for(int i=0; i<nums.length; i++){
        	if(!existed.add(nums[i])) return true;
        }
        return false;
	}
	
	// ---------------------------------------------------------
	public boolean containsDuplicateInDistance(int[] nums, int k) { // runtime O(n); memory O(n)
        HashSet<Integer> visited=new HashSet<Integer>();
        for(int i=0; i<nums.length; i++){
            if(i>k) visited.remove(nums[i-k-1]);
            if(!visited.add(nums[i])) return true;
        }
        return false;
    }
	
	// ---------------------------------------------------------
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
	
	
	// ---------------------------------------------------------
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
    
    
    // ---------------------------------------------------------
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
