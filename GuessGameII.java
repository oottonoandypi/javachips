package javachips;

public class GuessGameII {
	private int n;
	public GuessGameII(int target) {
		this.n=target;
		/* RULE:
		 *  Knowing the target number is between 1 and n.
			You guess a number.
			If you guess the right number, you win the game.
			If you guess the wrong number, then I will tell you whether the number I picked is higher or lower, and you will continue guessing.
			Every time you guess a wrong number x, you will pay x dollars. If you run out of money, you lose the game.
		 */
	}
	
	// Given a particular n
	// return the minimum amount of money you need to guarantee a win regardless of what number I pick
	public int getMoneyAmount() {
        if(n<=1) return 0;
        int[][] memory=new int[n+1][n+1];
        
        return getMinMoney(1, n, memory);
    }
    
    private int getMinMoney(int l, int r, int[][] memory){
        if(l==r) return 0;
        else if(memory[l][r]>0) return memory[l][r];
        else if(l-r==-1){
            memory[l][r]=l;
            return l;
        }else{
            int mid=l+(r-l)/2;
            int min=Integer.MAX_VALUE;
            
            while(mid<r){
                min=Math.min(min, Math.max(getMinMoney(l, mid-1, memory),getMinMoney(mid+1, r, memory))+mid);
                mid++;
            }
            memory[l][r]=min;
            // System.out.println("l: "+l+" r: "+r+" "+memory[l][r]);
            return min;
        }
        
    }
}
