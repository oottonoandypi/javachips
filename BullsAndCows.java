package javachips;

import java.util.HashSet;

public class BullsAndCows {
	public String getHint_optimized(String secret, String guess) {
        // optimized on top of the below solution:
        // because secret and guess always have the same length, when digit position match, bulls++ (no need to store and find if match)
        // if digit not match, count secretDigit as potential cows; count guessDigit as digits that are not in the same position as secret
        // then get the final cows by collecting the min count of each digit in secret and guess
        int[] countOfDigitsInGuess=new int[10];
        int[] countOfCows=new int[10];
        
        int bulls=0;
        int cows=0;
        
        for(int i=0; i<secret.length(); i++){
            int dS = secret.charAt(i)-48;
            int dG = guess.charAt(i)-48;
            if(dS==dG) bulls++;
            else{
                countOfCows[dS]++;
                countOfDigitsInGuess[dG]++;
            }
        }
        
        for(int i=0; i<10; i++){
            cows+=Math.min(countOfDigitsInGuess[i], countOfCows[i]);
        }
        
        StringBuilder result=new StringBuilder();
        result.append(bulls);
        result.append("A");
        result.append(cows);
        result.append("B");
        return result.toString();
    }
	
	// --------------------------------------------------
	public String getHint(String secret, String guess) {
        // in guess, digit and positions
        // in a secret, for each digit that appeared, record its position and potential cows++
        // for each digit in guess, 1. find if that digit exists in secret, 2. find if that digit has the same position in secret; if both meet criteria, bulls++ and potential cows--; else count digit from guess
        // count final cows that are the min of countOfPotentialCows and countOfDigitsFromGuess
        
        HashSet<Integer>[] secretPos = new HashSet[10];
        int[] countOfDigitsInGuess=new int[10];
        int[] countOfCows=new int[10];
        
        StringBuilder result=new StringBuilder();
        int bulls=0;
        int cows=0;
        
        for(int i=0; i<secret.length(); i++){
            int d = secret.charAt(i)-48;
            
            if(secretPos[d]==null) secretPos[d]=new HashSet<Integer>();
            secretPos[d].add(i);
            
            countOfCows[d]++;
        }
        
        for(int i=0; i<guess.length(); i++){
            int gD=guess.charAt(i)-48;
            countOfDigitsInGuess[gD]++;
            if(secretPos[gD]!=null){
                if(secretPos[gD].contains(i)){
                    bulls++;
                    countOfCows[gD]--;
                    countOfDigitsInGuess[gD]--;
                }
            }
        }
        
        for(int i=0; i<10; i++){
            cows+=Math.min(countOfDigitsInGuess[i], countOfCows[i]);
        }
        
        result.append(bulls);
        result.append("A");
        result.append(cows);
        result.append("B");
        return result.toString();
    }
}
