package javachips;

public class Array2DOfIntOps {
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
