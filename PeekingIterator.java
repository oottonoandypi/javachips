package javachips;

import java.util.Iterator;
import java.util.ArrayList;

// PeekingIterator class implements by using the Iterator interface
// and add a peek() function on top of the next() and hasNext() functions
class PeekingIterator implements Iterator<Integer> {
    private int index;
    private ArrayList<Integer> integers;
    
	public PeekingIterator(Iterator<Integer> iterator) {
	    this.index=0;
        this.integers=new ArrayList<Integer>();
        while(iterator.hasNext()){
            this.integers.add(iterator.next());
        }
	}

	
	public Integer peek() {
        return this.integers.get(this.index);
	}

	@Override
	public Integer next() {
        this.index++;
        return this.integers.get(this.index-1);
	}
	
	@Override
	public boolean hasNext() {
        return this.index<this.integers.size();
	}
}
