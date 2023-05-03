package javachips;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class MinHeightTrees {
	// ---------------------------------------------------------
	public List<Integer> findMHTs_linear(int n, int[][] edges) {
		// runtime O(Vertices+Edges of the tree)
		
        List<Integer> leaves=new ArrayList<Integer>();
        
        if(n<2) {
            for(int i=0; i<n; i++){
                leaves.add(i);
            }
            return leaves;
        }
        
        HashSet<Integer>[] neighbors=new HashSet[n];
        for(int[] edge: edges){
            if(neighbors[edge[0]]==null) neighbors[edge[0]]=new HashSet<Integer>();
            if(neighbors[edge[1]]==null) neighbors[edge[1]]=new HashSet<Integer>();
            neighbors[edge[0]].add(edge[1]);
            neighbors[edge[1]].add(edge[0]);
        }
        
        for(int i=0; i<n; i++){
            if(neighbors[i].size()==1) leaves.add(i);
        }
        
        int remainings=n-leaves.size();
        
        while(remainings>0){
            // System.out.println(remainings);
            List<Integer> newLeaves=new ArrayList<Integer>();
            
            for(int l=0; l<leaves.size(); l++){
                Integer leaf=leaves.get(l);
                Integer remainingConnection=neighbors[leaf].iterator().next();
                neighbors[remainingConnection].remove(leaf);
                if(neighbors[remainingConnection].size()==1) newLeaves.add(remainingConnection);
            }
            
            leaves=newLeaves;
            remainings-=leaves.size();
        }
        
        return leaves;
    }
	
	// ---------------------------------------------------------
	public List<Integer> findMHTs_naive(int n, int[][] edges) {
        int[][] shortestPaths=new int[n][n];
        for(int[] edge: edges){
            shortestPaths[edge[0]][edge[1]]=1;
            shortestPaths[edge[1]][edge[0]]=1;
        }
        
        /*for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(shortestPaths[i][j]+" ");
            }
            System.out.println();
        }*/
        
        int shortestDepth=n;
        List<Integer> res=new ArrayList<Integer>();
        
        for(int r=0; r<n; r++){
            int depth=0;
            Queue<Integer> parents=new LinkedList<Integer>();
            parents.add(r);
            
            HashSet<Integer> remainings=new HashSet<Integer>();
            for(int v=0; v<n; v++){
                if(v==r) continue;
                remainings.add(v);
            }
            
            while(!remainings.isEmpty()){
                int p=parents.poll();
                int minPN=0;
                for(int pN=0; pN<n; pN++){
                    if(!remainings.contains(pN)) continue;
                    //if(shortestPaths[])
                    
                    if(shortestPaths[p][pN]==1){
                        shortestPaths[r][pN]=shortestPaths[r][p]+shortestPaths[p][pN];
                        shortestPaths[pN][r]=shortestPaths[r][pN];
                        depth=Math.max(depth, shortestPaths[r][pN]);
                        // System.out.println("depth: "+depth);
                        remainings.remove(pN);
                        parents.add(pN);
                    }
                    
                }
                
                /*System.out.println();
                for(int i=0; i<n; i++){
                    for(int j=0; j<n; j++){
                        System.out.print(shortestPaths[i][j]+" ");
                    }
                    System.out.println();
                }*/
            }
            // System.out.println("depthth: "+depth);
            if(depth<shortestDepth) {
                res=new ArrayList<Integer>();
                res.add(r);
                shortestDepth=depth;
            }else if(depth==shortestDepth){
                res.add(r);
            }
            
            
        }
        
        return res;
        
    }
}
