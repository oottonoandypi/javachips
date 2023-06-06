package javachips;

import java.util.Queue;
import java.util.LinkedList;

public class Array2DOfIntOps {
	// ---------------------------------------------------------
	// Reshape a 2D array mxn to a 2D array rxc
	// matrix=[[1,2],[3,4]], r = 1, c = 4 => [[1,2,3,4]]
	public static int[][] matrixReshape(int[][] mat, int r, int c) {
        int originalR=mat.length;
        int originalC=mat[0].length;
        
        if(originalR*originalC==r*c){
            int[][] newMat=new int[r][c];
            int indexR=0;
            int indexC=0;
            
            for(int i=0; i<originalR; i++){
                for(int j=0; j<originalC; j++){
                    newMat[indexR][indexC++]=mat[i][j];
                    if(indexC==c){
                        indexR++;
                        indexC=0;
                    }
                }
            }
            return newMat;
        }else return mat;
    }
	
	// ---------------------------------------------------------
	// Given a nxn matrix that represents direct and indirect connections between n places
	// if there are 4 places in total, place 0 is only directly connected to place 3, matrix[0]=[1, 0, 0, 1]
	// places that are directly/indirectly connected counts as 1 component
	// findComponents() returns the number of components
	
	public static int findComponents_grouping(int[][] isConnected) {
		// runtime O(n^2) memory O(n)
        int[] component=new int[isConnected.length];
        int minComponent;
        
        for(int i=0; i<component.length; i++) component[i]=i;
        
        for(int i=0; i<isConnected.length; i++){
            minComponent=component[i];
            for(int j=0; j<isConnected.length; j++){
                if(isConnected[i][j]==1) {
                    if(component[component[j]]<minComponent) {
                        component[j]=component[component[j]];
                        minComponent=component[j];
                    }else component[component[j]]=minComponent;
                }
            }
        }
        
        int count=0;
        for(int i=0; i<component.length; i++){
            if(i==0 || component[i]>i-1) count++;
        }
        return count;
    }
	
	public static int findCircleNum_bfs(int[][] isConnected) {
        // bfs runtime O(n^2) memory O(n)
        boolean[] isVisited=new boolean[isConnected.length];
        Queue<Integer> queue=new LinkedList<Integer>();
        
        int components=isVisited.length;
        int checkout;
        
        for(int v=0; v<isVisited.length; v++){
            if(isVisited[v]) continue;
            
            queue.add(v);
            isVisited[v]=true;
            
            while(!queue.isEmpty()){
                checkout=queue.poll();
                for(int i=0; i<isVisited.length; i++){
                    if(isVisited[i]) continue;
                    if(isConnected[checkout][i]==1){
                        components--;
                        isVisited[i]=true;
                        queue.add(i);
                    }
                }
            }
        }
        
        return components;
    }
	
	public static int findCircleNum_dfs(int[][] isConnected) {
        // dfs runtime O(n^2) memory O(n)
        boolean[] isVisited=new boolean[isConnected.length];
        int components=0;
        for(int i=0; i<isVisited.length; i++){
            if(isVisited[i]) continue;
            components++;
            coverConnections(isConnected[i], isVisited, isConnected);
        }
        return components;
    }
    // helper function for findCircleNum_dfs() ^^
    private static void coverConnections(int[] connections, boolean[] isVisited, int[][] isConnected){
        for(int i=0; i<connections.length; i++){
            if(isVisited[i]) continue;
            if(connections[i]==1) {
                isVisited[i]=true;
                coverConnections(isConnected[i], isVisited, isConnected);
            }
        }
    }
	
	// ---------------------------------------------------------
	// Given a nxn binary matrix (contains only 1s and 0s, 0s mean paths, 1s mean obstacles that can not be crossed)
	// Return the shortest length of path from [0][0] to [n-1][n-1]
	public static int findShortestPathBinaryMatrix(int[][] grid) {
		// BFS approach: runtime O(n^2) memory usage(n^2)
        int width=grid.length;
        if(grid[0][0]==1 || grid[width-1][width-1]==1) return -1;
        
        Queue<int[]> q=new LinkedList<int[]>();
        int len=0;
        
        q.add(new int[]{0,0});
        int qLen=q.size();
        
        int[] cell;
        int cellR;
        int cellC;
        
        while(!q.isEmpty()){
            ++len;
            
            while(qLen>0){
                cell=q.poll();
                cellR=cell[0];
                cellC=cell[1];
                
                if(cellR==width-1 && cellC==width-1) return len;
                
                if(cellR-1>=0) {
                    // if(cellR-1==width-1 && cellC==width-1) return len+1;
                    
                    if(grid[cellR-1][cellC]==0) {
                        q.add(new int[]{cellR-1, cellC});
                        grid[cellR-1][cellC]=1;
                    }
                    
                    if(cellC+1<width && grid[cellR-1][cellC+1]==0){
                        // if(cellR-1==width-1 && cellC+1==width-1) return len+1;
                        q.add(new int[]{cellR-1, cellC+1});
                        grid[cellR-1][cellC+1]=1;
                    }
                    
                    if(cellC-1>=0 && grid[cellR-1][cellC-1]==0) {
                        // if(cellR-1==width-1 && cellC-1==width-1) return len+1;
                        q.add(new int[]{cellR-1, cellC-1});
                        grid[cellR-1][cellC-1]=1;
                    }
                    
                }
                
                if(cellC+1<width && grid[cellR][cellC+1]==0) {
                    // if(cellR==width-1 && cellC+1==width-1) return len+1;
                    q.add(new int[]{cellR, cellC+1});
                    grid[cellR][cellC+1]=1;
                }
                if(cellC-1>=0 && grid[cellR][cellC-1]==0) {
                    // if(cellR==width-1 && cellC-1==width-1) return len+1;
                    q.add(new int[]{cellR, cellC-1});
                    grid[cellR][cellC-1]=1;
                }
                
                if(cellR+1<width) {
                    if(cellC-1>=0 && grid[cellR+1][cellC-1]==0){
                        // if(cellR+1==width-1 && cellC-1==width-1) return len+1;
                        q.add(new int[]{cellR+1, cellC-1});
                        grid[cellR+1][cellC-1]=1;
                    }
                    
                    if(grid[cellR+1][cellC]==0){
                        // if(cellR+1==width-1 && cellC==width-1) return len+1;
                        q.add(new int[]{cellR+1, cellC});
                        grid[cellR+1][cellC]=1;
                    }
                    
                    if(cellC+1<width && grid[cellR+1][cellC+1]==0) {
                        // if(cellR+1==width-1 && cellC+1==width-1) return len+1;
                        q.add(new int[]{cellR+1, cellC+1});
                        grid[cellR+1][cellC+1]=1;
                    }
                    
                }
                
                grid[cellR][cellC]=1;
                qLen--;
            }
            qLen=q.size();
        }
        
        return -1;
	}
	
	// ---------------------------------------------------------
	// grid[i][j]=0 => water; grid[i][j]=1 => land
	// measure the perimeter of land
	public static int landPerimeter(int[][] grid) {
		// runtime O(n^2); memory usage O(1)
        int p=0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                int isLand=grid[i][j];
                if(isLand==1) {
                    p+=4;
                    
                    if(i-1>=0 && grid[i-1][j]==1) p-=2;;
                    if(j-1>=0 && grid[i][j-1]==1) p-=2;
                }
            }
        }
        
        return p;
        
        /*
         * int p=0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                int isLand=grid[i][j];
                if(isLand==1) {
                    p+=4;
                    if(i-1>=0 && grid[i-1][j]==1) p--;
                    if(i+1<grid.length && grid[i+1][j]==1) p--;
                    if(j-1>=0 && grid[i][j-1]==1) p--;
                    if(j+1<grid[i].length && grid[i][j+1]==1) p--;
                }
            }
        }
        
        return p;
         */
    }
	
	// ---------------------------------------------------------
	// Game of Life; find the next state
	// The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or dead (represented by a 0). 
	// Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules:
		// Any live cell with fewer than two live neighbors dies as if caused by under-population.
		// Any live cell with two or three live neighbors lives on to the next generation.
		// Any live cell with more than three live neighbors dies, as if by over-population.
		// Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
	// The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
	public static void gameOfLife(int[][] board) {
		// Runtime O(rows*cols)
        for (int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                int livingNeighbors=0;
                if(i-1>=0 && board[i-1][j]>=1) livingNeighbors++;
                if(i+1<board.length && board[i+1][j]>=1) livingNeighbors++;
                if(j-1>=0 && board[i][j-1]>=1) livingNeighbors++;
                if(j+1<board[i].length && board[i][j+1]>=1) livingNeighbors++;
                if(i-1>=0 && j-1>=0 && board[i-1][j-1]>=1) livingNeighbors++;
                if(i+1<board.length && j+1<board[i].length && board[i+1][j+1]>=1) livingNeighbors++;
                if(i+1<board.length && j-1>=0 && board[i+1][j-1]>=1) livingNeighbors++;
                if(i-1>=0 && j+1<board[i].length && board[i-1][j+1]>=1) livingNeighbors++;
                
                if(board[i][j]==0) board[i][j]=0-livingNeighbors;
                else board[i][j]+=livingNeighbors;
            }
        }
        
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j]<0){
                    if(board[i][j]==-3) board[i][j]=1;
                    else board[i][j]=0;
                }else{
                    if(board[i][j]==3 || board[i][j]==4) board[i][j]=1;
                    else board[i][j]=0;
                }
            }
        }
    }
	
	// ---------------------------------------------------------
	public static boolean searchTarget_nlgn(int[][] matrix, int target) {
		// Approach: binary search on row and column for every diagonal top left cell
		// runtime O(nlgn)
        int h=matrix.length;
        int w=matrix[0].length;
        int shorterSide=Math.min(h,w);
        
        for(int i=0; i<shorterSide; i++){
            if(target==matrix[i][i]) return true;
            
            int left=i+1;
            int right=w-1;
            while(left<=right){
                int mid = (left+right)/2;
                if(target==matrix[i][mid]) return true;
                else if(target<matrix[i][mid]) right=mid-1;
                else left=mid+1;
            }
            
            int top=i+1;
            int bottom=h-1;
            while(top<=bottom){
                int mid=(top+bottom)/2;
                if(target==matrix[mid][i]) return true;
                else if(target<matrix[mid][i]) bottom=mid-1;
                else top=mid+1;
            }
        }
        
        return false;
    }
	
	// ---------------------------------------------------------
	public static boolean searchTarget_optimized(int[][] matrix, int target) { 
		// O(row+col)
        int x=0;
        int y=matrix[x].length-1;
        
        while(x<matrix.length && y>=0){
            if(target==matrix[x][y]) return true;
            else if(target<matrix[x][y]) y--;
            else x++;
        }
        
        return false;
    }
}
