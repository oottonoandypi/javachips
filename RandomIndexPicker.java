package javachips;

import java.util.HashMap;
import java.util.ArrayList;

public class RandomIndexPicker {
	// Given an integer array nums with possible duplicates, randomly output the index of a given target number
	
	// Solution 2: random sampling
    // time complexity: initialization O(1) pick O(n)
    // space complexity: O(1)
    // Only efficient when pick is called once
	
	private int[] nums;
	private HashMap<Integer, ArrayList<Integer>> indiceMap;
	
    public RandomIndexPicker(int[] nums, int pick) {
    	this.nums=nums;
        
        // below part is only used for pick_solution1
    	if(pick==1) {
    		indiceMap=new HashMap<Integer, ArrayList<Integer>>();
            for(int i=0; i<nums.length; i++){
                if(!indiceMap.containsKey(nums[i])) indiceMap.put(nums[i], new ArrayList<Integer>());
                indiceMap.get(nums[i]).add(i);
            }
    	}
    }
    
    public int pick_solution2(int target) {
        int countIndice=0;
        int pickedIndex=-1;
        int newPick=-1;
        
        for(int i=0; i<nums.length; i++){
            if(nums[i]==target){
                countIndice++;
                newPick=(int)(Math.random()*countIndice);
                if(pickedIndex==-1 || newPick==0) pickedIndex=i;
            }
        }
        
        return pickedIndex;
    }
	
	// Solution 1: time complexity: initialization O(n) pick() O(1); space complexity O(n)
    // Efficient when pick is called many times
    
    public int pick_solution1(int target) {
        if(!indiceMap.containsKey(target)) return -1;
        ArrayList<Integer> targetIndice=indiceMap.get(target);
        return targetIndice.get((int)(Math.random()*targetIndice.size()));
    }

}
