package javachips;

import java.util.Stack;

public class BasicCalculator {
	public static double myPow(double x, int n){
		// time complexity O(lgn) space complexity O(lgn)
        if(n<0) x=1/x;
        double res=1;
        if(n==Integer.MIN_VALUE) {
            res*=x;
            n++;
        }
        n=Math.abs(n);
        
        return res*powOf(x, n);
    }
    // helper function for myPow() ^^
    private static double powOf(double x, int n){
        if(n==0) return 1;
        if(x==0) return 0;
        if(n==1) return x;
        if(x==1) return 1;
        else if(x==-1){
            if(n%2==0) return 1;
            else return -1;
        }
        
        double half=powOf(x, n/2);
        if(n%2==0) {
            return half*half;
        }else{
            return x*half*half;
        }
    }
	
	// use 1 stack for tracking numbers
    public static int calculate_1stack(String s) {
        Stack<Integer> nums = new Stack<Integer>();
        char op = '+';
        
        int n = 0;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c>='0' && c<='9') n=n*10+c-48;
            if((!(c>='0' && c<='9') && c!=' ') || i==s.length()-1){
                if(op=='+'){
                    nums.push(n);
                }else if(op=='-'){
                    nums.push(n*(-1));
                }else if(op=='*'){
                    nums.push(nums.pop()*n);
                }else if(op=='/'){
                    nums.push(nums.pop()/n);
                }
                
                op=c;
                n=0;
            }
        }
        
        int res=0;
        while(!nums.empty()) res+=nums.pop();
        return res;
    }
	
	
	// use 2 stacks, 1 for tracking numbers, 1 for tracking operations
    /* public int calculate_2stacks_deprecated(String s) {
        Stack<Character> op = new Stack<Character>();
        Stack<Integer> num = new Stack<Integer>();
        
        int n = 0;
        
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c>='0' && c<='9') {
                n=n*10+c-48;
            }else {
                if(c==' ') continue;
                if(!op.empty() && !num.empty()){
                    char topOp = op.pop();
                    if(topOp=='*'){
                        num.push(num.pop()*n);
                        op.push(c);
                    }else if(topOp=='/'){
                        num.push(num.pop()/n);
                        op.push(c);
                    }else{
                        op.push(topOp);
                        op.push(c);
                        num.push(n);
                    }
                    n=0;
                }else{
                    num.push(n);
                    op.push(c);
                    n=0;
                }
            }
        }
        num.push(n);
        
        int topNum=num.pop();
        while(!num.empty() && !op.empty()){
            char topOp=op.pop();
            int topNum2=num.pop();
            if(topOp=='*'){
                num.push(topNum2*topNum);
            }else if(topOp=='/'){
                num.push(topNum2/topNum);
            }else if(!op.empty() && op.peek()=='-'){
                if(topOp=='+') num.push(topNum2-topNum);
                else num.push(topNum2+topNum);
            }else if(topOp=='+'){
                num.push(topNum2+topNum);
            }else if(topOp=='-'){
                num.push(topNum2-topNum);
            }
            topNum=num.pop();
        }
        return topNum;
    } */
}
