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
    
    /* public NumMatrix(int[][] matrix) {
        this.m=new int[matrix.length][matrix[0].length];
        int lastSum=0;
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(j==0) m[i][j]=matrix[i][j];
                else m[i][j]=m[i][j-1]+matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res=0;
        for(int i=row1; i<=row2; i++){
            if(col1==0) res+=this.m[i][col2];
            else res+=this.m[i][col2]-this.m[i][col1-1];
        }
        return res;
    } */
}
