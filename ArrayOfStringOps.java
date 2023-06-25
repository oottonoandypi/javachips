package javachips;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class ArrayOfStringOps {
	// ---------------------------------------------------------
	// Minimum Index Sum of Two Lists
	public static String[] findMinIndexSumOfTwoLists(String[] list1, String[] list2) {
		// time complexity O(n) memory O(n)
        int leastIndexSum=list1.length+list2.length;
        String[] lookupList=list1;
        String[] iterList=list2;
        
        if(list2.length<list1.length) {
            lookupList=list2;
            iterList=list1;
        }
        
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        for(int i=0; i<lookupList.length; i++){
            if(!map.containsKey(lookupList[i])) map.put(lookupList[i], i);
        }
        
        List<String> resList=new ArrayList<String>();
        String curr;
        
        for(int i=0; i<iterList.length; i++){
            curr=iterList[i];
            if(!map.containsKey(curr) || map.get(curr)+i>leastIndexSum) continue;
            
            if(map.get(curr)+i==leastIndexSum) resList.add(curr);
            else {
                resList.clear();
                resList.add(curr);
                leastIndexSum=map.get(curr)+i;
            }
        }
        
        String[] res=new String[resList.size()];
        for(int i=0; i<res.length; i++) res[i]=resList.get(i);
        return res;
    }
	
	// ---------------------------------------------------------
	// Find Substring with Concatenation of All Words
	// Inputs: a string s and an array of strings words. 
	// All the strings of words are of the same length.
	// Return: the starting indices of all the concatenated substrings in s.
	// 
	// Note: A concatenated substring in s is a substring that contains all the strings of any permutation of words concatenated.
	// EX. if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated strings
	
	// Approach #3: same idea as _binarysearch below, but use hashmap instead so searching is faster than lgn
	public static List<Integer> findConcatenatedSubstrings_hashmap(String s, String[] words) {
        int wordsLen=words.length;
        int wLen=words[0].length();
        int substrLen=wordsLen*wLen;
        HashMap<String, Integer> wordsAtIndex=new HashMap<String, Integer>();
        int[] wordsCounts=new int[wordsLen];
        int uniqueWords=-1;
        
        for(String word: words){
            if(!wordsAtIndex.containsKey(word)) {
                uniqueWords++;
                wordsAtIndex.put(word, uniqueWords);
                wordsCounts[uniqueWords]++;
            }else {
                wordsCounts[wordsAtIndex.get(word)]++;
            }
        }
        
        int[] counts;
        List<Integer> res=new ArrayList<Integer>();
        String substr;
        int j;
        
        for(int i=0; i+substrLen<=s.length(); i++){
            counts=new int[uniqueWords+1];
            j=i;
            while(j<=i+substrLen-wLen){
                substr=s.substring(j,j+wLen);
                if(!wordsAtIndex.containsKey(substr) || counts[wordsAtIndex.get(substr)]>=wordsCounts[wordsAtIndex.get(substr)]) break;
                counts[wordsAtIndex.get(substr)]++;
                j=j+wLen;
            }
            if(j==i+substrLen) res.add(i);
        }
        
        return res;
    }
	
	
	// Approach #2:
	public static List<Integer> findConcatenatedSubstrings_binarysearch(String s, String[] words) {
        Arrays.sort(words);
        
        int wordsLen=words.length;
        int wLen=words[0].length();
        int substrLen=wordsLen*wLen;
        
        int[] wordsCount=new int[wordsLen];
        int uniqueWords=0;
        for(int i=0; i<wordsLen; i++){
            if(words[i].compareTo(words[uniqueWords])==0) {
                wordsCount[uniqueWords]=wordsCount[uniqueWords]+1;
            }else {
                uniqueWords=uniqueWords+1;
                words[uniqueWords]=words[i];
                wordsCount[uniqueWords]=wordsCount[uniqueWords]+1;
            }
        }
        
        int[] pickedWords=new int[uniqueWords+1];
        
        Queue<Integer> picked=new LinkedList<Integer>();
        List<Integer> res=new ArrayList<Integer>();
        String substr;
        int foundIndex;
        int j;
        
        for(int i=0; i+substrLen<=s.length(); i++){
            j=i;
            while(j<=i+substrLen-wLen){
                substr=s.substring(j, j+wLen); 
                // System.out.println(substr);
                foundIndex=isFound(substr, words, 0, uniqueWords);
                if(foundIndex>-1 && pickedWords[foundIndex]<wordsCount[foundIndex]) {
                    pickedWords[foundIndex]=pickedWords[foundIndex]+1;
                    if(pickedWords[foundIndex]==1) picked.add(foundIndex);
                }else break;
                j=j+wLen;
            }
            if(j==i+substrLen) res.add(i);
            while(!picked.isEmpty()) pickedWords[picked.poll()]=0;
        }   
        
        return res;
    }
    
    private static int isFound(String str, String[] words, int l, int r){
        int m;
        String mStr;
        while(l<=r){
            m=l+(r-l)/2;
            mStr=words[m];
            if(str.compareTo(mStr)==0) return m;
            else if(str.compareTo(mStr)<0) r=m-1;
            else l=m+1;
        }
        return -1;
    }
	
	// Approach #1: findConcatenatedSubstrings_triesearch()
	// 
	
	public static List<Integer> findConcatenatedSubstrings_triesearch(String s, String[] words) {
        // trie approach
        int wLen=words[0].length();
        int wordsLen=words.length;
        int substrLen=wLen*wordsLen;
        
        MyTrie startOfWords=new MyTrie();
        MyTrie parentTrie;
        char character;
        
        for(String word: words){
            parentTrie=startOfWords;
            for(int c=0; c<wLen; c++){
                character=word.charAt(c);
                if(!parentTrie.nextTrie.containsKey(character)) parentTrie.nextTrie.put(character, new MyTrie());
                parentTrie=parentTrie.nextTrie.get(character);
            }
            parentTrie.count++;
            parentTrie.isEnd=true;
        }
        
        List<Integer> res=new ArrayList<Integer>();
        Queue<MyTrie> endOfVisited=new LinkedList<MyTrie>();
        int j;
        
        for(int i=0; i<=s.length()-substrLen; i++){
            parentTrie=startOfWords;
            j=i;
            // System.out.println(j);
            while(j<i+substrLen){
                character=s.charAt(j);
                if(parentTrie.isEnd) {
                    if(parentTrie.countVisits>=parentTrie.count) break;
                    parentTrie.countVisits++;
                    if(parentTrie.countVisits==1) endOfVisited.add(parentTrie);
                    parentTrie=startOfWords;
                }
                if(!parentTrie.nextTrie.containsKey(character)) break;
                parentTrie=parentTrie.nextTrie.get(character);
                j++;
            }
            if(j==i+substrLen && parentTrie.countVisits<parentTrie.count) res.add(i);
            while(!endOfVisited.isEmpty()) endOfVisited.poll().countVisits=0;
        }
        
        return res;
    }
	
	// ---------------------------------------------------------
	// Using American keyboard
	// Return the words from array words that can be typed using only 1 of the 3 rows on the keyboard
	public static String[] findWords_I(String[] words) {
		// runtime O(n); memory usage O(1)
		
        int[][] rows=new int[3][26];
        
        String row1Chars="qwertyuiop";
        for(int i=0; i<row1Chars.length(); i++){
            rows[0][row1Chars.charAt(i)-'a']=1;
        }
        String row2Chars="asdfghjkl";
        for(int i=0; i<row2Chars.length(); i++){
            rows[1][row2Chars.charAt(i)-'a']=1;
        }
        String row3Chars="zxcvbnm";
        for(int i=0; i<row3Chars.length(); i++){
            rows[2][row3Chars.charAt(i)-'a']=1;
        }
        
        int row;
        List<String> res= new ArrayList<String>();
    
        for(String word: words){
            for(row=0; row<3; row++){
                if((word.charAt(0)<='Z' && rows[row][word.charAt(0)+32-'a']==1) || (word.charAt(0)>'Z' && rows[row][word.charAt(0)-'a']==1)) break;
            }
            // System.out.println("Row: "+row);
            for(int i=0; i<word.length(); i++){
                if((word.charAt(i)<='Z' && rows[row][word.charAt(i)+32-'a']!=1) || (word.charAt(i)>'Z' && rows[row][word.charAt(i)-'a']!=1)) break;
                if (i==word.length()-1) res.add(word);
                
            }
        }
        
        String[] resToArray=new String[res.size()];
        for(int i=0; i<resToArray.length; i++){
            resToArray[i]=res.get(i);
        }
        
        return resToArray;
    }
	
	// Find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters
	// If no such two words exist, return 0;
	public static int maxProduct_bitCompare(String[] words) {
		// runtime O(n^2+n*k); additional memory n+26n
        int[] wordsInBits=new int[words.length];
        int res=0;
        
        for(int i=0; i<words.length; i++){
            String word=words[i];
            HashSet<Character> appeared=new HashSet<Character>();
            for(int j=0; j<word.length(); j++){
                char c=word.charAt(j);
                if(!appeared.contains(c)) {
                    wordsInBits[i]+=Math.pow(2, c-97);
                    appeared.add(c);
                }
            }
        }
        
        for(int i=0; i<wordsInBits.length; i++){
            for(int j=i+1; j<wordsInBits.length; j++){
                if((wordsInBits[i] & wordsInBits[j])==0) res=Math.max(res, words[i].length()*words[j].length());
            }
        }
        
        return res;
    }
	
	public static int maxProduct_naive(String[] words) {
		// runtime O(2n^2*k); additional memory 26*n*2
        int res=0;
        int[][] isLetterInWord=new int[26][words.length];
        
        // words.length*word.length()
        for(int w=0; w<words.length; w++){
            String word=words[w];
            
            for(int i=0; i<word.length(); i++){
                char c=word.charAt(i);
                isLetterInWord[c-97][w]=1;
            }
        }
        
        // words.length*(word.length()*words.length+words.length)
        for(int w=0; w<words.length; w++){
            String word=words[w];
            HashSet<Integer> avoidIndicies=new HashSet<Integer>();
            
            for(int c=0; c<word.length(); c++){
                for(int i=0; i<isLetterInWord[word.charAt(c)-97].length; i++){
                     if(isLetterInWord[word.charAt(c)-97][i]==1) avoidIndicies.add(i);
                }
            }
            
            for(int i=w+1; i<words.length; i++){
                if(!avoidIndicies.contains(i)) res=Math.max(res, word.length()*words[i].length());
            }
        }
        
        return res;
    } 
}

//helper class for findConcatenatedSubstrings_trie()
class MyTrie{
	 HashMap<Character, MyTrie> nextTrie;
	 int countVisits;
	 int count;
	 boolean isEnd;
	 public MyTrie(){
	     this.nextTrie=new HashMap<Character, MyTrie>();
	     this.countVisits=0;
	     this.count=0;
	     this.isEnd=false;
	 }
}
