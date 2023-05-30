package javachips;

public class MyHashSet {
	// 0 <= key <= 10^6
	
	// All ops take O(1) time
	// Memory usage O(10^6)
	
	private boolean[] set;
    
    public MyHashSet() {
        this.set=new boolean[1000001];
    }
    
    public void add(int key) {
        this.set[key]=true;
    }
    
    public void remove(int key) {
         this.set[key]=false;
    }
    
    public boolean contains(int key) {
        return this.set[key];
    }
}

/*
class MyHashSet {
    private ArrayList<Integer> set;
    private int size;
    public MyHashSet() {
        this.set=new ArrayList<Integer>();
        this.size=0;
    }
    
    public void add(int key) {
        if(this.size==0 || key>this.set.get(this.size-1)) {
            this.set.add(key);
            this.size++;
        }else{
            int lookup=search(key);
            if(lookup<0 || this.set.get(lookup)<key) {
                this.set.add(lookup+1, key);
                this.size++;
            }
        }
    }
    
    private int search(int key){
        int l=0;
        int r=this.size-1;
        int m;
        int mKey;
        
        while(l<=r){
            m=l+(r-l)/2;
            mKey=this.set.get(m);
            if(mKey==key) return m;
            else if(mKey>key) r=m-1;
            else l=m+1;
        }
        
        return l-1;
    }
    
    public void remove(int key) {
        int lookup=search(key);
        if(lookup>=0 && this.set.get(lookup)==key) {
            this.set.remove(lookup);
            this.size--;
        }
    }
    
    public boolean contains(int key) {
        int lookup=search(key);
        return lookup>=0 && this.set.get(lookup)==key;
    }
}*/
