package javachips;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class ArrayOfStringOps {
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
