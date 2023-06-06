package javachips;

import java.util.HashMap;

public class SubwaySystem {
	class Rider{
        private int id;
        public String checkInStation;
        public int checkInTime;
        
        public Rider(int id){
            this.id=id;
        }
    }
    
    /*
    class Routes{
        
    }
    */
    
    private HashMap<String, HashMap<String, int[]>> averageTime;
    private HashMap<Integer, Rider> checkInData;
    
    public SubwaySystem() {
        this.averageTime=new HashMap<String, HashMap<String, int[]>>();
        this.checkInData=new HashMap<Integer, Rider>();
    }
    
    public void checkIn(int id, String stationName, int t) {
    	// runtime O(1)
        if(!checkInData.containsKey(id)) checkInData.put(id, new Rider(id));
        Rider rider=checkInData.get(id);
        rider.checkInStation=stationName;
        rider.checkInTime=t;
    }
    
    public void checkOut(int id, String stationName, int t) {
    	// runtime O(1)
        if(checkInData.containsKey(id)){
            Rider rider= checkInData.get(id);
            if(rider.checkInStation!=null) {
                HashMap<String, int[]> at=averageTime.get(rider.checkInStation);
                
                if(at==null) {
                    at=new HashMap<String, int[]>();
                    at.put(stationName, new int[]{t-rider.checkInTime, 1});
                    averageTime.put(rider.checkInStation, at);
                }else if(at.get(stationName)==null){
                    at.put(stationName, new int[]{t-rider.checkInTime, 1});
                }else{
                    at.get(stationName)[0]+=t-rider.checkInTime;
                    at.get(stationName)[1]++;
                }
            }
            
        }
    }
    
    public double getAverageTime(String startStation, String endStation) {
    	// runtime O(1)
    	
        //System.out.println("startStation: "+startStation);
        //System.out.println("endStation: "+endStation);
        HashMap<String, int[]> at=averageTime.get(startStation);
        // System.out.println(at==null);
        if(at!=null) {
            int[] totalTime=at.get(endStation);
            // System.out.println(totalTime==null);
            if(totalTime!=null) {
                //System.out.println(totalTime[0]+" "+totalTime[1]);
                return (double)totalTime[0]/(double)totalTime[1];
            }
        }
        return 0;
    }
}
