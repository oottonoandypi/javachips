package javachips;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class NestedIterator implements Iterator<Integer> {
	// utility interface; actual implementation is skipped
	private interface NestedInteger {
		// @return true if this NestedInteger holds a single integer, rather than a nested list.
		public boolean isInteger();
		// @return the single integer that this NestedInteger holds, if it holds a single integer
		// Return null if this NestedInteger holds a nested listpublic Integer getInteger();
		public Integer getInteger();
		
		// @return the nested list that this NestedInteger holds, if it holds a nested list
		// Return empty list if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}
	
	// ------------------------------------------------------------------------------------------------------
	// Approach 3: Same as approach 2 below, but only using 1 stack to store NestedInteger
	// flatten list nestedinteger only when next in queue is a list
	// when calling next(), it's always a integer nestedinteger
	// runtime O(n); memory O(n)
	/*
	private Stack<NestedInteger> stack;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        this.stack=new Stack<NestedInteger>();
        for(int i=nestedList.size()-1; i>=0; i--){
            this.stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return this.stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while(!this.stack.empty() && !this.stack.peek().isInteger()){
            List<NestedInteger> list=this.stack.pop().getList();
            for(int i=list.size()-1; i>=0; i--){
                this.stack.push(list.get(i));
            }
        }
        return !this.stack.empty();
    }
    */
	
	// ------------------------------------------------------------------------------------------------------
	// Approach 2: Use 2 Stacks to store NestedInteger Lists and keep track of the index of each List that use additional O(n) memory
	// Initiation takes O(1) runtime
	// next() takes O(1) runtime
	// hasNext() add non-empty NesedInteger Lists to the stack, which takes O(n) runtime
	/*
	private Stack<List<NestedInteger>> stack;
    private Stack<Integer> stackIndex;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        this.stack=new Stack<List<NestedInteger>>();
        this.stackIndex=new Stack<Integer>();
        this.stack.push(nestedList);
        this.stackIndex.push(0);
    }

    @Override
    public Integer next() {
        Integer returnInt = stack.peek().get(stackIndex.peek()).getInteger();
        int nextIndex = stackIndex.pop()+1;
        if(nextIndex==stack.peek().size()) stack.pop();
        else stackIndex.push(nextIndex);
        
        return returnInt;
    }

    @Override
    public boolean hasNext() {
        while(!stack.empty() && !stack.peek().get(stackIndex.peek()).isInteger()) {
            List<NestedInteger> list=stack.peek().get(stackIndex.peek()).getList();
            int nextIndex=stackIndex.pop()+1;
            if(nextIndex==stack.peek().size()) stack.pop();
            else stackIndex.push(nextIndex);
            
            if(list.size()>0){
                stack.push(list);
                stackIndex.push(0); 
            }
        }
        return !stack.empty();
    }
	*/
	
	
	// ------------------------------------------------------------------------------------------------------
	// Approach 1: Use BFS recursion to flatten nestedInteger at the initiation that takes O(n) run time
	// and store Integers in an ArrayList that uses O(n) additional memory
	// and use an integer to keep track of the index of this ArrayList
	// Both next() and hasNext() take O(1) run time
    
	private List<Integer> integers;
    private int index;
    private int listSize;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        this.integers=new ArrayList<Integer>();
        flattenNested(nestedList, this.integers);
        this.index=0;
        this.listSize=this.integers.size();
    }
    
    private void flattenNested(List<NestedInteger> nestedList, List<Integer> integers){
        for(int i=0; i<nestedList.size(); i++){
            NestedInteger nextNested=nestedList.get(i);
            if(nextNested.isInteger()) integers.add(nextNested.getInteger());
            else flattenNested(nextNested.getList(), integers);
        }
    }

    @Override
    public Integer next() {
        return this.integers.get(this.index++);
    }

    @Override
    public boolean hasNext() {
        return this.index<this.listSize;
    }
    
}


