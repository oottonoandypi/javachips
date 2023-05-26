package javachips;

import java.util.List;
import java.util.ArrayList;

public class BinaryWatch {
	public List<String> readBinaryWatch(int turnedOn) {
		// Approach; 10 choose turnedOn possibilities
		// runtime O(min(10^turnedOn, 10^(10-turnedOn))) where 0<turnedOn<=8 OR O(1) where turnedOn==0, 9, 10
		// max, 10^5; min 1
		// Additional memory usage, O(1)
        int[] hours=new int[]{1, 2, 4, 8};
        int[] mins=new int[]{1, 2, 4, 8, 16,32};
        int[][] visited=new int[12][60];
        List<String> times=new ArrayList<String>();
        if(turnedOn>8) return times;
        
        findTimes(times, new StringBuilder("00:00"), hours, 0, 0, mins, 0, 0, turnedOn, visited);
        return times;
    }
    
    private void findTimes(List<String> times, StringBuilder time, int[] hours, int hIndex, int h, int[] mins, int mIndex, int m, int turnedOn, int[][] visited){
        if(turnedOn==0) {
            if(visited[h][m]==0){
                time.setCharAt(0, (char)(h/10+'0'));
                time.setCharAt(1, (char)(h%10+'0'));
                time.setCharAt(3, (char)(m/10+'0'));
                time.setCharAt(4, (char)(m%10+'0'));

                if(h<10) times.add(time.substring(1));
                else times.add(time.toString());

                time.setCharAt(0,'0');
                time.setCharAt(1,'0');
                time.setCharAt(3,'0');
                time.setCharAt(4,'0');
                
                visited[h][m]=1;
            }
        }
        
        for(int i=hIndex; i<hours.length; i++){
            int addHour=hours[i];
            if(h+addHour<=11) findTimes(times, time, hours, i+1, h+addHour, mins, mIndex, m, turnedOn-1, visited);
        }
        
        for(int i=mIndex; i<mins.length; i++){
            int addMin=mins[i];
            if(m+addMin<=59) findTimes(times, time, hours, hIndex, h, mins, i+1, m+addMin, turnedOn-1, visited);
        }
    }
}
