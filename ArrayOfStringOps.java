package javachips;

import java.util.HashSet;

public class ArrayOfStringOps {
	// Find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters
	// If no such two words exist, return 0;
	public int maxProduct_bitCompare(String[] words) {
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
	
	public int maxProduct_naive(String[] words) {
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
