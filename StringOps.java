package javachips;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class StringOps {
	// ---------------------------------------------------------
	// Given a 0-indexed string s that consists of digits from 0 to 9
	// Return the length of the longest semi-repetitive substring inside s
	
	// Note: A substring is semi-repetitive if there is at most one consecutive pair of the same digits inside t. 
	// For example, 0010, 002020, 0123, 2002, and 54944 are semi-repetitive while 00101022, and 1101234883 are not.
	public static int longestSemiRepetitiveSubstring(String s) {
		// runtime O(n*k) memory O(1)
        int startIndex=0;
        int longest=0;
        int repetitiveAt=-1;
        
        for(int i=1; i<s.length(); i++){
            // System.out.println(i);
            if(s.charAt(i)==s.charAt(i-1)) {
                if(repetitiveAt==-1) repetitiveAt=i;
                else{
                    longest=Math.max(longest, i-startIndex);
                    startIndex=repetitiveAt;
                    i=startIndex;
                    repetitiveAt=-1;
                }
            }
        }
        
        longest=Math.max(longest, s.length()-startIndex);
        return longest;
	}
	
	// ---------------------------------------------------------
	// Given a string s, reverse characters in each word separated by a space (' ') while keeping the order of words
	// EX. "Let's take LeetCode contest" => "s'teL ekat edoCteeL tsetnoc"
	public static String reverseWords(String s) {
		// runtime O(n) memory O(n)
        char[] build=s.toCharArray();
        int l=0;
        int r;
        char c;
        
        for(int i=0; i<build.length; i++){
            if(i==build.length-1 || build[i+1]==' ') {
                r=i;
                
                while(l<r){
                    c=build[l];
                    build[l++]=build[r];
                    build[r--]=c;
                }
                
                l=i+2;
            }
        }
        
        return new String(build);
    }
	
	// ---------------------------------------------------------
	// Given a string that represents a student's attendance record
	// 'A' => Absent; 'L' => Late; 'P' => Present
	// return true if a student was absent ('A') for strictly fewer than 2 days total and was never late ('L') for 3 or more consecutive days.
	public static boolean checkAttendance(String s) {
		// runtime O(n) memory O(1)
        int countAbsent=0;
        int countLate=0;
        char c;
        
        for(int i=0; i<s.length(); i++){
            c=s.charAt(i);
            if(c=='L'){
                if(countLate==2) return false;
                countLate++;
            }else{
                countLate=0;
                if(c=='A') {
                    if(countAbsent==1) return false;
                    countAbsent++;
                }
            }
        }
        
        return true;
    }
	
	// ---------------------------------------------------------
	// Reverse k characters for every 2k characters counting from start of the given string
	public static String reverse_k_chars(String s, int k) {
		// runtime O(n) memory O(n)
        char[] chars=s.toCharArray();
        int l;
        int r;
        char t;
        int len=s.length();
        
        for(int i=0; i<len; i+=k+k){
            l=i;
            r=Math.min(i+k, len)-1;
            while(l<r){
                t=chars[l];
                chars[l]=chars[r];
                chars[r]= t;
                l++;
                r--;
            }
        }
        return new String(chars);
    }
	
	// ---------------------------------------------------------
	// Given 2 strings, return the length of longest uncommon subsequence of them
	// An uncommon subsequence between two strings is a string that is a subsequence of one but not the other.
	public static int findLongestUncommonSubsequence(String a, String b) {
		// runtime O(n)
        if(a.length()<b.length()) return b.length();
        else if(a.length()>b.length()) return a.length();
        
        for(int i=0; i<a.length(); i++){
            if(a.charAt(i)!=b.charAt(i)) return a.length();
        }
        
        return -1;
    }
	
	// ---------------------------------------------------------
	// Given a String word
	// Check if it's using Capitals correctly like the following:
	// 1. All letters in this word are capitals, like "USA".
	// 2. All letters in this word are not capitals, like "leetcode".
	// 3. Only the first letter in this word is capital, like "Google".
	
	public static boolean checkCapitalUse(String word) {
		// runtime O(n) memory usage O(1)
		if(word.length()==1) return true;
        boolean isCaps;
        if(word.charAt(0)>'Z') isCaps=false;
        else{
            if(word.charAt(1)>'Z') isCaps=false;
            else isCaps=true;
        }
        
        for(int i=1; i<word.length(); i++){
            char c=word.charAt(i);
            if((isCaps && c>'Z') || (!isCaps && c<='Z')) return false;
        }
        
        return true;
    }
	
	// ---------------------------------------------------------
	// Reformatting dash separated alphabetic letters to a string constructed by k-length segmented substrings
	// Only the first segment can have a length<k
	// EX. s = "5F3Z-2e-9-w", k = 4 => "5F3Z-2E9W"
	// EX. s = "2-5g-3-J", k = 2 => "2-5G-3J"
	public static String reformatting_I(String s, int k) {
        StringBuilder build=new StringBuilder();
        int countK=0;
        
        for(int i=s.length()-1; i>=0; i--){
            char c=s.charAt(i);
            if(c=='-') continue;
            if(c>'Z') c=(char)(c-'a'+'A');
            build.append(c);
            countK++;
            if(countK==k) {
                countK=0;
                build.append('-');
            }
        }
        
        if(build.length()>0 && build.charAt(build.length()-1)=='-') return build.reverse().substring(1);
        return build.reverse().toString();
    }
	
	// ---------------------------------------------------------
	// Given a string s, check if it can be constructed by taking a substring of it and appending multiple copies of the substring together.
	public static boolean isRepeatedSubstrings(String s) {
        // runtime O(n^2); memory usage O(1)
        int parts=2;
        int partLen;
        int index;
        
        while(parts<=s.length()){
            if(s.length()%parts==0){
                partLen=s.length()/parts;
                index=partLen;
                while(index<s.length() && s.charAt(index)==s.charAt(index%partLen)) index++;
                if(index==s.length()) return true;
            }
            
            parts++;
        }
        
        return false;
    }
	
	// ---------------------------------------------------------
	// Given a string s, return the number of segments in the string.
	// A segment is defined to be a contiguous sequence of non-space characters.
	public static int countSegments(String s) {
		// runtime O(n); memory usage O(1)
        int count=0;
        int startIndex=0;
        
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)==' '){
                if(i>startIndex) count++;
                startIndex=i+1;
            }
        }
        
        if(startIndex<s.length()) count++;
        return count;
    }
	
	// ---------------------------------------------------------
	// Given two non-negative integers, num1 and num2 represented as string, return the sum of num1 and num2 as a string.
	// You must solve the problem without using any built-in library for handling large integers (such as BigInteger). You must also not convert the inputs to integers directly.
	public static String sumOfTwoStr(String num1, String num2) {
		// runtime O(n); memory usage O(1)
		StringBuilder resBuilder=new StringBuilder();
        int carry=0;
        int index1=num1.length()-1;
        int index2=num2.length()-1;
        int charToInt1;
        int charToInt2;
        int sum;
        int toAppend;
        
        while(index1>=0 && index2>=0){
            charToInt1=num1.charAt(index1--)-'0';
            charToInt2=num2.charAt(index2--)-'0';
            sum=charToInt1+charToInt2+carry;
            toAppend=sum%10;
            carry=sum/10;
            resBuilder.append((char)(toAppend+'0'));
        }
        
        while(index1>=0){
            charToInt1=num1.charAt(index1--)-'0';
            sum=charToInt1+carry;
            toAppend=sum%10;
            carry=sum/10;
            resBuilder.append((char)(toAppend+'0'));
        }
        
        while(index2>=0){
            charToInt2=num2.charAt(index2--)-'0';
            sum=charToInt2+carry;
            toAppend=sum%10;
            carry=sum/10;
            resBuilder.append((char)(toAppend+'0'));
        }
        
        if(carry>0) resBuilder.append((char)(carry+'0'));
        return resBuilder.reverse().toString();
    }
	
	// ---------------------------------------------------------
	// Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.
	// Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
	public static int longestPalindrome_countOdds(String s) {
		// runtime O(n); additional memory O(1)
		int[] letters=new int[58];
        int odds=0;
        
        for(int i=0; i<s.length(); i++) {
            int charToInt=(int)(s.charAt(i)-65);
            if(letters[charToInt]==0){
                letters[charToInt]=1;
                odds++;
            }else{
                letters[charToInt]=0;
                odds--;
            }
        }
        
        if(odds>0) return s.length()-odds+1;
        else return s.length();
    }
	
	public static int longestPalindrome_countEvens(String s) {
		// runtime O(n); additional memory O(1)
        int[] letters=new int[58];
        int countSingles=0;
        int longestLen=0;
        
        for(int i=0; i<s.length(); i++){
            int charToInt=(int)(s.charAt(i)-65);
            if(letters[charToInt]==0){
                letters[charToInt]=1;
                countSingles++;
            }else{
                letters[charToInt]=0;
                longestLen+=2;
                countSingles--;
            }
        }
        
        if(countSingles==0) return longestLen;
        else return longestLen+1;
        
    }
	
	// ---------------------------------------------------------
	// Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
	// A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).			 
	public static boolean isSubsequence(String s, String t) {
		// runtime O(n); additional memory usage O(1)
        if(s.length()==0) return true;
        if(s.length()>t.length()) return false;
        
        int indexS=0;
        for(int indexT=0; indexT<t.length(); indexT++){
            if(s.charAt(indexS)==t.charAt(indexT) && ++indexS==s.length()) return true;
        }
        
        return false;
    }
	
	// ---------------------------------------------------------
	// You are given two strings s and t.
	// String t is generated by random shuffling string s and then add one more letter at a random position.
	// Return the letter that was added to t.
	public static char findOneLetterDifference(String s, String t) {
		// runtime O(n); additional memory usage O(1)
        int[] letters=new int[26];
        for(int i=0; i<s.length(); i++){
            letters[s.charAt(i)-97]++;
        }
        
        for(int i=0; i<t.length(); i++){
            if(--letters[t.charAt(i)-97]<0) return t.charAt(i);
        }
        
        return ' ';
    }
	
	// ---------------------------------------------------------
	// Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.
	public static int firstUniqChar(String s) {
		// runtime O(n); additional memory usage O(1)
        int[] letters=new int[26];
        for(int i=0; i<s.length(); i++){
            letters[s.charAt(i)-97]++;
        }
        
        for(int i=0; i<s.length(); i++){
            if(letters[s.charAt(i)-97]==1) return i;
        }
        
        return -1;
    }
	
	// ---------------------------------------------------------
	// Given two strings ransomNote and magazine, return true if ransomNote can be constructed by using the letters from magazine and false otherwise.
	// Each letter in magazine can only be used once in ransomNote.
	public static boolean canConstruct(String ransomNote, String magazine) {
		// runtime O(n); additional memory usage O(1)
        if(ransomNote.length()>magazine.length()) return false;
        int[] letters=new int[26];
        for(int i=0; i<magazine.length(); i++){
            letters[magazine.charAt(i)-97]++;
        }
        for(int i=0; i<ransomNote.length(); i++){
            if(--letters[ransomNote.charAt(i)-97]<0) return false;
        }
        return true;
    }
	
	// ---------------------------------------------------------
	// Given a string s, reverse only all the vowels in the string and return it.
	public static String reverseVowels(String s) {
		// runtime O(n)
        StringBuilder sb=new StringBuilder(s);
        int l=0;
        int r=s.length()-1;
        
        while(l<r){
            boolean isLVowel=isVowel(s.charAt(l));
            boolean isRVowel=isVowel(s.charAt(r));
            if(isRVowel && isLVowel){
                sb.setCharAt(l, s.charAt(r));
                sb.setCharAt(r, s.charAt(l));
                l++;
                r--;
            }else if(isLVowel) r--;
            else if(isRVowel) l++;
            else{
                l++;
                r--;
            }
        }
        
        return sb.toString();
    }
    // Helper function for reverseVowels() ^^
    private static boolean isVowel(char c){
        return c=='a' || c=='A' || c=='e' || c=='E' || c=='i' || c=='I' || c=='o' || c=='O' || c=='u' || c=='U';
    }
	
	
	// ---------------------------------------------------------
	public static String removeDuplicateLetters_stack(String s) {
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
	
	public static String removeDuplicateLetters(String s) {
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
	public static boolean wordPattern(String pattern, String s) {
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
