package javachips;

import java.util.HashMap;

public class RandomizedSet {
	// RandomizedSet() Initializes the RandomizedSet object.
	// bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
	// bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false otherwise.
	// int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
	
	// All 3 operations take O(1) time complexity
	// memory O(n)
	// Note: The max operations can be called is max possible size of an array
	private int[] list;
    private int lastEleIndex;
    private HashMap<Integer, Integer> indices;
    
    public RandomizedSet(int limitOps) {
        this.list=new int[limitOps+1];
        this.lastEleIndex=-1;
        this.indices=new HashMap<Integer, Integer>();
    }
    
    public boolean insert(int val) {
        if(indices.containsKey(val)) return false;
        list[++lastEleIndex]=val;
        indices.put(val, lastEleIndex);
        return true;
    }
    
    public boolean remove(int val) {
        if(!indices.containsKey(val)) return false;
        int index=indices.remove(val);
        int lastEle=list[lastEleIndex--];
        if(lastEle!=val) {
            list[index]= lastEle;
            indices.put(lastEle, index);
        }
        return true;
    }
    
    public int getRandom() {
        int index=(int)(Math.random()*(lastEleIndex+1));
        return list[index];
    }
}
