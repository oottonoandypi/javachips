package javachips;

import java.util.List;
import java.util.Stack;

public class MiniStringParserToNestedInteger {
	interface NestedInteger {
	   // Constructor initializes an empty nested list.
	       // public NestedInteger();
	  
	       // Constructor initializes a single integer.
	       // public NestedInteger(int value);
	  
	       // @return true if this NestedInteger holds a single integer, rather than a nested list.
	       public boolean isInteger();
	  
	       // @return the single integer that this NestedInteger holds, if it holds a single integer
	   // Return null if this NestedInteger holds a nested list
	       public Integer getInteger();
	  
	       // Set this NestedInteger to hold a single integer.
	       public void setInteger(int value);
	  
	       // Set this NestedInteger to hold a nested list and adds a nested integer to it.
	       public void add(NestedInteger ni);
	  
	       // @return the nested list that this NestedInteger holds, if it holds a nested list
	   // Return empty list if this NestedInteger holds a single integer
	       public List<NestedInteger> getList();
	}
	
	private int index=0; // helper integer for deserialize_recursive() method
    public NestedInteger deserialize_recursive(String s) {
        // recursive approach 
        // runtime O(n) memory O(n)
        return buildNestedInteger(null, s);
    }
    // helper function for deserialize_recursive() ^^
    private NestedInteger buildNestedInteger(NestedInteger curr, String s){
        if(index==s.length()) return curr;
        
        char c=s.charAt(index);
        
        int num=-1;
        boolean isNegative=false;
        while(index<s.length()){
            c=s.charAt(index);
            if(c=='-'){
                isNegative=true;
                index++;
            }else if(c>='0' && c<='9'){
                if(num==-1) num=c-48;
                else num=num*10+(c-48);
                index++;
            }else if(c==','){
                if(num>-1){
                    if(isNegative) {
                        num*=-1;
                        isNegative=false;
                    }
                    curr.add(new NestedInteger(num));
                    num=-1;
                }
                index++;
            }else if(c=='['){
                index++;
                if(curr==null) curr=buildNestedInteger(new NestedInteger(), s);
                else curr.add(buildNestedInteger(new NestedInteger(), s));
            }else if(c==']'){
                if(num>-1){
                    if(isNegative) {
                        num*=-1;
                        isNegative=false;
                    }
                    curr.add(new NestedInteger(num));
                    num=-1;
                }
                index++;
                return curr;
            }
        }
        
        if(num>-1){
            if(isNegative) num*=-1;
            if(curr==null) curr=new NestedInteger(num);
            else curr.add(new NestedInteger(num));
        }
        return curr;
    }
	
	public NestedInteger deserialize_iterative(String s) {
        // iterative approach with stack
        // runtime O(n) memory (n)
        Stack<NestedInteger> stack=new Stack<NestedInteger>();
        int num=0;
        boolean isNegative=false;
        int sLen=s.length();
        NestedInteger topOfStack=null;
        NestedInteger integer=null;
        char c;
        
        for(int i=0; i<sLen; i++){
            c=s.charAt(i);
            if(c=='['){
                NestedInteger newList=new NestedInteger();
                if(topOfStack!=null) topOfStack.add(newList);
                stack.add(newList);
                topOfStack=newList;
            }else if(c==']'){
                if(topOfStack.isInteger()){
                    integer=stack.pop();
                    topOfStack=stack.peek();
                    // System.out.println(integer.getInteger());
                    if(isNegative) {
                        integer.setInteger(integer.getInteger()*(-1));
                        isNegative=false;
                    }
                    topOfStack.add(integer);
                }
                topOfStack=stack.pop();
                if(stack.empty()) return topOfStack;
                topOfStack=stack.peek();
            }else if(c=='-'){
                isNegative=true;
                stack.add(new NestedInteger(0));
                topOfStack=stack.peek();
            }else if(c==','){
                if(topOfStack.isInteger()){
                    integer=stack.pop();
                    // System.out.println(integer.getInteger());
                    topOfStack=stack.peek();
                    if(isNegative){
                        integer.setInteger(integer.getInteger()*(-1));
                        isNegative=false;
                    }
                    topOfStack.add(integer);
                }
            }else if(c>='0' && c<='9'){
                if(topOfStack!=null && topOfStack.isInteger()){
                    topOfStack.setInteger(topOfStack.getInteger()*10+(c-48));
                }else {
                    stack.add(new NestedInteger(c-48));
                    topOfStack=stack.peek();
                }
            }else return null;
            
        }
        if(isNegative) topOfStack.setInteger(topOfStack.getInteger()*(-1));
        // System.out.println(topOfStack.getInteger());
        return topOfStack;
    }
}
