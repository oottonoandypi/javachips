package javachips;

import java.io.File;
import java.util.Scanner;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class ArrayOfIntOps {
	// ---------------------------------------------------------
	// Maximum rotation function 
	// Input: an unsorted integer array nums of length n
	// Returns: the maximum value of F(0), F(1), ..., F(n-1)
	// where F(k)= 0*arrK[0] + 1*arrK[1] + ... + (n-1)*arrK[n-1]
	// EX. nums = [4,3,2,6] => 26
			// F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
			// F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
			// F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
			// F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
			// So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
	public static int maxRotateFunction(int[] nums) {
		// time complexity O(n) space complexity O(1)
        int sum=0;
        int fSum=0;
        for(int i=0; i<nums.length; i++){
            sum+=nums[i];
            fSum+=nums[i]*i;
        }
        // System.out.println(fSum);
        int maxVal=fSum;
        for(int i=nums.length-1; i>0; i--){
            fSum=fSum-(nums.length-1)*nums[i]+sum-nums[i];
            if(fSum>maxVal) maxVal=fSum;
        }
        return maxVal;
    }
	
	// ---------------------------------------------------------
	// Longest Subarray of 1's After Deleting One Element
	// Input: a binary array nums (only has 1's and 0's)
	// Returns: size of the longest non-empty subarray containing only 1's in the resulting array; 0 if there is no such subarray.
	public static int longestSubarrayOfOnesAfterDeletingOneElement_n(int[] nums) {
		// time complexity O(n) space complexity O(1)
        int[] consectiveOnes=new int[]{-1, -1};
        int prevLen=0;
        int prevEndAt=-1;
        int currLen;
        int longest=0;
        
        for(int i=0; i<nums.length; i++){
            if(nums[i]==1){
                if(consectiveOnes[0]==-1){
                    consectiveOnes[0]=i;
                    consectiveOnes[1]=i;
                }else consectiveOnes[1]=i;
            }
            
            if(nums[i]==0 || i==nums.length-1){
                if(consectiveOnes[0]==-1) continue;
                currLen=consectiveOnes[1]-consectiveOnes[0]+1;
                
                if(prevEndAt==-1){
                    if(consectiveOnes[0]==0 && consectiveOnes[1]+1==nums.length) longest=currLen-1;
                    else longest=currLen;
                }else{
                    if(consectiveOnes[0]-prevEndAt>2) longest=Math.max(longest, currLen);
                    else longest=Math.max(longest, currLen+prevLen);
                }
                
                prevLen=currLen;
                prevEndAt=consectiveOnes[1];
                consectiveOnes[0]=-1;
                consectiveOnes[1]=-1;
            }
        }
        
        return longest;
    }
	
	public static int longestSubarrayOfOnesAfterDeletingOneElement_2n(int[] nums) {
		// time complexity O(n) space complexity O(n)
        List<int[]> consectiveOnes=new ArrayList<>();
        int startI=-1;
        int endI=startI;
        
        for(int i=0; i<nums.length; i++){
            if(nums[i]==1){
                if(startI==-1) {
                    startI=i;
                    endI=startI;
                }else endI=i;
            }else{
                if(startI>-1){
                    consectiveOnes.add(new int[]{startI, endI});
                    startI=-1;
                }
            }
        }
        
        if(startI>-1) consectiveOnes.add(new int[]{startI, endI});
        /*
        for(int i=0; i<consectiveOnes.size(); i++){
            System.out.println("start: "+consectiveOnes.get(i)[0]+" end: "+consectiveOnes.get(i)[1]);
        }
        */
        
        int longest=0;
        if(consectiveOnes.size()==0) return longest;
        
        int prev=0;
        int prevEnd=-1;
        int curr;
        int[] checkout;
        for(int i=0; i<consectiveOnes.size(); i++){
            checkout=consectiveOnes.get(i);
            curr=checkout[1]-checkout[0]+1;
            
            if(i==0) {
                if(checkout[0]==0 && checkout[1]+1==nums.length) longest=curr-1;
                else longest=curr;
            }else{
                if(checkout[0]-prevEnd>2) longest=Math.max(longest, curr);
                else longest=Math.max(longest, curr+prev);
            }
            
            prev=curr;
            prevEnd=checkout[1];
        }
        
        return longest;
    }
	
	// ---------------------------------------------------------
	// Maximum Product of Three Numbers
	// Input: an unsorted integer array nums
	// Return: maximum product of 3 numbers from the given array
	public static int maxProductOfThreeIntegers_n(int[] nums) {
		// time complexity O(n) space complexity O(1)
        int[] biggestThree=new int[3];
        biggestThree[0]=Integer.MIN_VALUE;
        biggestThree[1]=Integer.MIN_VALUE;
        biggestThree[2]=Integer.MIN_VALUE;
        
        int[] smallestTwo=new int[2];
        smallestTwo[0]=Integer.MAX_VALUE;
        smallestTwo[1]=Integer.MAX_VALUE;
        
        for(int n: nums){
            if(n>biggestThree[0]) {
                biggestThree[0]=n;
                if(n>biggestThree[1]) {
                    biggestThree[0]=biggestThree[1];
                    biggestThree[1]=n;
                }
                if(n>biggestThree[2]){
                    biggestThree[1]=biggestThree[2];
                    biggestThree[2]=n;
                }
            }
            if(n<smallestTwo[1]){
                smallestTwo[1]=n;
                if(n<smallestTwo[0]){
                    smallestTwo[1]=smallestTwo[0];
                    smallestTwo[0]=n;
                }
            }
        }
        
        /*
        for(int i: biggestThree) System.out.print(i+" ");
        System.out.println();
        for(int i: smallestTwo) System.out.print(i+" ");
        System.out.println();
        */
        return Math.max(smallestTwo[0]*smallestTwo[1]*biggestThree[2], biggestThree[0]*biggestThree[1]*biggestThree[2]); 
    }
	
	public static int maxProductOfThreeIntegers_nlgn(int[] nums) {
		// time complexity O(n) space complexity O(0)
        Arrays.sort(nums);
        return Math.max(nums[0]*nums[1]*nums[nums.length-1], nums[nums.length-1]*nums[nums.length-2]*nums[nums.length-3]);
    }
	
	// ---------------------------------------------------------
	// Input: an unsorted integer array nums
	// Returns: the smallest missing positive integer
	public static int smallestMissingPositive(int[] nums) {
		// time complexity O(n) space complexity O(1)
        int toSwap;
        
        for(int i=0; i<nums.length; i++){
            if(nums[i]<=nums.length && nums[i]>0 && nums[i]!=i+1 && nums[nums[i]-1]!=nums[i]) {
                toSwap=nums[nums[i]-1];
                nums[nums[i]-1]=nums[i];
                nums[i--]=toSwap;
            }
        }
        
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=i+1) return i+1;
        }
        
        return nums.length+1;
    }
	
	// ---------------------------------------------------------
	// Input: an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty
	// 		  and an integer k
	// Returns: true if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule and false otherwise.
	// Rule: flowers cannot be planted in adjacent plots
	// Assume the input flowerbed is NOT violating the rule
	public static boolean canPlaceKFlowers(int[] flowerbed, int k) {
        if(k==0) return true;
        int len=flowerbed.length;
        for(int i=0; i<len; i++){
            if(flowerbed[i]==1) {
                i++;
                continue;
            }
            
            if((i-1<0 || flowerbed[i-1]==0) && (i+1==len || flowerbed[i+1]==0)){
                // flowerbed[i]=1;
                // System.out.println(i);
                if(--k==0) return true;
                i++;
            }
        }
        
        return false;
    }
	
	
	// ---------------------------------------------------------
	// Given two sorted arrays nums1 and nums2 of size m and n respectively, 
	// return the median of the two sorted arrays.
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		// runtime O(n) memory O(n)
        int nums1Len=nums1.length;
        int nums2Len=nums2.length;
        
        int[] nums=new int[nums1Len+nums2Len];
        int index=0;
        int index1=0;
        int index2=0;
        
        while(index1<nums1Len && index2<nums2Len){
            if(nums1[index1]<=nums2[index2]) nums[index++]=nums1[index1++];
            else nums[index++]=nums2[index2++];
        }
        
        while(index1<nums1Len) nums[index++]=nums1[index1++];
        while(index2<nums2Len) nums[index++]=nums2[index2++];
        
        if(nums.length%2==0) return ((double)nums[(nums.length-1)/2]+nums[(nums.length-1)/2+1])/2;
        else return (double)nums[(nums.length-1)/2];
    }
	
	// ---------------------------------------------------------
	public static void randomShuffleArray(int[] nums) {
		// runtime O(n) memory O(1)
        int randomPick;
        int length=nums.length;
        int t;
        for(int i=0; i<length; i++){
            randomPick=(int)(Math.random()*(length-i))+i;
            t=nums[i];
            nums[i]=nums[randomPick];
            nums[randomPick]=t;
        }
    }
	
	// ---------------------------------------------------------
	// Given three positive integers: n, index, and maxSum. 
	// You want to construct an array nums (0-indexed) that satisfies the following conditions:
	/*
	 * nums.length == n
		nums[i] is a positive integer where 0 <= i < n.
		abs(nums[i] - nums[i+1]) <= 1 where 0 <= i < n-1.
		The sum of all the elements of nums does not exceed maxSum.
		nums[index] is MAXIMIZED.

	 */
	// Return nums[index] of the constructed array
	public static int maxValueAtIndexOfArray0ToN_binsearch(int n, int index, int maxSum){
		// runtime O(lgn) memory O(1)
		int leftBound=maxSum/n;
        int rightBound=maxSum;
        int middle;
        long sum=0;
        
        long smallestLeft;
        long smallestRight;
        
        while(leftBound<rightBound){
            //System.out.println("leftBound: "+leftBound);
            //System.out.println("rightBound: "+rightBound);
            middle=leftBound+(rightBound-leftBound)/2;
            //System.out.println("middle: "+middle);
            
            smallestLeft=Math.max(1, middle-index)+(middle-1);
        
            if(middle-1<index) {
                smallestLeft=smallestLeft*(middle-1)/2;
                smallestLeft+=index-(middle-1);
            }else{
                smallestLeft=smallestLeft*index/2;
            }
            
            //System.out.println(smallestLeft);
            
            smallestRight=Math.max(1, middle-(n-index-1))+(middle-1);
            if(middle-1<n-index-1){
                smallestRight=smallestRight*(middle-1)/2;
                smallestRight+=(n-index-1)-(middle-1);
            }else{
                smallestRight=smallestRight*(n-index-1)/2;
            }
            
            //System.out.println(smallestRight);
            
            sum=middle+smallestLeft+smallestRight;
            if(sum==maxSum) return middle;
            else if(sum<maxSum) leftBound=middle+1;
            else rightBound=middle-1;
        }
        
        smallestLeft=Math.max(1, leftBound-index)+(leftBound-1);
        
        if(leftBound-1<index) {
            smallestLeft=smallestLeft*(leftBound-1)/2;
            smallestLeft+=index-(leftBound-1);
        }else{
            smallestLeft=smallestLeft*index/2;
        }

        smallestRight=Math.max(1, leftBound-(n-index-1))+(leftBound-1);
        if(leftBound-1<n-index-1){
            smallestRight=smallestRight*(leftBound-1)/2;
            smallestRight+=(n-index-1)-(leftBound-1);
        }else{
            smallestRight=smallestRight*(n-index-1)/2;
        }

        sum=leftBound+smallestLeft+smallestRight;
        if(sum>maxSum) return leftBound-1;
        return leftBound;
		
	}
	
	public static int maxValueAtIndexOfArray0ToN_bruteforce(int n, int index, int maxSum){
		// runtime O(n) memory O(1);
		int maxVal=maxSum/n;
        int smallestLeft=Math.max(1, maxVal-index)+(maxVal-1);
        
        if(maxVal-1<index) {
            smallestLeft=smallestLeft*(maxVal-1)/2;
            smallestLeft+=index-(maxVal-1);
        }else{
            smallestLeft=smallestLeft*index/2;
        }
        //System.out.println("smallestLeft: "+smallestLeft);
        
        int smallestRight=Math.max(1, maxVal-(n-index-1))+(maxVal-1);
        if(maxVal-1<n-index-1){
            smallestRight=smallestRight*(maxVal-1)/2;
            smallestRight+=(n-index-1)-(maxVal-1);
        }else{
            smallestRight=smallestRight*(n-index-1)/2;
        }
        //System.out.println("smallestRight: "+smallestRight);
        
        while(maxVal+smallestLeft+smallestRight<=maxSum){
            maxVal++;
            
            smallestLeft=Math.max(1, maxVal-index)+(maxVal-1);
        
            if(maxVal-1<index) {
                smallestLeft=smallestLeft*(maxVal-1)/2;
                smallestLeft+=index-(maxVal-1);
            }else{
                smallestLeft=smallestLeft*index/2;
            }
            //System.out.println("smallestLeft: "+smallestLeft);
            
            
            smallestRight=Math.max(1, maxVal-(n-index-1))+(maxVal-1);
            if(maxVal-1<n-index-1){
                smallestRight=smallestRight*(maxVal-1)/2;
                smallestRight+=(n-index-1)-(maxVal-1);
            }else{
                smallestRight=smallestRight*(n-index-1)/2;
            }
            //System.out.println("smallestRight: "+smallestRight);
        }
        
        return maxVal-1;
	}
	// ---------------------------------------------------------
	// Longest Harmonious Subsequence: an array where the difference between its maximum value and its minimum value is exactly 1
	// Given an integer array nums, 
	// return the length of its longest harmonious subsequence among all its possible subsequences
	public static int lenOflongestHarmonuousSubsequence_nlgn(int[] nums) {
		// runtime O(lgn) memory O(1)
        Arrays.sort(nums);
        int maxLen=0;
        int first=nums[0];
        int countFirst=0;
        int second=first;
        int countSecond=0;
        
        for(int n:nums){
            if(n==first) countFirst++;
            else if(n==second) countSecond++;
            else if(first==second) {
                if(first-n==-1) {
                    second=n;
                    countSecond=1;
                }else {
                    first=n;
                    countFirst=1;
                    second=first;
                    // countSecond=0;
                }
            }else {
                // System.out.println("count first "+first+" "+countFirst);
                // System.out.println("count second "+second+" "+countSecond);
                maxLen=Math.max(maxLen, countFirst+countSecond);
                if(second-n==-1){
                    first=second;
                    countFirst=countSecond;
                    second=n;
                    countSecond=1;
                }else{
                    first=n;
                    countFirst=1;
                    second=first;
                    countSecond=0;
                }
            }
        }
        
        if(countFirst>0 && countSecond>0) maxLen=Math.max(maxLen, countFirst+countSecond);
        return maxLen;
    }
	
	public static int lenOflongestHarmonuousSubsequence_hash_n(int[] nums) {
		// runtime O(n) memory O(n)
        HashMap<Integer, Integer> integerCounts=new HashMap<Integer, Integer>();
        int maxLen=0;
        
        for(int n: nums){
            if(!integerCounts.containsKey(n)) integerCounts.put(n, 1);
            else integerCounts.put(n, integerCounts.get(n)+1);
            
            if(integerCounts.containsKey(n-1)) maxLen=Math.max(maxLen, integerCounts.get(n-1)+integerCounts.get(n));
            if(integerCounts.containsKey(n+1)) maxLen=Math.max(maxLen, integerCounts.get(n+1)+integerCounts.get(n));
        }
        return maxLen;
    }
	
	// ---------------------------------------------------------
	// Given an array of candies, each entry candies[i] represents a candy type
	// Knowing the maximum candies can be distributed is array.size/2
	// Return the maximum different candy types can be distributed
	public static int distributeCandies_array(int[] candyType) {
		//runtime O(n) memory O(k)
        int countTypes=0;
        int maxCandies=candyType.length/2;
        int smallestType=candyType[0];
        int largestType=candyType[0];
        
        for(int ct: candyType){
            if(ct<smallestType) smallestType=ct;
            else if(ct>largestType) largestType=ct;
        }
        
        boolean[] countCandies=new boolean[largestType-smallestType+1];
        for(int ct: candyType){
            if(!countCandies[ct-smallestType]) {
                countTypes++;
                countCandies[ct-smallestType]=true;
            }
            if(countTypes>=maxCandies) return maxCandies;
        }
        
        return countTypes;
    }
	
	public static int distributeCandies_hashset(int[] candyType) {
		//runtime O(n) memory O(k)
        HashSet<Integer> candyTypes=new HashSet<Integer>();
        int maxCandy=candyType.length/2;
        for(int ct: candyType) {
            candyTypes.add(ct);
            if(candyTypes.size()>maxCandy) return maxCandy;
        }
        return candyTypes.size();
    }
	
	
	// ---------------------------------------------------------
	// Given an array of distinct non-negative integers nums and a target integer target, 
	// return the number of possible combinations that add up to target.
	public static int countCombinationsThatSumToTarget(int[] nums, int target) {
		// runtime O(n^target) memory O(target)
        int[] visited=new int[target+1];
        int res=findComboThatSumToTarget(nums, visited, target);
        // for(int n: visited) System.out.println(n);
        if(res==-1) return 0;
        return res;
    }
    
    private static int findComboThatSumToTarget(int[] nums, int[] visited, int target){
        if(target<0) return -1;
        if(target==0) return 1;
        if(visited[target]!=0) return visited[target];
        
        int countCombos=0;
        int count;
        for(int n: nums){
            count=findComboThatSumToTarget(nums, visited, target-n);
            if(count>-1) countCombos+=count;
        }
        
        if(countCombos==0) visited[target]=-1;
        else visited[target]=countCombos;
        
        return visited[target];
    }
	
	// ---------------------------------------------------------
	// Given an unordered array, returns true if array can be arranged to an arithmetic progression
	// Note; arithmetic progression is a sequence of numbers where the difference between any two consecutive elements is the same
	public static boolean canMakeArithmeticProgression_n(int[] arr) {
		// runtime O(n) memory O(1)
        int min=arr[0];
        int max=arr[0];
        int len=arr.length;
        for(int n: arr){
            if(n<min) min=n;
            else if(n>max) max=n;
        }
        
        if(min==max) return true;
        if((max-min)%(len-1)!=0) return false;
        
        int diff=(max-min)/(len-1);
        int index=0;
        int findIndex;
        int t;
        
        while(index<len){
            if((arr[index]-min)%diff!=0) return false;
            findIndex=(arr[index]-min)/diff;
            if(findIndex!= index) {
                if(arr[findIndex]==arr[index]) return false;
                t=arr[findIndex];
                arr[findIndex]=arr[index];
                arr[index]=t;
            }else index++;
        }
        
        return true;
    }
	
	public static boolean canMakeArithmeticProgression_n_set(int[] arr) {
		// runtime O(n) memory O(n)
        int min=arr[0];
        int max=arr[0];
        HashSet<Integer> set=new HashSet<Integer>();
        boolean hasMultiple=false;
        
        for(int n: arr){
            if(n<min) min=n;
            else if(n>max) max=n;
            if(set.contains(n)) hasMultiple=true;
            set.add(n);
        }
        if(min==max) return true;
        if(hasMultiple) return false;
        if((max-min)%(arr.length-1)!=0) return false;
        int diff=(max-min)/(arr.length-1);
        for(int n: arr){
            if((n-min)%diff!=0) return false;
        }
        
        return true;
    }
	
	public static boolean canMakeArithmeticProgression_nlgn(int[] arr) {
		// runtime O(nlgn) memory O(1)
        Arrays.sort(arr);
        int diff=arr[1]-arr[0];
        
        for(int i=2; i<arr.length; i++){
            if(arr[i]-arr[i-1]!=diff) return false;
        }
        
        return true;
    }
	
	// ---------------------------------------------------------
	// Given an integer array nums of 2n integers, (not ordered)
	// group these integers into n pairs (a1, b1), (a2, b2), ..., (an, bn) such that 
	// sum of min(ai, bi) for all i is maximized. Return the maximized sum.
	public static int maxSumOfminPairs_countSort(int[] nums) {
        // Count Sort runtime O(n) memory O(~); ~:depends on the range of integers in nums
        int max=nums[0];
        int min=nums[0];
        
        for(int i=1; i<nums.length; i++){
            if(nums[i]<min) min=nums[i];
            else if(nums[i]>max) max=nums[i];
        }
        
        int[] range=new int[max-min+1];
        
        for(int n:nums) range[n-min]++;
        
        int remain=0;
        int count=0;
        int sum=0;
        for(int i=0; i<range.length; i++){
            if(range[i]>0){
                count=(range[i]-remain)/2;
                remain=(range[i]-remain)%2;

                sum+=(i+min)*(count+remain);
            }
        }
        
        return sum;
    }
	
	public static int maxSumOfminPairs_Sort(int[] nums) {
        // Sort runtime O(nlgn) memory O(1)
        Arrays.sort(nums);
        int sum=0;
        for(int i=0; i<nums.length; i+=2){
            sum+=nums[i];
        }
        return sum;
    }
	
	// ---------------------------------------------------------
	// Given 2 arrays:
	// nums2 contains integers with no duplicates in non-ordered manner
	// nums1 contains integers from nums2
	// EX. there is a 0<=i<nums1.length and a 0<=j<nums2.length where nums1[i]=nums2[j]
	// return an array of the closest greater integer in the right sub-array of each nums1[i] in nums2 (if no greater integer, put -1)
	// EX. nums1 = [4,1,2], nums2 = [1,3,4,2] => [-1,3,-1]
	public static int[] nextGreaterElement_stack(int[] nums1, int[] nums2) {
		// use a stack to store the closest greater elements
		// to help eliminate some repeated visits
		// runtime < O(n^2); memory usage O(n)
        HashMap<Integer, Integer> nextGreater=new HashMap<Integer, Integer>();
        Stack<Integer> greaters=new Stack<Integer>();
        
        for(int i=nums2.length-1; i>=0; i--){
            while(!greaters.empty() && greaters.peek()<nums2[i]) greaters.pop();
            if(!greaters.empty()) nextGreater.put(nums2[i], greaters.peek());
            greaters.push(nums2[i]);
        }
        
        int[] res=new int[nums1.length];
        for(int i=0; i<res.length; i++){
            if(nextGreater.containsKey(nums1[i])) res[i]=nextGreater.get(nums1[i]);
            else res[i]=-1;
        }
        
        return res;
        
    }
	
	public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
		// runtime < O(n^2); memory usage O(n)
        HashMap<Integer, Integer> nextGreater=new HashMap<Integer, Integer>();
        
        for(int i=nums2.length-2; i>=0; i--){
            for(int j=i+1; j<nums2.length; j++){
                if(nums2[j]>nums2[i]) {
                    nextGreater.put(nums2[i], nums2[j]);
                    break;
                }
            }
        }
        
        int[] res=new int[nums1.length];
        for(int i=0; i<res.length; i++){
            if(nextGreater.containsKey(nums1[i])) res[i]=nextGreater.get(nums1[i]);
            else res[i]=-1;
        }
        
        return res;
        
    }
	
	// ---------------------------------------------------------
	// Given a binary array nums, return the maximum number of consecutive 1's in the array.
	public static int findMaxConsecutive1s(int[] nums) {
        int maxLenOf1s=0;
        int count1s=0;
        
        for(int n: nums){
            if(n==0) {
                maxLenOf1s=Math.max(maxLenOf1s, count1s);
                count1s=0;
            }else count1s++;
        }
        maxLenOf1s=Math.max(maxLenOf1s, count1s);
        
        return maxLenOf1s;
    }
	
	// ---------------------------------------------------------
	// find the number of ints in an array S that are >= ints in an array G
	public static int findIntsInS_GreaterThan_IntsInG(int[] g, int[] s) {
		// runtime O(nlgn); memory usage O(1)
        Arrays.sort(g);
        Arrays.sort(s);
        
        int indexG=0;
        int indexS=0;
        int count=0;
        
        while(indexG<g.length && indexS<s.length){
            if(s[indexS]>=g[indexG]){
                indexS++;
                indexG++;
                count++;
            }else indexS++;
        }
        
        return count;
    }
	
	// ---------------------------------------------------------
	// Find All Numbers Disappeared in an Array
	// Given an array nums of n integers where nums[i] is in the range [1, n], 
	// return an array of all the integers in the range [1, n] that do not appear in nums.
	public static List<Integer> findDisappearedNumbersInRange_1ToN(int[] nums) {
		// runtime O(n); memory usage O(n)
        int[] numsAppeared=new int[nums.length+1];
        for(int n: nums){
            numsAppeared[n]=1;
        }
        
        List<Integer> res=new ArrayList<Integer>();
        for(int i=1; i<=nums.length; i++){
            if(numsAppeared[i]==0) res.add(i);
        }
        
        return res;
    }
	
	// ---------------------------------------------------------
	// Given an integer array nums, return the third distinct maximum number in this array. 
	// If the third maximum does not exist, return the maximum number.
	public static int thirdBiggestInt_1pass(int[] nums) {
		// runtime O(n); memory usage O(1)
        int[] max=new int[3];
        max[0]=nums[0];
        max[1]=Integer.MIN_VALUE;
        max[2]=Integer.MIN_VALUE;
        
        int count=1;
        
        for(int i=1; i<nums.length; i++){
            int n=nums[i];
            if(n>=max[0]){
                if(n==max[0]) continue;
                max[2]=max[1];
                max[1]=max[0];
                max[0]=n;
                count++;
            }else if(n>=max[1]){
                if(n==max[1] && count>1) continue;
                max[2]=max[1];
                max[1]=n;
                count++;
            }else if(n>=max[2]) {
                max[2]=n;
                count++;
            }
        }
        
        if(count>=3) return max[2];
        return max[0];
    }
	
	public static int thirdBiggestInt_3pass(int[] nums) {
		// runtime O(n); memory usage O(1)
		int max1=nums[0];
        int smallest=nums[0];
        
        for(int n:nums){
            max1=Math.max(n, max1);
            smallest=Math.min(smallest, n);
        }
        
        int max2=smallest;
        for(int n:nums){
            if(n<max1 && n>=max2) max2=n;
        }
        
        int max3=smallest;
        for(int n:nums){
            if(n<max2 && n>=max3) max3=n;
        }
        
        if(max3<max2) return max3;
        return max1;
    }
	
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
	
	public static List<String> summaryRanges_stringbuilder(int[] nums) {
		// runtime O(n) memory O(1)
        List<String> res=new ArrayList<String>();
        int length=nums.length;
        if(length==0) return res;
        
        StringBuilder range=new StringBuilder();
        int startNum=nums[0];
        int endNum=startNum;
        int num;
        
        for(int i=1; i<length; i++){
            num=nums[i];
            if(num-1>endNum) {
                range.append(startNum);
                if(endNum>startNum){
                    range.append("->");
                    range.append(endNum);
                }
                res.add(range.toString());
                range.delete(0, range.length());
                startNum=num;
                endNum=startNum;
                
            }else endNum=num;
        }
        
        range.append(startNum);
        if(endNum>startNum){
            range.append("->");
            range.append(endNum);
        }
        res.add(range.toString());
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
    public static int minSubArrayLen_nlgn(int target, int[] nums) {
        // nlgn
        int[] sums=new int[nums.length];
        sums[0]=nums[0];
        //System.out.print(sums[0]+" ");
        for(int i=1; i<nums.length; i++) {
            sums[i]=nums[i]+sums[i-1];
            //System.out.print(sums[i]+" ");
        }
        //System.out.println();
        if(sums[sums.length-1]<target) return 0;
        
        int minLen=nums.length;
        int cut;
        for(int i=minLen-1; i>=0 && sums[i]>=target; i--){
            if(sums[i]==target) minLen=Math.min(minLen, i+1);
            else{
                cut=Sprinkles.greatestLessThanK(sums, 0, i+1, sums[i]-target);
                //System.out.println(i+" cut: "+cut);
                if(cut>-1) minLen=Math.min(minLen, i-cut);
                else minLen=Math.min(minLen, i+1);
            }
        }
        
        return minLen;
    }
    
	public static int minSubArrayLen_n(int target, int[] nums) { // runtime O(n)
		int minLen=nums.length;
        int leftIndex=0;
        int rightIndex=leftIndex;
        int sum=0;
        while(rightIndex<nums.length && sum<target) sum+=nums[rightIndex++];
        if(sum<target) return 0;
        //System.out.println("right: "+rightIndex);
        rightIndex--;
        while(rightIndex<nums.length){
            if(sum>=target){
                minLen=Math.min(minLen, rightIndex+1-leftIndex);
                sum-=nums[leftIndex];
                leftIndex++;
                //System.out.println("left: "+leftIndex);
            }else{
                if(rightIndex+1<nums.length) sum+=nums[++rightIndex];
                else rightIndex=nums.length;
                //System.out.println("right2: "+rightIndex);
            }
            
        }
        
        return minLen;
    }
	
	private static void minSubArrayLen_test(Scanner inScan, Scanner outScan) {
		while(inScan.hasNextLine() && outScan.hasNextLine()) {
			int target = inScan.nextInt();
			int[] nums = new int[inScan.nextInt()];
			for(int i=0; i<nums.length; i++) nums[i]=inScan.nextInt();
			
			int result = minSubArrayLen_n(target, nums);
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
