package javachips;

import java.util.Queue;
import java.util.LinkedList;

public class MyStackWithQueues {
	private Queue<Integer> lastTopQ;
    private Queue<Integer> inOrderQ;
    public MyStackWithQueues() {
        lastTopQ=new LinkedList<Integer>();
        inOrderQ=new LinkedList<Integer>();
    }
    
    public void push(int x) {
        Integer topInt = lastTopQ.poll();
        if(topInt!=null) inOrderQ.add(topInt);
        lastTopQ.add(x);
    }
    
    public int pop() {
        Integer topInt = lastTopQ.poll();
        if(topInt!=null){
            Integer nextInorder = inOrderQ.poll();
            while(inOrderQ.peek()!=null){
                lastTopQ.add(nextInorder);
                nextInorder=inOrderQ.poll();
            }
            inOrderQ.add(nextInorder);
            Queue temp = lastTopQ;
            lastTopQ=inOrderQ;
            inOrderQ=temp;
            
            return topInt;
        }else return topInt;
    }
    
    public int top() {
        return lastTopQ.peek();
    }
    
    public boolean empty() {
        return lastTopQ.peek()==null;
    }
}
