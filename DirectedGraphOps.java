package javachips;

import java.util.List;
import java.util.ArrayList;

public class DirectedGraphOps {
	// Find Eventual Safe Nodes
	// Input: a directed graph of n nodes with each node labeled from 0 to n - 1, 
				// represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, 
				// meaning there is an edge from node i to each node in graph[i]
	// Return: an array containing all the safe nodes of the graph in ascending order.
	// Def of SAFE NODE: A node is a terminal node if there are no outgoing edges. 
						// A node is a SAFE NODE if every possible path starting from that node leads to a terminal node 
						// (or another safe node).
	private static boolean[] visited;
    private static boolean[] visitedSafeNodes;
    private static boolean[] validatedSafeNodes;
    private static int[][] graphRef;
    public static List<Integer> eventualSafeNodes(int[][] graph) {
        // time complexity O(n+links) space complexity O(n)
        List<Integer> safeNodes=new ArrayList<Integer>();
        graphRef=graph;
        visited=new boolean[graphRef.length];
        visitedSafeNodes=new boolean[graphRef.length];
        validatedSafeNodes=new boolean[graphRef.length];
        
        for(int i=0; i<graphRef.length; i++){
            if(validateSafeNode(i)) safeNodes.add(i);
        }
        
        return safeNodes;
    }
    
    private static boolean validateSafeNode(int i){
        if(visitedSafeNodes[i]) return validatedSafeNodes[i];
        if(visited[i]) return false;
        if(graphRef[i].length==0) return true;
        
        boolean res=true;
        visited[i]=true;
        for(int j=0; j<graphRef[i].length && res; j++){
            res=res && validateSafeNode(graphRef[i][j]);
        }
        visited[i]=false;
        visitedSafeNodes[i]=true;
        validatedSafeNodes[i]=res;
        return res;
    }
}
