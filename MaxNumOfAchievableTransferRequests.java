package javachips;

public class MaxNumOfAchievableTransferRequests {
	private int[] transfers;
    private int maxSuccessfulTransfers=0;
    private int[][] requests;
    
    // Input: an array requests where requests[i] = [from i, to i] represents an employee's request to transfer from building from i to building to i
    // All buildings are full, so a list of requests is achievable only if for each building, the net change in employee transfers is zero. This means the number of employees leaving is equal to the number of employees moving in. 
    // Returns: Maximum number of successful transfers
    
    // Solution #1: Every request can be included or not
    // time complexity O(2^n) where n is number of requests; space complexity O(k+n) where k is number of buildings
    public int maximumRequests(int n, int[][] requests) {
        transfers=new int[n];
        this.requests=requests;
        // index, countZeros, countSuccessfulTransfers
        return findSuccessfulTransfers(0, n, 0);
        
    }
    
    private int findSuccessfulTransfers(int startIndex, int countZeros, int countSuccessfulTransfers){
        if(countSuccessfulTransfers+requests.length-startIndex<maxSuccessfulTransfers) return maxSuccessfulTransfers;
        else if(startIndex==requests.length){
            if(countZeros==transfers.length) return countSuccessfulTransfers;
            else return maxSuccessfulTransfers;
        }
        
        int countBuildingFrom=transfers[requests[startIndex][0]];
        int countBuildingTo=transfers[requests[startIndex][1]];
        
        if(requests[startIndex][0]!=requests[startIndex][1]){
            int newCountZeros=countZeros;
            if(countBuildingFrom==0) newCountZeros--;
            else if(countBuildingFrom-1==0) newCountZeros++;

            if(countBuildingTo==0) newCountZeros--;
            else if(countBuildingTo+1==0) newCountZeros++;

            transfers[requests[startIndex][0]]--;
            transfers[requests[startIndex][1]]++;
            maxSuccessfulTransfers=Math.max(maxSuccessfulTransfers, findSuccessfulTransfers(startIndex+1, newCountZeros, countSuccessfulTransfers+1));

            transfers[requests[startIndex][0]]++;
            transfers[requests[startIndex][1]]--;
            
            maxSuccessfulTransfers=Math.max(maxSuccessfulTransfers, findSuccessfulTransfers(startIndex+1, countZeros, countSuccessfulTransfers));
        }else{
            maxSuccessfulTransfers=Math.max(maxSuccessfulTransfers, findSuccessfulTransfers(startIndex+1, countZeros, countSuccessfulTransfers+1));
        }

        return maxSuccessfulTransfers;
    }
}
