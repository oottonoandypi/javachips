package javachips;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class KPairsWithSmallestSums {
	// optimized solution:
	// More efficient method to avoid duplicated visits
	// for each element in nums1, nums1[0]+[nums2[0]...num2[i]] is in ascending order same as ...nums1[j]+[nums2[0]...num2[i]]
	// using priorityqueue, store index of nums2 for each of the element in nums1; and only move forward index of nums2 when the nums1[j] and index of nums2 is off the waitlist
	// 
	class PairComparator2 implements Comparator<int[]>{
	    @Override
	    public int compare(int[] a, int[] b) {
	        int sumA=a[0]+a[1];
	        int sumB=b[0]+b[1];
	        
	        if(sumA>sumB) return 1;
	        else if(sumA<sumB) return -1;
	        else return 0;
	    }
	}
	
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k){
        List<List<Integer>> res=new ArrayList<>();
        PriorityQueue<int[]> pairsInline=new PriorityQueue<int[]>(new PairComparator2());
        
        for(int i=0; i<Math.min(nums1.length, k); i++){
            pairsInline.offer(new int[]{nums1[i], nums2[0], 0});
        }
        
        while(pairsInline.size()>0 && k>0){
            List<Integer> next=new ArrayList<Integer>();
            int[] nextPair=pairsInline.poll();
            next.add(nextPair[0]);
            next.add(nextPair[1]);
            res.add(next);
            if(nextPair[2]+1<nums2.length) pairsInline.offer(new int[]{nextPair[0], nums2[nextPair[2]+1], nextPair[2]+1});
            k--;
        }
        return res;
    }
	
	// improvement 1: insert and sort waitlist can be more efficient if to use a PriorityQueue
	class PairComparator implements Comparator<int[]>{
        @Override
        public int compare(int[] a, int[] b) {
            if(a[2]>b[2]) return 1;
            else if(a[2]<b[2]) return -1;
            else return 0;
        }
    }
 
    public List<List<Integer>> kSmallestPairs_priorityqueue(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res=new ArrayList<>();
        PriorityQueue<int[]> pairsInline=new PriorityQueue<int[]>(new PairComparator());
        int[][] isVisited=new int[nums1.length][nums2.length];
    
        pairsInline.offer(new int[] {0,0,nums1[0]+nums2[0]});
        isVisited[0][0]=1;
        
        while(pairsInline.size()>0 && k>0){
            int[] nextPair=pairsInline.poll();
            List<Integer> curr=new ArrayList<Integer>();
            int currIndex1=nextPair[0];
            int currIndex2=nextPair[1];
            
            int next1Index1=currIndex1+1;
            int next1Index2=currIndex2;
            
            int next2Index1=currIndex1;
            int next2Index2=currIndex2+1;
            
            if(next1Index1<nums1.length && isVisited[next1Index1][next1Index2]==0){
                pairsInline.offer(new int[]{next1Index1, next1Index2, nums1[next1Index1]+nums2[next1Index2]});
                isVisited[next1Index1][next1Index2]=1;
            }
            
            if(next2Index2<nums2.length && isVisited[next2Index1][next2Index2]==0){
                pairsInline.offer(new int[]{next2Index1, next2Index2, nums1[next2Index1]+nums2[next2Index2]});
                isVisited[next2Index1][next2Index2]=1;
            }
            
            curr.add(nums1[currIndex1]);
            curr.add(nums2[currIndex2]);
            res.add(curr);
            k--;
        }
        return res;
    }
	
	// Thought Process;
	// sum of nums1[0] and nums2[0] is guaranteed to be the smallest sum
	// starting at nums1[1]+nums2[0] and nums1[0]+nums2[1], there is possibility of either one is smaller
	// line them up in ascending order
	// take the next smallest to the result list
	// and insert the 2 next possible pairs to the waitlist and make sure the waitlist is always sorted in ascending order
	// To avoid duplication, record when a new pair is added to the waitlist
    // repeat until k is reached
	public List<List<Integer>> kSmallestPairs_draft(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res=new ArrayList<>();
        
        int[][] isVisited=new int[nums1.length][nums2.length];
        
        List<Integer> first=new ArrayList<Integer>();
        first.add(0);
        first.add(0);
        isVisited[0][0]=1;
        res.add(first);
        
        int index=0;
        int count=k;
        
        while(index<res.size() && k>0){
            System.out.println(res.size());
            List<Integer> curr=res.get(index);
            int currIndex1=curr.get(0);
            int currIndex2=curr.get(1);
            
            int next1Index1=currIndex1+1;
            int next1Index2=currIndex2;
            
            int next2Index1=currIndex1;
            int next2Index2=currIndex2+1;
            
            if(next1Index1<nums1.length && isVisited[next1Index1][next1Index2]==0){
                List<Integer> nextList1=new ArrayList<Integer>();
                nextList1.add(next1Index1);
                nextList1.add(next1Index2);
                res.add(nextList1);
                
                for(int i=res.size()-2; i>index && nums1[next1Index1]+nums2[next1Index2]<nums1[res.get(i).get(0)]+nums2[res.get(i).get(1)]; i--){
                    res.set(i+1, res.get(i));
                    res.set(i, nextList1);
                }
                isVisited[next1Index1][next1Index2]=1;
            }
            
            if(next2Index2<nums2.length && isVisited[next2Index1][next2Index2]==0){
                List<Integer> nextList2=new ArrayList<Integer>();
                nextList2.add(next2Index1);
                nextList2.add(next2Index2);
                res.add(nextList2);
                
                for(int i=res.size()-2; i>index && nums1[next2Index1]+nums2[next2Index2]<nums1[res.get(i).get(0)]+nums2[res.get(i).get(1)]; i--){
                    res.set(i+1, res.get(i));
                    res.set(i, nextList2);
                }
                isVisited[next2Index1][next2Index2]=1;
            }
            
            curr.set(0, nums1[currIndex1]);
            curr.set(1, nums2[currIndex2]);
            index++;
            k--;
        }
        if(res.size()<count) return res;
        return res.subList(0, count);
    }
}
