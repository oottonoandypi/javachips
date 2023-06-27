package javachips;

public class SudokuSolver {
	// Input: a 9x9 Sudoku board char[][] where some cells are filed with ['1'...'9'], others are '.' means need to fill in valid ['1'...'9']
	// SudokuSolver's solveSudoku function solves Sudoku puzzle by filling the empty/'.' cells
	
	/* Rule:
	 * A sudoku solution must satisfy all of the following rules:
	 * Each of the digits 1-9 must occur exactly once in each row.
	 * Each of the digits 1-9 must occur exactly once in each column.
	 * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
	 */
	
	private boolean[][] rows=new boolean[9][10];
    private boolean[][] cols=new boolean[9][10];
    private boolean[][] sqrs=new boolean[9][10];
    private char[][] board;
    private boolean solved;
    
    public void solveSudoku(char[][] board) {
    	// time complexity O(9!^9) space complexity O(10^2*3)
        this.rows=new boolean[9][10];
        this.cols=new boolean[9][10];
        this.sqrs=new boolean[9][10];
        this.board=board;
        this.solved=false;
        
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j]!='.'){
                    rows[i][board[i][j]-48]=true;
                    cols[j][board[i][j]-48]=true;
                    sqrs[i/3+(j/3)*3][board[i][j]-48]=true;
                }
            }
        }
        
        solve(0, 0);
        
    }
    
    private void solve(int row, int col){
        if(row==9) {
            solved=true;
            return;
        }
        
        if(board[row][col]!='.'){
            if(col+1==9) solve(row+1, 0);
            else solve(row, col+1);
        }else{
            for(int i=1; i<=9 && !solved; i++){
                if(!rows[row][i] && !cols[col][i] && !sqrs[row/3+(col/3)*3][i]) {
                    board[row][col]=(char)(i+48);
                    rows[row][i]=true;
                    cols[col][i]=true;
                    sqrs[row/3+(col/3)*3][i]=true;
                    
                    if(col+1==9) solve(row+1, 0);
                    else solve(row, col+1);
                    
                    if(!solved){
                        board[row][col]='.';
                        rows[row][i]=false;
                        cols[col][i]=false;
                        sqrs[row/3+(col/3)*3][i]=false;
                    }else return;
                }
            }
        }
    }
}
