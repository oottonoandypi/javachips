package javachips;

import java.util.Arrays;

public class RelativeRank {
	// Given a unordered array of scores
	// Find the relative ranks and return in an array
	// 1st, 2nd, 3rd places are "Gold Medal", "Silver Medal", and "Bronze Medal"
	// Starting at 4th place, ranks are "4", "5", "6", etc.
	public String[] findRelativeRanks(int[] score) {
		// runtime O(nlgn) memory usage O(n)
        int[] indices=new int[score.length];
        for(int i=0; i<indices.length; i++){
            indices[i]=i;
        }
        
        mergeSortIndices(indices, score, 0, indices.length-1);
        
        String[] res=new String[score.length];
        for(int i=0; i<res.length; i++){
            if(i==0) res[indices[0]]="Gold Medal";
            else if(i==1) res[indices[1]]="Silver Medal";
            else if(i==2) res[indices[2]]="Bronze Medal";
            else res[indices[i]]=Integer.toString(i+1);
        }
        return res;
    }
    
    private void mergeSortIndices(int[] indices, int[] score, int l, int r){
        int m;
        if(l<r){
            m=l+(r-l)/2;
            mergeSortIndices(indices, score, l, m);
            mergeSortIndices(indices, score, m+1, r);
            mergeIndices(indices, score, l, m, r);
        }
    }
    
    private void mergeIndices(int[] indices, int[] score, int l, int m, int r){
        int[] leftIndices=Arrays.copyOfRange(indices, l, m+1);
        int[] rightIndices=Arrays.copyOfRange(indices, m+1, r+1);
        
        int lI=0;
        int rI=0;
        int i=l;
        
        while(lI<leftIndices.length && rI<rightIndices.length){
            if(score[leftIndices[lI]]>=score[rightIndices[rI]]){
                indices[i++]=leftIndices[lI++];
            }else{
                indices[i++]=rightIndices[rI++];
            }
        }
        
        while(lI<leftIndices.length){
            indices[i++]=leftIndices[lI++];
        }
        
        while(r<rightIndices.length){
            indices[i++]=leftIndices[rI++];
        }
    }
}
