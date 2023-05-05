package javachips;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class StringOps {
	public String removeDuplicateLetters_stack(String s) {
		// Given a string s, remove duplicate letters so that every letter appears once and only once. 
		// must make sure the result is the smallest in lexicographical order among all possible results
		// runtime O(n)
        HashMap<Character, Integer> charCounts=new HashMap<Character, Integer>();
        HashSet<Character> inStack=new HashSet<Character>();
        Stack<Character> stack=new Stack<Character>();
        
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(!charCounts.containsKey(c)) charCounts.put(c, 0);
            charCounts.put(c, charCounts.get(c)+1);
        }
        
        
        for(int i=0; i<s.length(); i++){
            char c=s.charAt(i);
            if(inStack.contains(c)) {
                charCounts.put(c, charCounts.get(c)-1);
                continue;
            }
            
            while(!stack.empty() && stack.peek()>c && charCounts.get(stack.peek())>0){
                inStack.remove(stack.pop());
            }
            
            stack.push(c);
            inStack.add(c);
            charCounts.put(c, charCounts.get(c)-1);
        }
        
        StringBuilder smallestLexOrder= new StringBuilder();
        while(!stack.empty()) smallestLexOrder.append(stack.pop());
            
        return smallestLexOrder.reverse().toString();
    }
	
	public String removeDuplicateLetters(String s) {
		// Given a string s, remove duplicate letters so that every letter appears once and only once. 
		// must make sure the result is the smallest in lexicographical order among all possible results
		// runtime O(n)
        HashMap<Character, Integer> charCounts=new HashMap<Character, Integer>();
        HashSet<Character> inStr=new HashSet<Character>();
        
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(!charCounts.containsKey(c)) charCounts.put(c, 0);
            charCounts.put(c, charCounts.get(c)+1);
        }
        
        StringBuilder smallestLexOrder= new StringBuilder();
        for(int i=0; i<s.length(); i++){
            char c=s.charAt(i);
            if(inStr.contains(c)){
                charCounts.put(c, charCounts.get(c)-1);
                continue;
            }
            // System.out.println("Next char: "+c);
            while(smallestLexOrder.length()>0 && smallestLexOrder.charAt(smallestLexOrder.length()-1)>c && charCounts.get(smallestLexOrder.charAt(smallestLexOrder.length()-1))>0){
                // System.out.println("check end of smallestLexOrder: "+smallestLexOrder.charAt(smallestLexOrder.length()-1));
                //charCounts.put(smallestLexOrder.charAt(smallestLexOrder.length()-1), charCounts.get(smallestLexOrder.charAt(smallestLexOrder.length()-1))-1);
                // System.out.println(smallestLexOrder.charAt(smallestLexOrder.length()-1)+"'s remaining count: "+charCounts.get(smallestLexOrder.charAt(smallestLexOrder.length()-1)));
                inStr.remove(smallestLexOrder.charAt(smallestLexOrder.length()-1));
                smallestLexOrder.deleteCharAt(smallestLexOrder.length()-1);
            }
            
            smallestLexOrder.append(c);
            inStr.add(c);
            charCounts.put(c, charCounts.get(c)-1);
            // System.out.println(c+": "+charCounts.get(c));
            // System.out.println(smallestLexOrder.toString());
        }
        
        
        return smallestLexOrder.toString();
    }
	
	// ---------------------------------------------------------
	// Given a pattern and a string, find if the string follows the same pattern
	// Ex, pattern="abba" string="dog cat cat dog". this string follows the pattern
	public boolean wordPattern(String pattern, String s) {
        HashMap<Character, String> map=new HashMap<Character, String>();
        HashMap<String, Character> mapRev=new HashMap<String, Character>();
        int wordStart=0;
        int wordEnd=wordStart+1;
        for(int i=0; i<pattern.length(); i++){
            if(wordStart-1==s.length()) return false;
            char c = pattern.charAt(i);
            while(wordEnd<=s.length() && s.charAt(wordEnd-1)!=' ') wordEnd++;
            
            String word=s.substring(wordStart, wordEnd-1);
            
            if(map.containsKey(c)){
                if(!map.get(c).equals(word)) return false;
            }else if(mapRev.containsKey(word)){
                if(mapRev.get(word)!=c) return false;
            }else{
                map.put(c, word);
                mapRev.put(word, c);
            }
            
            wordStart=wordEnd;
            wordEnd=wordStart+1;
        }
        return wordStart-1==s.length();
    }
	
	// ---------------------------------------------------------
	// find all the possible computation results of putting parentheses around 
	public static List<Integer> diffWaysToCompute(String expression) {
        List<Integer> nums=new ArrayList<Integer>();
        List<Character> ops=new ArrayList<Character>();
        
        int num=0;
        for (int i=0; i<expression.length(); i++){
            char c = expression.charAt(i);
            if (c>='0' && c<='9'){
                num=num*10+c-48;
            }else if(c=='+' || c=='-' || c=='/' || c=='*'){
                nums.add(num);
                num=0;
                ops.add(c);
            }else {
                return new ArrayList<Integer>();
            }
        }
        nums.add(num);
        
        return findComputations(nums, ops, 0, 0, nums.size()-1, ops.size()-1);
    }
	// helper function for diffWaysToCompute
	private static List<Integer> findComputations(List<Integer> nums, List<Character> ops, int numsStart, int opsStart, int numsEnd, int opsEnd){
        List<Integer> res=new ArrayList<Integer>();
        
        if (opsStart>opsEnd) {
            res.add(nums.get(numsEnd));
        }else{
            for(int i=numsStart; i<numsEnd; i++){
                List<Integer> leftRes=findComputations(nums, ops, numsStart, opsStart, i, opsStart+(i-numsStart-1));
                char op = ops.get(opsStart+(i-numsStart));
                List<Integer> rightRes=findComputations(nums, ops, i+1, opsStart+(i-numsStart)+1, numsEnd, opsEnd);

                for(int l=0; l<leftRes.size(); l++){
                    for(int r=0; r<rightRes.size(); r++){
                        if(op=='+') res.add(leftRes.get(l)+rightRes.get(r));
                        else if(op=='-') res.add(leftRes.get(l)-rightRes.get(r));
                        else if(op=='*') res.add(leftRes.get(l)*rightRes.get(r));
                        else res.add(leftRes.get(l)/rightRes.get(r));
                    }
                }


            }
        }
        
        
        return res;
    }
	
	// ---------------------------------------------------------
	public static boolean isAnagramStrings_hashmap(String s, String t) { // O(s+t) using HashMap
		HashMap<Character, Integer> count = new HashMap<Character, Integer>();
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(count.get(c)==null) count.put(c, 1);
            else count.put(c, count.get(c)+1);
        }
        
        for(int i=0; i<t.length(); i++){
            char c = t.charAt(i);
            if(count.get(c)==null) return false;
            else if(count.get(c)==1) count.remove(c);
            else count.put(c, count.get(c)-1);
        }
        
        return count.isEmpty();
    }
	
	// ---------------------------------------------------------
	public static boolean isAnagramStrings_array(String s, String t) { // O(s+t+26) using array
        if(s.length()!=t.length()) return false;
        int[] count = new int[26];
        
        for(int i=0; i<s.length(); i++){
            count[s.charAt(i)-97]++;
        }
        
        for(int i=0; i<t.length(); i++){
            count[t.charAt(i)-97]--;
        }
        
        for(int c: count){
            if(c!=0) return false;
        }
        return true;
    }
	
	// ---------------------------------------------------------
	public static boolean isIsomorphicStrings(String s, String t) {// runtime (s.length) memory O(1) 256
		if(s.length() != t.length()) return false;
        char[] replacementMapS = new char[128];
        char[] replacementMapT = new char[128];
        
        for(int i=0; i<s.length(); i++){
            char charS = s.charAt(i);
            char charT = t.charAt(i);
            
            if(replacementMapS[charS]=='\u0000' && replacementMapT[charT]=='\u0000'){
                replacementMapS[charS] = charT;
                replacementMapT[charT] = charS;
            }else if(replacementMapS[charS]=='\u0000' || replacementMapT[charT]=='\u0000') return false;
            else if(replacementMapS[charS]!=charT || replacementMapT[charT]!=charS) return false;
        }
        return true;
	}
	
	
	public static void main(String[] args) {
		try {
			File testInput = new File("txt/StringOps/StringOps_isIsomorphicStrings_TestInput.txt");
			File testOutput = new File("txt/StringOps/StringOps_isIsomorphicStrings_TestOutput.txt");
			
			Scanner inScan = new Scanner(testInput);
			Scanner outScan = new Scanner(testOutput);
			boolean correct=true;
			
			while(inScan.hasNextLine()) {
				String s1=inScan.next();
				String s2=inScan.next();
				
				System.out.print(s1);
				System.out.println(" "+s2);
				
				boolean isIsomorphic=StringOps.isIsomorphicStrings(s1, s2);
				if(isIsomorphic) System.out.println("Isomorphic");
				else System.out.println("NOT Isomorphic");
				
				
				if(outScan.hasNextLine()) {
					String outputReference = outScan.next();
					if((isIsomorphic && outputReference=="false") 
							|| (!isIsomorphic && outputReference=="true")) {
						correct=false;
						System.out.println("INCORRECT\n");
					}else System.out.println("INCORRECT\n");
				}
				
				
			}
			inScan.close();
			outScan.close();
			
			if(correct) System.out.println("ALL Correct");
			else System.out.println("Some are INCORRECT");
			
		}catch(Exception e) {
			System.out.println("ERROR:: "+e);
		}
	}
}
