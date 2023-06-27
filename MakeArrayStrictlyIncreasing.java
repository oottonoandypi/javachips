package javachips;

import java.util.Arrays;

public class MakeArrayStrictlyIncreasing {
	// Input: two integer arrays arr1 and arr2
	// Returns: the minimum number of operations (possibly zero) needed to make arr1 strictly increasing.
	// In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do the assignment arr1[i] = arr2[j].
	// If there is no way to make arr1 strictly increasing, return -1.
	
	// EX. Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
	//     Output: 1; Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7]
	
	
	// Solution #1:
	// recursive approach using postorder traversal with memorization
	// time complexity O(arr1.length*arr2.length*lg(arr2.length)) memory O(arr1.length*arr2.length)
    private boolean[][] ifVisited;
    private int[][] visitedChanges;
    private int arr2Rightbound;
    
    public int minChanges_recursive(int[] arr1, int[] arr2) {
        Arrays.sort(arr2);
        
        arr2Rightbound=0;
        for(int i=1; i<arr2.length; i++){
            if(arr2[i]>arr2[arr2Rightbound]) arr2[++arr2Rightbound]=arr2[i];
        }
        
        ifVisited=new boolean[arr1.length+1][arr2Rightbound+2];
        visitedChanges=new int[arr1.length+1][arr2Rightbound+2];
        
        return findMinChanges(arr1, arr2, -1, -1);
    }
    
    private int findMinChanges(int[] arr1, int[] arr2, int prevChange, int index){
        if(index==arr1.length){
            if(prevChange==-1) return 0;
            else return 1;
        }else if(ifVisited[index+1][prevChange+1]) return visitedChanges[index+1][prevChange+1];
        
        int changesKeep=-1;
        int prev=-1;
        if(index>=0){
            if(prevChange==-1) prev=arr1[index];
            else prev=arr2[prevChange];
        }
        if(index+1==arr1.length || arr1[index+1]>prev) changesKeep=findMinChanges(arr1, arr2, -1, index+1);
        
        int changesChange=-1;
        int smallestGreaterThanPrev=findSmallestGreater(prev, arr2, prevChange+1, arr2Rightbound);
        if(smallestGreaterThanPrev>-1){
            changesChange=findMinChanges(arr1, arr2, smallestGreaterThanPrev, index+1);
            if(changesChange>-1) changesChange++;
        }
        
        int changes=changesKeep;
        if(changes==-1) changes=changesChange;
        else if(changesChange>-1) changes=Math.min(changes, changesChange);
        
        ifVisited[index+1][prevChange+1]=true;
        visitedChanges[index+1][prevChange+1]=changes;
        return changes;
    } 
    
    private int findSmallestGreater(int target, int[] puddle, int puddleLeftbound, int puddleRightbound){
        // System.out.println("search "+target+" in the range: "+l+" "+r);
        int l=puddleLeftbound;
        int r=puddleRightbound;
        int m;
        
        while(l<=r){
            m=l+(r-l)/2;
            if(puddle[m]<=target) l=m+1;
            else r=m-1;
        }
        // System.out.println("l: "+l);
        if(l>puddleRightbound) l--;
        if(l<puddleLeftbound || puddle[l]<=target) return -1;
        return l;
    }
    
    // Solution #2:
    // iterative approach with memorization
    // time complexity O(arr1.length*arr2.length*lg(arr2.length)) memory O(arr1.length*arr2.length)
    public int minChanges_iterative(int[] arr1, int[] arr2) {
        Arrays.sort(arr2);
        int uniqueArr2=0;
        for(int i=1; i<arr2.length; i++){
            if(arr2[i]>arr2[uniqueArr2]) arr2[++uniqueArr2]=arr2[i];
        }
        
        int[] prev=new int[uniqueArr2+2];
        int[] curr;
        int compareVal;
        int greatestSmaller;
        
        for(int i=arr1.length-1; i>=0; i--){
            curr=new int[uniqueArr2+2];
            Arrays.fill(curr, -1);
            
            for(int j=0; j<prev.length; j++){
                if(prev[j]>-1){
                    if(j==0) {
                        if(i==arr1.length-1) compareVal=Integer.MAX_VALUE;
                        else compareVal=arr1[i+1];
                    }else {
                        compareVal=arr2[j-1];
                    }
                    
                    if(arr1[i]<compareVal){
                        if(curr[0]==-1) curr[0]=prev[j];
                        else curr[0]=Math.min(curr[0], prev[j]);
                    }
                    greatestSmaller=findGreatestSmaller(compareVal, arr2, uniqueArr2+1);
                    // System.out.println("greatest smaller than "+compareVal+" is at "+greatestSmaller);
                    if(greatestSmaller>-1){
                        if(curr[greatestSmaller+1]==-1) curr[greatestSmaller+1]=1+prev[j];
                        else curr[greatestSmaller+1]=Math.min(curr[greatestSmaller+1], 1+prev[j]);
                    }
                }
            }
            /*
            for(int k=0; k<curr.length; k++) System.out.print(curr[k]+" ");
            System.out.println(); */
            prev=curr;
        }
        
        
        int res=-1;
        for(int n: prev) {
            // System.out.println(n);
            if(n>-1){
                if(res==-1) res=n;
                else res=Math.min(res, n);
            }
        }
        
        return res;
    }
    
    private int findGreatestSmaller(int target, int[] arr2, int rightBound){
        int l=0;
        int r=rightBound;
        int m;
        int res=-1;
        
        while(l<r){
            m=l+(r-l)/2;
            if(arr2[m]<target) {
                if(res==-1) res=m;
                else res=Math.max(res, m);
                l=m+1;
            }else r=m-1;
        }
        if(l<rightBound && arr2[l]<target) res=Math.max(res, l);
        return res;
    }
}
