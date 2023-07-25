package javachips;

public class ChessGame {
	// Knight Probability in Chessboard
	// Input: an integer n (represents a nXn chessboard); an integer k (represents k moves); an integer r and an integer c (represents starting cell position at [r][c]
	// Return: the probability that the knight remains on the board after it has stopped moving.
	// Rule: A chess knight has eight possible moves it can make. Each move is two cells in a cardinal direction, then one cell in an orthogonal direction
			// Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece would go off the chessboard) and moves there.
			// The knight continues moving until it has made exactly k moves or has moved off the chessboard.
	
	public static double knightProbability_iterative(int n, int k, int row, int column) {
		// Iterative DP approach
		// time complexity O(k*n^2) space complexity (k*n^2);
        double[][][] prob=new double[n][n][k+1];
        int[][] moves=new int[][]{{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}};
        int r;
        int c;
        double p;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                prob[i][j][k]=1;
            }
        }
        
        while(--k>=0){
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    p=0;
                    for(int[] move: moves){
                        r=i+move[0];
                        c=j+move[1];
                        
                        if(r>=0 && r<n && c>=0 && c<n) p+=prob[r][c][k+1];
                    }
                    
                    prob[i][j][k]=p/8;
                }
            }
        }
        
        return prob[row][column][0];
    }
	
	public static double knightProbability_recursive(int n, int k, int row, int column) {
		// Recursive DP approach
		// time complexity O(k*n^2) space complexity (k*n^2);
        int[][] moves=new int[][]{{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}};
        double[][][] prob=new double[n][n][k+1];
        return findProb(moves, prob, k, row, column, n);
    }
    
    private static double findProb(int[][] moves, double[][][] prob, int k, int r, int c, int n){
        if(k==0){
            if(r>=0 && r<n && c>=0 && c<n) return (double)1;
            else return (double)0;
        }else if(r<0 || r>=n || c<0 || c>=n) return (double)0;
        else if(prob[r][c][k]>0) return prob[r][c][k];
        
        double onBoardPaths=0;
        for(int[] move: moves){
            onBoardPaths+=findProb(moves, prob, k-1, r+move[0], c+move[1], n);
        }
        
        prob[r][c][k]=onBoardPaths/8;
        // System.out.println(onBoardPaths);
        return prob[r][c][k];
    }
}
