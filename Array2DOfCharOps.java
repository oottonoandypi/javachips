package javachips;

public class Array2DOfCharOps {
	// Count Battleships in a mXn Board
	// Input: m x n matrix board where each cell is a battleship 'X' or empty '.', return the number of the battleships on board
	// Return: the number of the battleships on board.
	// Note: Battleships can only be placed horizontally or vertically on board. 
			// In other words, they can only be made of the shape 1 x k (1 row, k columns) or k x 1 (k rows, 1 column),
			// where k can be of any size. At least one horizontal or vertical cell separates between two battleships (i.e., there are no adjacent battleships)
	
	public static int countBattleships(char[][] board) {
		// time complexity O(mXn) space complexity O(1)
        int countShips=0;
        char c;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                c=board[i][j];
                if(c=='X'){
                    if((i-1>=0 && board[i-1][j]=='X') || (j-1>=0 && board[i][j-1]=='X')) continue;
                    else countShips++;
                }
            }
        }
        return countShips;
    }
}
