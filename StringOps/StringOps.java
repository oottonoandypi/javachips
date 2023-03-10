package javachips;
import java.io.File;
import java.util.Scanner;

public class StringOps {
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
			File testInput = new File("src/practice/StringOps_isIsomorphicStrings_TestInput.txt");
			File testOutput = new File("src/practice/StringOps_isIsomorphicStrings_TestOutput.txt");
			
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
