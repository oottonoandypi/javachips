package javachips;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

// ** CAN BE OPTIMIZED **
public class DivisionEvaluation {
	private HashMap<String, HashMap<String, Double>> valueMap;
    private HashSet<String> included;
	
	public DivisionEvaluation(List<List<String>> equations, double[] values) {
		this.valueMap=new HashMap<String, HashMap<String, Double>>();
		this.included=null;
		
		String str1;
        String str2;
		
		for(int i=0; i<equations.size(); i++){
            str1=equations.get(i).get(0);
            str2=equations.get(i).get(1);
            if(!valueMap.containsKey(str1)) valueMap.put(str1, new HashMap<String, Double>());
            if(!valueMap.containsKey(str2)) valueMap.put(str2, new HashMap<String, Double>());
            
            valueMap.get(str1).put(str2, values[i]);
            valueMap.get(str2).put(str1, 1/values[i]);
            
            //System.out.println(str1+" "+str2+" "+valueMap.get(str1).get(str2));
            //System.out.println(str2+" "+str1+" "+valueMap.get(str2).get(str1));
        }
	}
	
	public double[] calcQueries(List<List<String>> queries) {
		double[] res=new double[queries.size()];
		String str1;
        String str2;
        
        for(int i=0; i<res.length; i++){
            included=new HashSet<String>();
            str1=queries.get(i).get(0);
            str2=queries.get(i).get(1);
            res[i]=findValue(str1, str2);
        }
        return res;
	}
	
	private double findValue(String str1, String str2){
		// time complexity O(n^2) space complexity(n^2)
        // System.out.println(str1+" "+str2);
        if(!valueMap.containsKey(str1) || !valueMap.containsKey(str2)) return (double)(-1);
        if(str1.equals(str2)) return (double)(1);
        if(valueMap.containsKey(str1) && valueMap.get(str1).containsKey(str2)) return valueMap.get(str1).get(str2);
        
        // ** this part can be improved by giving each string an integer id and use a 2D array instead of hashmap to store
        // Will update later
        Iterator<String> stringKeys=valueMap.get(str1).keySet().iterator();
        List<String> stringKeysList=new ArrayList<String>();
        String nextKey;
        while(stringKeys.hasNext()) {
            nextKey=stringKeys.next();
            if(valueMap.get(str1).get(nextKey)!=-1) stringKeysList.add(nextKey);
        }
        
        double val=-1;
        for(int i=0; i<stringKeysList.size(); i++){
            nextKey=stringKeysList.get(i);
            if(included.contains(nextKey)) continue;
            included.add(nextKey);
            val=findValue(nextKey, str2);
            included.remove(nextKey);
            if(val!=-1) {
                val*=valueMap.get(str1).get(nextKey);
                break;
            }
        }
        
        valueMap.get(str1).put(str2, val);
        valueMap.get(str2).put(str1, 1/val);
        
        return val;
    }
}
