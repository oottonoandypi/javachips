package javachips;

public class Array2DOfIntOps {
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
