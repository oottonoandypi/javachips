package javachips;

public class Stock {
	private int[] prices;
	
	public Stock(int[] prices) {
		this.prices=prices;
	}
	
	
	// ---------------------------------------------------------
	// find the max profit by performing BUY, SELL, COOLDOWN
	// DP solution with runtime O(n)
	public int maxProfit_DP(int[] prices) {
        int[] buys=new int[prices.length];
        int[] rests=new int[prices.length];
        int[] sells=new int[prices.length];
        
        buys[0]=0-prices[0];
        rests[0]=0;
        sells[0]=0;
        
        for(int i=1; i<prices.length; i++){
            buys[i]=Math.max(rests[i-1]-prices[i], buys[i-1]);
            rests[i]= Math.max(sells[i-1], rests[i-1]);
            sells[i]= buys[i-1]+prices[i];
        }
        
        return Math.max(sells[prices.length-1], rests[prices.length-1]);
    }
	
	// ---------------------------------------------------------
	// find the max profit by performing BUY, SELL, COOLDOWN
	// 1st attempt DFS recursion solution with runtime O(n^2)
	public int maxProfit_naive(int[] prices) {
        if(prices.length<=1) return 0;
        
        int[] maxProfit=new int[prices.length];
        int max= findMaxProfits(maxProfit, prices, 0);
        
        /* for(int p: maxProfit){
            System.out.println(p);
        }*/
        return max;
    }
    
    private static int findMaxProfits(int[] maxProfit, int[] prices, int buyAtIndex){
        if(buyAtIndex>=prices.length-1) {
            if(buyAtIndex==prices.length-1) maxProfit[buyAtIndex]=-1;
            return 0;
        }else if(maxProfit[buyAtIndex]>0) return maxProfit[buyAtIndex];
        else if(maxProfit[buyAtIndex]<0) return 0;
        
        int max=0;
        for (int i=buyAtIndex; i<prices.length; i++){
            for (int j=i+1; j<prices.length && prices[j]>prices[i]; j++){
                int maxAtJ2=findMaxProfits(maxProfit, prices, j+2);
                //System.out.println(maxAtJ2);
                max=Math.max(max, prices[j]-prices[i]+maxAtJ2);
            }
        }
        
        if(max==0) maxProfit[buyAtIndex]=-1;
        else maxProfit[buyAtIndex]=max;
        return max;
    }
	
	
}
