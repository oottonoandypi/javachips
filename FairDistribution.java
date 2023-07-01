package javachips;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class FairDistribution {
	// Input: an array of items (size range from 2 to 8) and an integer k represents k people
	// Return: The smallest unfair count of items a person gets
	// EX. items=[8,15,10,20,8], k=2 => 31 (One optimal distribution between 2 people is [8,15,8] and [10,20])
	
	
	private int minUnfairness=800000; //
    private int[] items;
    private int totalItems;
    private List<HashMap<Integer, Integer>> visited;
    // ithChild's item status smallest maxCount
    
    // Solution #2: dfs by distributing each item to each of the k people
 	// time complexity O(k^n) space complexity O(k)
     private int[] distribution;
     // ithChild's candy status smallest MaxCandy
     
     public int distribute_Solution2(int[] items, int k) {
         this.items=items;
         this.distribution=new int[k];
         
         return optimalUnfairness(0, 0);
         
     }
     
     private int optimalUnfairness(int itemIndex, int remainingItems){
         if(items.length-itemIndex<remainingItems) return minUnfairness;
         if(itemIndex==items.length) {
             int maxCount=distribution[0];
             for(int i=1; i<distribution.length; i++) {
                 if(distribution[i]>maxCount) maxCount=distribution[i];
             }
             return maxCount;
         }
         
         for(int i=0; i<distribution.length; i++){
             distribution[i]+=items[itemIndex];
             if(distribution[i]<minUnfairness) minUnfairness=Math.min(minUnfairness, optimalUnfairness(itemIndex+1, remainingItems-1));
             distribution[i]-=items[itemIndex];
         }
         
         return minUnfairness;
         
     }
    
    // Solution #1: dfs with memorization of ithChild's smallest maxCount when with item status
    // time complexity O(k^n-duplications) space complexity O(k^n)
    public int distribute_Solution1(int[] items, int k) {
        totalItems=(int)Math.pow(2, items.length)-1;
        visited=new ArrayList<>();
        this.items=items;
        
        for(int i=0; i<k; i++) visited.add(new HashMap<Integer, Integer>());
        
        distributeFairly(k, 0, 0);
        // ith child, maxCount, distributeStatus
        
        return minUnfairness;
    }

    private void distributeFairly(int ithChild, int maxCount, int distributeStatus){
        if(ithChild==0 && distributeStatus==totalItems) {
            // System.out.println("hit");
            minUnfairness=Math.min(minUnfairness, maxCount);
        }else if(ithChild!=0 && distributeStatus!=totalItems){
            Queue<int[]> next=new LinkedList<int[]>();
            // itemCount, maxCount, distributeStatus, 
            next.offer(new int[]{0, maxCount, distributeStatus});
            int count;
            int newMax;
            int newDistributeStatus;
            int[] checkout;
            
            while(!next.isEmpty()){
                count=next.size();
                while(count>0){
                    checkout=next.poll();
                    // System.out.println("distributeStatus: "+ checkout[2]);
                    // System.out.println("maxCount: "+ checkout[1]);
                    for(int i=0; i<items.length; i++){
                        
                        if(((1<<i)&checkout[2])!=0) continue;
                        
                        newDistributeStatus=(1<<i)+checkout[2];
                        newMax=Math.max(checkout[1], checkout[0]+items[i]);
                        if(checkout[0]+items[i]<minUnfairness && (!visited.get(ithChild-1).containsKey(newDistributeStatus) || newMax<visited.get(ithChild-1).get((1<<i)+checkout[2]))){ 
                            distributeFairly(ithChild-1, newMax, newDistributeStatus);
                            next.offer(new int[]{checkout[0]+items[i], newMax, newDistributeStatus});
                            visited.get(ithChild-1).put(newDistributeStatus, newMax);
                        }else visited.get(ithChild-1).put(newDistributeStatus, minUnfairness);
                        
                    }
                    count--;
                }
            }
        }
    }
}
