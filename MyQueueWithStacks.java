package javachips;

import java.util.Stack;

public class MyQueueWithStacks {
	private Stack<Integer> stackOrder;
    private Stack<Integer> queueOrder;
    public MyQueueWithStacks() {
        this.stackOrder=new Stack<Integer>();
        this.queueOrder=new Stack<Integer>();
    }
    
    public void push(int x) {
        if(queueOrder.empty()) queueOrder.push(x);
        else stackOrder.push(x);
    }
    
    public int pop() {
        int returnVal = queueOrder.pop();
        if(queueOrder.empty()){
            while(!stackOrder.empty()) queueOrder.push(stackOrder.pop());
        }
        return returnVal;
    }
    
    public int peek() {
        return queueOrder.peek();
    }
    
    public boolean empty() {
        return queueOrder.empty();
    }
}
