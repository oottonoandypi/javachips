package javachips;

public class AddictiveNumber {
	// Given a num string that only has digits (0-9)
	// Returns if it is an additive number sequence such as "112358" => 1+1=2; 1+2=3; 2+3=5; 3+5=8
	// Note: 
	// 1. leading zeros are INVALID EX. 1, 2, 03 or 1, 02, 3
	// 2. The 1st and 2nd numbers can be anything; starting at 3rd number, it should be addition of the previous 2 numbers.
	public boolean isAdditiveNumber(String num) {
		// runtime: O(n^2)
        if(num.length()<3) return false;
        int skipLeadingZeros=0;
        /* while(skipLeadingZeros<num.length() && num.charAt(skipLeadingZeros)=='0') skipLeadingZeros++;
        if(skipLeadingZeros==num.length()) return true;
        if(skipLeadingZeros>0) skipLeadingZeros--;
        if(skipLeadingZeros>0) skipLeadingZeros--;
        if(num.length()-skipLeadingZeros<3) return false; */
        
        return searchAdditiveSeq(num, 0, 0, skipLeadingZeros, 0);
    }
    
    private boolean searchAdditiveSeq(String num, long firstNum, long secondNum, int startIndex, int ithNum){
        if(startIndex==num.length()){
            if(ithNum>2) return true;
            else return false;
        }
        
        long currNum=0;
        if(ithNum==0) {
            if(num.charAt(startIndex)=='0') return searchAdditiveSeq(num, 0, 0, startIndex+1, ithNum+1);
            for(int i=startIndex; i<=startIndex+(num.length()-1-startIndex)/3+1 && i<num.length(); i++){
                currNum=currNum*10+(num.charAt(i)-48);
              //  System.out.println("first num: "+currNum);
                if(searchAdditiveSeq(num, currNum, secondNum, i+1, ithNum+1)) return true;
            }
        }else if(ithNum==1){
            if(num.charAt(startIndex)=='0') return searchAdditiveSeq(num, firstNum, 0, startIndex+1, ithNum+1);
            for(int i=startIndex; i<=startIndex+(num.length()-1-startIndex)/2+1 && i<num.length(); i++){
                currNum=currNum*10+(num.charAt(i)-48);
              //  System.out.println("second num: "+currNum);
                if(searchAdditiveSeq(num, firstNum, currNum, i+1, ithNum+1)) return true;
            }
        }else{
            long target=firstNum+secondNum;
            int index=startIndex;
          //  System.out.println("target num: "+target);
            if(num.charAt(index)=='0') return target==0;
            while(index<num.length()){
                currNum=currNum*10+(num.charAt(index)-48);
              //  System.out.println(currNum);
                if(currNum>=target) break;
                index++;
            }
          //  System.out.println((ithNum+1)+"st num: "+currNum);
            if(currNum==target) return searchAdditiveSeq(num, secondNum, currNum, index+1, ithNum+1);
            else return false;
        }
        
        return false;
    }
}
