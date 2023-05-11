package javachips;

public class BinaryTreeInStringOps {
	// Given a preorder binary tree in form of a string: each value is separated by a comma and null node is represented by '#'
	// Return whether it is a valid binary tree
	public static boolean isValidSerialization(String preorder) {
        int count=1;
        int newCount=0;
        int nextNum=-1;
        for(int i=0; i<preorder.length(); i++){
            if(count==0) return false;
            char nextChar=preorder.charAt(i);
            if(nextChar>='0' && nextChar<='9'){
                if(nextNum==-1) nextNum=nextChar-48;
                else nextNum=nextNum*10+(nextChar-48);
                if(i==preorder.length()-1){
                    newCount++;
                    count--;
                    nextNum=-1;
                }
            }else if(nextChar=='#'){
                if(i-1>=0 && preorder.charAt(i-1)!=',') return false;
            }else if(nextChar==','){
                if(i-1<0 || (preorder.charAt(i-1)!='#' && !(preorder.charAt(i-1)>='0' && preorder.charAt(i-1)<='9'))) return false;
                if(nextNum>-1) {
                    newCount++;
                    nextNum=-1;
                }
                count--;
            }else return false;
            
            if(count==0) {
                count=newCount*2;
                newCount=0;
            }
        }
        
        if(nextNum>-1 || preorder.charAt(preorder.length()-1)=='#') count--;
        return count==0;
    }
}
