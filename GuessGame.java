package javachips;

public class GuessGame {
	private int target;
	
	public GuessGame(int target) {
		this.target=target;
	}
	
	public int guessNumber_solution2(int n) {
		// lgn
        int low=1;
        int high=n;
        
        while(low<=high){
            int mid=low+(high-low)/2;
            if(guess(mid)==0) return mid;
            else if(guess(mid)==-1) high=mid-1;
            else low=mid+1;
        }
        
        return -1;
    }
	
	public int guessNumber_solution1(int n) {
		// lgn
		int low=1;
        int high=n;
        
        while(low<=high){
            if(guess(high)==0) return high;
            else if(guess(high)==-1) high=low+(high-low)/2;
            else {
                low=high+1;
                high=high+(n-high)/2;
            }
            // System.out.println("low: "+low);
            // System.out.println("high: "+high);
        }
        return low;
    }
	
	private int guess(int guess) {
		if (guess==target) return 0;
		else if(guess<target) return 1;
		else return -1;
	}
}
