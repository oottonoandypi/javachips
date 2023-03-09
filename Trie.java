package practice;

import java.util.HashMap;

public class Trie {
// store char in nodes; 26 character hashmap, each character stores a map/an arraylist that stores all the indices that this character has been visited
    
    private HashMap<Character, Node> dict;
    
    class Node{
        private char c;
        private boolean isEndOfWord;
        private HashMap<Character, Node> nextDict;
        public Node(char c, boolean isEndOfWord){
            this.c=c;
            this.isEndOfWord=isEndOfWord;
            this.nextDict=new HashMap<Character, Node>();
        }
        
        public void setEnd(){
            this.isEndOfWord=true;
        }
        
        public HashMap<Character, Node> getNextDict(){
            return this.nextDict;
        }
        
        public boolean isEnd(){
            return this.isEndOfWord;
        }
    }
    
    public Trie() {
        this.dict = new HashMap<>();
    }
    
    public void insert(String word) {
        HashMap<Character, Node> iter = dict;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!iter.containsKey(c)) {
                if(i==word.length()-1) iter.put(c, new Node(c, true));
                else {
                    iter.put(c, new Node(c, false));
                    iter=iter.get(c).getNextDict();
                }
            }else if(i==word.length()-1) iter.get(c).setEnd();
            else iter=iter.get(c).getNextDict();
        }
    }
    
    public boolean search(String word) {
        HashMap<Character, Node> iter = dict;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!iter.containsKey(c)) return false;
            else if(i==word.length()-1) return iter.get(c).isEnd();
            iter= iter.get(c).getNextDict();
        }
        
        return false;
    }
    
    public boolean startsWith(String prefix) {
        HashMap<Character, Node> iter=dict;
        for(int i=0; i<prefix.length(); i++){
            char c = prefix.charAt(i);
            if(!iter.containsKey(c)) return false;
            iter=iter.get(c).getNextDict();
        }
        return true;
    }
}
