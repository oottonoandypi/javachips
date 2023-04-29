package javachips;

class NumMatrix {
    private int[][] m;
    public NumMatrix(int[][] matrix) {
        this.m=new int[matrix.length+1][matrix[0].length+1];
        
        for(int i=1; i<m.length; i++){
            for(int j=1; j<m[0].length; j++){
                this.m[i][j]=this.m[i][j-1]+this.m[i-1][j]-this.m[i-1][j-1]+matrix[i-1][j-1];
                // System.out.print(this.m[i][j]+" ");
            }
            // System.out.println();
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return this.m[row2+1][col2+1]-this.m[row2+1][col1+1-1]-this.m[row1+1-1][col2+1]+this.m[row1+1-1][col1+1-1];
    }
}
