package javachips;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MaxEventsCanBeAttended {
	// Given an array of events where events[i] = [startDayi, endDayi, valuei] and an integer k which represents the maximum number of events you can attend.
	// Return the maximum sum of values that you can receive by attending events.
	// Note: You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on the same day.
	
	
	// Solution #1: dp with memorization; for every event, there are m events that can be attended next
	// time complexity O(n choose k; n^2/(k^2(n-k)^2)) space complexity O(n choose k; n^2/(k^2(n-k)^2))
	class IntComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer a, Integer b){
            if(a<=b) return -1;
            else return 1;
        }
    }
    
    private HashMap<Integer, HashMap<Integer, Integer>> eventVals;
    private List<Integer> starts;
    private int[][] events;
    private int[][] visited;
    public int maxValue(int[][] events, int k) {
        eventVals=new HashMap<Integer, HashMap<Integer, Integer>>();
        starts=new ArrayList<Integer>();
        this.events=events;
        
        for(int[] ev: events){
            if(!eventVals.containsKey(ev[0])) {
                starts.add(ev[0]);
                eventVals.put(ev[0], new HashMap<Integer, Integer>());
            }
            
            if(eventVals.get(ev[0]).containsKey(ev[1])) eventVals.get(ev[0]).put(ev[1], Math.max(ev[2], eventVals.get(ev[0]).get(ev[1])));
            else eventVals.get(ev[0]).put(ev[1], ev[2]);
        }
        
        starts.sort(new IntComparator());
        // System.out.println("starts: "+starts.size());
        // for(int i=0; i<starts.size(); i++) System.out.println(starts.get(i));
        visited=new int[starts.size()][k];
        return findMaxRes(k, 0);
    }
    
    private int findMaxRes(int remain, int index){
        if(remain==0 || index==starts.size()) return 0;
        if(visited[index][remain-1]>0) return visited[index][remain-1];
        
        Iterator<Integer> ends;
        int nextEnd;
        int start;
        int max=0;
        for(int i=index; i<starts.size(); i++){
            start=starts.get(i);
            ends=eventVals.get(start).keySet().iterator();
            while(ends.hasNext()){
                nextEnd=ends.next();
                max=Math.max(max, eventVals.get(start).get(nextEnd)+findMaxRes(remain-1, smallestGreater(i+1, nextEnd+1)));
            }
        }
        visited[index][remain-1]=max;
        return max;
    }
    
    private int smallestGreater(int start, int target){
        int end=starts.size();
        int m;
        while(start<end){
            m=start+(end-start)/2;
            if(starts.get(m)==target) return m;
            if(starts.get(m)<target) start=m+1;
            else end=m;
        }
        
        return start;
    }
}
