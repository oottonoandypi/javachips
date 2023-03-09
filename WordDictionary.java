package practice;

import java.util.HashMap;

public class WordDictionary {// addWord using hashmap O(1) searchWord O(1)-O(26^m)

    class DictionaryNode{
        private char c;
        private HashMap<Character, DictionaryNode> nextDictionary;
        private boolean isEndOfWord;
        
        public DictionaryNode(char c){
            this.c=c;
            this.nextDictionary = new HashMap<Character, DictionaryNode>();
            this.isEndOfWord=false;
        }
        
        public HashMap<Character, DictionaryNode> getNextDictionary(){
            return nextDictionary;
        }
        
        public void setEndOfWord(){
            isEndOfWord=true;
        }
        
        public boolean isEndOfWord(){
            return isEndOfWord;
        }
    }
    
    private HashMap<Character, DictionaryNode> startWord;
    
    public WordDictionary() {
        startWord=new HashMap<Character, DictionaryNode>();
    }
    
    public void addWord(String word) {
        HashMap<Character, DictionaryNode> dic = startWord;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!dic.containsKey(c)) dic.put(c, new DictionaryNode(c));
            if(i==word.length()-1) dic.get(c).setEndOfWord();
            else dic=dic.get(c).getNextDictionary();
        }
    }
    
    public boolean search(String word) {
        return preOrderSearch(word, 0, startWord);
    }
    
    private boolean preOrderSearch(String word, int charIndex, HashMap<Character, DictionaryNode> dict){
        char currChar = word.charAt(charIndex);
        
        if(charIndex==word.length()-1) {
            if(currChar!='.') {
                if(dict.containsKey(currChar) && dict.get(currChar).isEndOfWord()) return true;
                else return false;
            }else{
                for(DictionaryNode node: dict.values()) {
                    if(node.isEndOfWord()) return true;
                }
                return false;
            }
        }else{
            if(currChar!='.') {
                if(dict.containsKey(currChar)) return preOrderSearch(word, charIndex+1, dict.get(currChar).getNextDictionary());
                else return false;
            }else{
                for(DictionaryNode node: dict.values()){
                    if(preOrderSearch(word, charIndex+1, node.getNextDictionary())) return true;
                }
                return false;
            }
        }
    }
    
    public static void main(String[] args) {
    	// test to be updated soon.. 
    }

}
