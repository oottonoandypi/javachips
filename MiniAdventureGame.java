package javachips;

import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class MiniAdventureGame {
	private String[] grid;
	public MiniAdventureGame(String[] grid) {
		this.grid=grid;
	}
	// Function: shortestPathAllKeys()
	// Input: A grid presented as an array of strings where:
				// '.' is an empty cell;
				// '#' is a wall.
				// '@' is the starting point.
				// Lowercase letters (range a-f) represent keys.
				// Uppercase letters (range A-F) represent locks.
	// Return: lowest number of moves to get all keys
	
	// Move rules: one move consists of walking one space in one of the four cardinal directions. 
				// You cannot walk outside the grid, or walk into a wall.
	
	// Solution #3: bfs approach to find shortest paths
	// with tracking keys obtained at position using bits instead of string in an array
	// time complexity O(grid height*grid width*(2^keys)) space complexity O(grid height*grid width*(2^keys))
	public int shortestPathAllKeys_BFSBitsArray() {
        int[][] moves=new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        boolean[] keys=new boolean[6];
        boolean[] locks=new boolean[6];
        int totalKeys=0;
        
        int width=grid[0].length();
        int height=grid.length;
        int[][][] shortestPathsFromStart=new int[height][width][63];
        
        Queue<int[]> next=new LinkedList<>();
        char c;
        
        int[] startPos=new int[]{0,0};
        
        // Initiate game 
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                c=grid[i].charAt(j);
                if(c=='@') {
                    next.add(new int[]{i,j,0,0});
                    startPos[0]=i;
                    startPos[1]=j;
                }else if(c!='#'){
                    if(c>='a' && c<='f') {
                        keys[c-97]=true;
                        totalKeys+=1<<(c-97);
                        //System.out.println(c-97);
                        //System.out.println(totalKeys);
                    }else if(c>='A' && c<='F') locks[c-65]=true;
                }
            }
        }
        
        int count;
        int nextR;
        int nextC;
        int[] checkoutCell;
        int newKeyStat;
        
        while(!next.isEmpty()){
            count=next.size();
            while(count>0){
                checkoutCell=next.poll();
                
                for(int[] move: moves){
                    nextR=checkoutCell[0]+move[0];
                    nextC=checkoutCell[1]+move[1];
                    
                    if(nextR>=0 && nextR<height && nextC>=0 && nextC<width && grid[nextR].charAt(nextC)!='#'){
                        c=grid[nextR].charAt(nextC);
                        
                        if(c-97>=0 && c-97< 6 && keys[c-97]){
                            if(((1<<(c-97))&checkoutCell[2])==0){
                                newKeyStat=(checkoutCell[2] | (1<<(c-97)));
                                // System.out.println(c+" "+Integer.toBinaryString(newKeyStat)+" "+(checkoutCell[3]+1));
                                if(newKeyStat==totalKeys) return checkoutCell[3]+1;
                                shortestPathsFromStart[nextR][nextC][newKeyStat]=checkoutCell[3]+1;
                                next.add(new int[]{nextR, nextC, newKeyStat, checkoutCell[3]+1});
                            }
                        }else if(c-65>=0 && c-65<6 && locks[c-65] && (checkoutCell[2]&(1<<(c-65)))==0) continue;
                        
                        if((nextR==startPos[0] && nextC==startPos[1] && checkoutCell[2]!=0) || shortestPathsFromStart[nextR][nextC][checkoutCell[2]]==0){
                            shortestPathsFromStart[nextR][nextC][checkoutCell[2]]= checkoutCell[3]+1;
                            next.add(new int[]{nextR, nextC, checkoutCell[2], checkoutCell[3]+1});
                        }
                    }
                }
                
                count--;
            }
        }
        
        return -1;
        
    }
	
	// Solution #2: bfs approach to find shortest paths
    // with tracking keys obtained at position using bits instead of string in a list of hashmaps
	// time complexity O(grid height*grid width*(2^keys)) space complexity O(grid height*grid width*(2^keys))
    public int shortestPathAllKeys_BFSBitsHashMap(String[] grid) {
        int[][] moves=new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        HashSet<Character> keys=new HashSet<Character>();
        HashSet<Character> locks=new HashSet<Character>();
        int totalKeys=0;
        
        int width=grid[0].length();
        int height=grid.length;
        List<List<HashMap<Integer, Integer>>> shortestPathsFromStart=new ArrayList<>();
        
        Queue<int[]> next=new LinkedList<>();
        char c;
        
        for(int i=0; i<height; i++){
            shortestPathsFromStart.add(new ArrayList<>());
            for(int j=0; j<width; j++){
                c=grid[i].charAt(j);
                if(c=='@') {
                    next.add(new int[]{i,j,0,0});
                    shortestPathsFromStart.get(i).add(new HashMap<Integer, Integer>());
                    shortestPathsFromStart.get(i).get(j).put(0, 0);
                }else if(c!='#'){
                    if(c>='a' && c<='f') {
                        keys.add(c);
                        totalKeys+=1<<(c-97);
                        //System.out.println(c-97);
                        //System.out.println(totalKeys);
                    }else if(c>='A' && c<='F') locks.add(c);
                    shortestPathsFromStart.get(i).add(new HashMap<Integer, Integer>());
                }else shortestPathsFromStart.get(i).add(null);
            }
        }
        
        int count;
        int nextR;
        int nextC;
        int[] checkoutCell;
        int newKeyStat;
        
        while(!next.isEmpty()){
            count=next.size();
            while(count>0){
                checkoutCell=next.poll();
                
                for(int[] move: moves){
                    nextR=checkoutCell[0]+move[0];
                    nextC=checkoutCell[1]+move[1];
                    
                    if(nextR>=0 && nextR<height && nextC>=0 && nextC<width && shortestPathsFromStart.get(nextR).get(nextC)!=null){
                        c=grid[nextR].charAt(nextC);
                        
                        if(keys.contains(c)){
                            if(((1<<(c-97))&checkoutCell[2])!=0) continue;
                            newKeyStat=(checkoutCell[2] | (1<<(c-97)));
                            // System.out.println(c+" "+Integer.toBinaryString(newKeyStat)+" "+(checkoutCell[3]+1));
                            if(newKeyStat==totalKeys) return checkoutCell[3]+1;
                            shortestPathsFromStart.get(nextR).get(nextC).put(newKeyStat, checkoutCell[3]+1);
                            next.add(new int[]{nextR, nextC, newKeyStat, checkoutCell[3]+1});
                        }
                        
                        if(locks.contains(c) && (checkoutCell[2]&(1<<(c-65)))==0) continue;
                        else if(!shortestPathsFromStart.get(nextR).get(nextC).containsKey(checkoutCell[2])){
                            shortestPathsFromStart.get(nextR).get(nextC).put(checkoutCell[2], checkoutCell[3]+1);
                            next.add(new int[]{nextR, nextC, checkoutCell[2], checkoutCell[3]+1});
                        }
                    }
                }
                
                count--;
            }
        }
        
        return -1;
        
    }
    
    // Solution #3: bfs approach to find shortest paths
    // with tracking remaining keys needed at position using strings in a list of hashmaps
    // time complexity O(grid height*grid width*(2^keys)*keys) space complexity O(grid height*grid width*(2^keys))
    public int shortestPathAllKeys_BFSStringHashMap(String[] grid) {
        int[][] moves=new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        List<List<HashMap<String, Integer>>> shortestPathsFromStart=new ArrayList<>();
        HashSet<Character> keys=new HashSet<Character>();
        HashSet<Character> locks=new HashSet<Character>();
        StringBuilder keysNeeded=new StringBuilder("______");
        // int shortestPath=-1;
        int width=grid[0].length();
        int height=grid.length;
        Queue<int[]> next=new LinkedList<>();
        Queue<HashSet<Character>> nextKeys=new LinkedList<>();
        Queue<HashSet<Character>> nextLocks=new LinkedList<>();
        Queue<StringBuilder> nextKeysNeeded=new LinkedList<>();
        int count=0;
        
        
        for(int i=0; i<height; i++){
            shortestPathsFromStart.add(new ArrayList<>());
            for(int j=0; j<width; j++){
                if(grid[i].charAt(j)=='@') next.add(new int[]{i, j, 0});
                
                if(grid[i].charAt(j)!='#'){
                    if(grid[i].charAt(j)>='a' && grid[i].charAt(j)<='f'){
                        keys.add(grid[i].charAt(j));
                        keysNeeded.setCharAt(grid[i].charAt(j)-97, grid[i].charAt(j));
                    }else if(grid[i].charAt(j)>='A' && grid[i].charAt(j)<='F'){
                        locks.add(grid[i].charAt(j));
                    }
                    
                    shortestPathsFromStart.get(i).add(new HashMap<String, Integer>());
                }else shortestPathsFromStart.get(i).add(null);
            }
        }
        
        shortestPathsFromStart.get(next.peek()[0]).get(next.peek()[1]).put(keysNeeded.toString(), 0);
        nextKeys.add(keys);
        nextLocks.add(locks);
        nextKeysNeeded.add(keysNeeded);
        
        int[] checkoutCell;
        HashSet<Character> checkoutKeys;
        HashSet<Character> checkoutLocks;
        StringBuilder checkoutKeysNeeded;
        
        
        int nextRow;
        int nextCol;
        char c;
        String s;
        
        while(!next.isEmpty()){
            count=next.size();
            
            while(count>0){
                checkoutCell=next.poll();
                checkoutKeys=nextKeys.poll();
                checkoutLocks=nextLocks.poll();
                checkoutKeysNeeded=nextKeysNeeded.poll();
                
                for(int[] move: moves){
                    nextRow=checkoutCell[0]+move[0];
                    nextCol=checkoutCell[1]+move[1];
                    
                    if(nextRow>=0 && nextRow<height && nextCol>=0 && nextCol<width && shortestPathsFromStart.get(nextRow).get(nextCol)!=null){
                        c=grid[nextRow].charAt(nextCol);
                        s=checkoutKeysNeeded.toString();
                        
                        if(checkoutKeys.contains(c)){
                            checkoutKeys.remove(c);
                            checkoutKeysNeeded.setCharAt(c-97, '_');
                            s=checkoutKeysNeeded.toString();
                            if(!shortestPathsFromStart.get(nextRow).get(nextCol).containsKey(s) || checkoutCell[2]+1<shortestPathsFromStart.get(nextRow).get(nextCol).get(s)){
                                shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1);
                                // System.out.println("["+nextRow+"]"+"["+nextCol+"]"+" "+s+" "+shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1));
                                if(checkoutKeys.isEmpty()) {
                                    return checkoutCell[2]+1;
                                }else{
                                    next.add(new int[]{nextRow, nextCol, checkoutCell[2]+1});
                                    nextKeys.add((HashSet)checkoutKeys.clone());
                                    nextLocks.add((HashSet)checkoutLocks.clone());
                                    nextKeysNeeded.add(new StringBuilder(checkoutKeysNeeded));
                                }
                            }
                            
                            checkoutKeys.add(c);
                            checkoutKeysNeeded.setCharAt(c-97, c);
                            s=checkoutKeysNeeded.toString();
                            
                        }else if(checkoutLocks.contains(c)){
                            if(!checkoutKeys.contains((char)(c+32))){
                                checkoutLocks.remove(c);
                                s=checkoutKeysNeeded.toString();
                                if(!shortestPathsFromStart.get(nextRow).get(nextCol).containsKey(s) || checkoutCell[2]+1<shortestPathsFromStart.get(nextRow).get(nextCol).get(s)){
                                    shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1);
                                    // System.out.println("["+nextRow+"]"+"["+nextCol+"]"+" "+s+" "+shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1));
                                    next.add(new int[]{nextRow, nextCol, checkoutCell[2]+1});
                                    nextKeys.add((HashSet)checkoutKeys.clone());
                                    nextLocks.add((HashSet)checkoutLocks.clone());
                                    nextKeysNeeded.add(new StringBuilder(checkoutKeysNeeded));
                                }
                                checkoutLocks.add(c);
                            }
                            
                        }else if(!shortestPathsFromStart.get(nextRow).get(nextCol).containsKey(s) || checkoutCell[2]+1<shortestPathsFromStart.get(nextRow).get(nextCol).get(s)){
                            shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1);
                            // System.out.println("["+nextRow+"]"+"["+nextCol+"]"+" "+s+" "+shortestPathsFromStart.get(nextRow).get(nextCol).put(s, checkoutCell[2]+1));
                            next.add(new int[]{nextRow, nextCol, checkoutCell[2]+1});
                            nextKeys.add((HashSet)checkoutKeys.clone());
                            nextLocks.add((HashSet)checkoutLocks.clone());
                            nextKeysNeeded.add(new StringBuilder(checkoutKeysNeeded));
                        }
                        
                    }
                }
                
                count--;
            }
        }
        
        return -1;
    }
	
}
