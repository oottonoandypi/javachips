package javachips;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Array2DOfIntOps {
	// ---------------------------------------------------------
	// Given an m x n matrix M initialized with all 0's 
	// and an array of operations ops, where ops[i] = [ai, bi] 
		// means M[x][y] should be incremented by one for all 0 <= x < ai and 0 <= y < bi.
	// Count and return the number of maximum integers in the matrix after performing all the operations.
	public static int countMaxNums(int m, int n, int[][] ops) {
		// runtime O(ops.length) memory O(1)
        int[] area=new int[]{m, n};
        
        for(int[] op: ops){
            if(op[0]<area[0]) area[0]=op[0];
            if(op[1]<area[1]) area[1]=op[1];
        }
        
        return area[0]*area[1];
    }

	
	// ---------------------------------------------------------
	// Given a 0-indexed n x n integer matrix grid, 
	// return the number of pairs (ri, cj) such that row ri and column cj are equal.

	// Note: A row and column pair is considered equal if they contain the same elements in the same order (i.e., an equal array).
	// EX. grid = [[3,2,1],[1,7,6],[2,7,7]] => There is 1 equal row and column pair (Row 2, Column 1): [2,7,7]
	public static int equalRowColPairs_hashmap(int[][] grid) {
		// runtime O(n^2) memory (n^2)
		int n=grid.length;
		NumberPaths_HashMap startOfPaths=new NumberPaths_HashMap();
        int count=0;
        NumberPaths_HashMap parent;
        
        for(int r=0; r<n; r++){
            parent=startOfPaths;
            for(int c=0; c<n; c++) parent=parent.addPath(grid[r][c]);
            parent.endCount++;
        }
        
        for(int c=0; c<n; c++){
            parent=startOfPaths;
            for(int r=0; r<n; r++){
                parent=parent.getNext(grid[r][c]);
                if(parent==null) break;
            }
            if(parent!=null) count+=parent.endCount;
        }
        
        return count;
	}
	// helper class for function equalRowColPairs_hashmap() ^^
	static class NumberPaths_HashMap{
        public HashMap<Integer, NumberPaths_HashMap> nextPaths;
        public int endCount;
        
        public NumberPaths_HashMap(){
            this.nextPaths=new HashMap<Integer, NumberPaths_HashMap>();
            this.endCount=0;
        }
        
        public NumberPaths_HashMap addPath(int val){
            if(!this.nextPaths.containsKey(val)) this.nextPaths.put(val, new NumberPaths_HashMap());
            return this.nextPaths.get(val);
        }
        
        public NumberPaths_HashMap getNext(int val){
            return this.nextPaths.get(val);
        }
    }
	
	public static int equalRowColPairs_math(int[][] grid) {
		// runtime O(n^2) memory O(n)
		int n=grid.length;
        int count=0;
        long[] row= new long[n];
        long[] col= new long[n];
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                row[i]=row[i]*5+grid[i][j];
            }
        }
        
        for(int j=0; j<n; j++){
            for(int i=0; i<n; i++){
                col[j]=col[j]*5+grid[i][j];
            }
        }
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(row[i]==col[j]) count++;
            }
        }
        return count;
	}
	
	public static int equalRowColPairs_bruteforce(int[][] grid) {
		// runtime O(n^3) memory O(1)
        int n=grid.length;
        int count=0;
        int i;
        for(int[] row: grid){
            for(int j=0; j<n; j++){
                i=0;
                while(i<n){
                    if(grid[i][j]!=row[i]) break;
                    i++;
                }
                if(i==n) count++;
            }
        }
        return count;
    }
	
	// ---------------------------------------------------------
	// Given a non-decreasing ordered matrix
	// Return the k-th smallest value in the matrix (not the kth distinct)
	public static int kthSmallest_binsearch(int[][] matrix, int k) {
        // binary search runtime <O(klgn) memory O(1)
        int width=matrix.length;
        int smallestVal=matrix[0][0];
        int biggestVal=matrix[width-1][width-1]+1;
        int middleVal;
        int count;
        int j;
        
        while(smallestVal<biggestVal){
            middleVal=smallestVal+(biggestVal-smallestVal)/2;
            count=0;
            for(int i=0; i<width; i++){
                j=width-1;
                while(j>=0 && matrix[i][j]>middleVal) j--;  
                count+=j+1;
            }
            if(count<k) smallestVal=middleVal+1;
            else biggestVal=middleVal;
        }
        return smallestVal;
    }
	
	public static int kthSmallest_priorityqueue(int[][] matrix, int k) {
        // runtime O(klgn) memory O(n)
        PriorityQueue<int[]> coordinates=new PriorityQueue<int[]>(new MatrixComparator());
        int width=matrix.length;
        int last=matrix[0][0];
        int[] checkout;
        for(int i=0; i<width; i++){
            coordinates.add(new int[]{i, 0, matrix[i][0]});
        }
        
        while(k>0 && !coordinates.isEmpty()){
            checkout=coordinates.poll();
            last=checkout[2];
            checkout[1]++;
            if(checkout[1]<width) {
                checkout[2]=matrix[checkout[0]][checkout[1]];
                coordinates.add(checkout);
            }
            k--;
        }
        
        return last;
    }
    // helper class for kthSmallest_priorityqueue() ^^
    static class MatrixComparator implements Comparator<int[]>{
        @Override
        public int compare(int[] a, int[] b){
            if(a[2]<=b[2]) return -1;
            else return 1;
        }
    }
	
	
	// ---------------------------------------------------------
	// Count negative numbers in a sorted 2D matrix that is in non-increasing order both row-wise and column-wise.
	public static int countNegativesInSortedMatrix_log(int[][] grid) {
		// runtime O(lgm+lgn) memory O(1)
        int rows=grid.length;
        int cols=grid[0].length;
        int count=0;
        int j=cols-1;
        int i=0;
        boolean onRow=true;
        int biggestNegIndex;
            
        while(i<rows && j>=0){
            if(onRow){
                biggestNegIndex=__binSearchNegRow__(0, j, grid[i]);
                //System.out.println("rowIndex "+biggestNegIndex);
                if(biggestNegIndex<cols && grid[i][biggestNegIndex]<0) count+=(j-biggestNegIndex+1)*(rows-i);
                j=biggestNegIndex-1;
                i++;
                onRow=false;
            }else{
                biggestNegIndex=__binSearchNegCol__(i, rows-1, grid, j);
                //System.out.println("colIndex "+biggestNegIndex);
                if(biggestNegIndex<rows && grid[biggestNegIndex][j]<0) count+=rows-biggestNegIndex;
                i=biggestNegIndex;
                j--;
                onRow=true;
            }
        }
        
        return count;
    }
    // helper function for countNegatives_log()^^
    private static int __binSearchNegRow__(int l, int r, int[] row){
        int mid;
        while(l<=r){
            mid=l+(r-l)/2;
            if(row[mid]<0) r=mid-1;
            else l=mid+1;
        }
        
        return l;
    }
    // helper function for countNegatives_log()^^
    private static int __binSearchNegCol__(int t, int d, int[][] grid, int col){
        int mid;
        while(t<=d){
            mid=t+(d-t)/2;
            if(grid[mid][col]<0) d=mid-1;
            else t=mid+1;
        }
        
        return t;
    }
	
	public static int countNegativesInSortedMatrix_linear(int[][] grid) {
		// runtime O(row+col) memory O(1)
        int rows=grid.length;
        int cols=grid[0].length;
        int count=0;
        int j=cols-1;
        
        for(int i=0; i<rows; i++){
            while(j>=0){
                if(grid[i][j]<0) {
                    count+=rows-i;
                    j--;
                }else break;
            }
        }
        
        return count;
    }
	
	// ---------------------------------------------------------
	// Given an integer array nums, return the length of the longest wiggle subsequence of nums.
	public static int maxLengthOfWiggleSubsequence_n(int[] nums) {
		// Linear greedy approach runtime O(n) memory O(1)
		// when nums[i-1]-nums[i] stay in the previous status (increasing/decreasing), max length stay the same
		// until the status of (increasing/decreasing) changes
        int numsLen=nums.length;
        if(numsLen==1) return numsLen;
        
        int prevStatus=0;
        if((nums[0]-nums[1])!=0) prevStatus=(nums[0]-nums[1])/Math.abs(nums[0]-nums[1]);
        //int prev=1;
        int maxLen=2;
        if(prevStatus==0) maxLen=1;
        
        for(int i=2; i<nums.length; i++){
            if(nums[i-1]-nums[i]==0) continue;
            if((nums[i-1]-nums[i])/Math.abs(nums[i-1]-nums[i])!=prevStatus){
                maxLen++;
                prevStatus=(nums[i-1]-nums[i])/Math.abs(nums[i-1]-nums[i]);
            }
            //prev=i;
        }
        return maxLen;
    }
	
	public static int maxLengthOfWiggleSubsequence_nn(int[] nums) {
		// Brute force approach, runtime O(n^2) memory O(n)
		// for i in range nums.length; record the max length of next increasing and next decreasing seq
        int numsLen=nums.length;
        int[][] sequenceLength=new int[numsLen][2];
        int currNum;
        
        for(int i=nums.length-1; i>=0; i--){
            currNum=nums[i];
            for(int j=i+1; j<numsLen; j++){
                if(nums[j]>currNum){
                    sequenceLength[i][0]=Math.max(sequenceLength[i][0], sequenceLength[j][1]+1);
                }else if(nums[j]<currNum){
                    sequenceLength[i][1]=Math.max(sequenceLength[i][1], sequenceLength[j][0]+1);
                }
            }
            if(sequenceLength[i][0]==0) sequenceLength[i][0]=1;
            if(sequenceLength[i][1]==0) sequenceLength[i][1]=1;
        }
        
        return Math.max(sequenceLength[0][0],sequenceLength[0][1]);
    }
	
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
	
	public static int findComponents_bfs(int[][] isConnected) {
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
	
	public static int findComponents_dfs(int[][] isConnected) {
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
