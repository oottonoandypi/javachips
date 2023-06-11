package javachips;

import java.util.List;
import java.util.ArrayList;

// SnapshotArray supports the following interface:

// SnapshotArray(int length) initializes an array-like data structure with the given length. Initially, each element equals 0.
// initiation takes O(n) runtime
// void set(index, val) sets the element at the given index to be equal to val. Takes O(1) runtime
// int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1. Takes O(1) runtime
// int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id. Takes O(nlgn) runtime

public class SnapshotArray {
	private List<List<int[]>> changes;
    private int lastSnap;
    
    public SnapshotArray(int length) {
        this.changes=new ArrayList<>();
        for(int i=0; i<length; i++){
            this.changes.add(new ArrayList<int[]>());
        }
        this.lastSnap=-1;
    }
    
    public void set(int index, int val) {
        List<int[]> indexChanges=changes.get(index);
        int indexChangesSize=indexChanges.size();
        if(indexChangesSize==0 || indexChanges.get(indexChangesSize-1)[0]<this.lastSnap+1){
            indexChanges.add(new int[]{this.lastSnap+1, val});
        }else{
            indexChanges.get(indexChangesSize-1)[1]=val;
        }
    }
    
    public int snap() {
        this.lastSnap++;
        return this.lastSnap;
    }
    
    public int get(int index, int snap_id) {
        List<int[]> indexChanges=changes.get(index);
        int l=0;
        int r=indexChanges.size()-1;
        int m;
        int[] snap;
        int closestSnap=0;
        
        //System.out.println("index: "+index);
        //System.out.println("changes: "+indexChanges.size());
        if(r<0) return 0;
        while(l<r){
            m=l+(r-l)/2;
            snap=indexChanges.get(m);
            if(snap[0]==snap_id) return snap[1];
            else if(snap[0]<snap_id) {
                closestSnap=Math.max(closestSnap,m);
                l=m+1;
            }else r=m-1;
        }
        // System.out.println("closestSnap: "+closestSnap);
        //System.out.println("snapAt: "+indexChanges.get(closestSnap)[1]);
        if(closestSnap<0) return 0;
        if(indexChanges.get(l)[0]<=snap_id) closestSnap=l;
        if(indexChanges.get(closestSnap)[0]>snap_id) closestSnap--;
        if(closestSnap<0) return 0;
        return indexChanges.get(closestSnap)[1];
    }
}
