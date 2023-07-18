package javachips;

import java.util.HashMap;
import java.util.List;

public class SmallestSufficientTeam {
	// Given a list of required skills req_skills, and a list of people. The ith person people[i] contains a list of skills that the person has.
	// Return any sufficient team of the smallest possible size, represented by the index of each person. You may return the answer in any order.
	// Note: a sufficient team: a set of people such that for every required skill in req_skills, there is at least one person in the team who has that skill. We can represent these teams by the index of each person.
	// For example, team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
	
	
	// Solution #1: consider all the req_skills are needed, any person may be included or skipped
	// Time complexity O(2^people) space complexity O(2^people)
	private long minTeam;
    //private int minTeamSize;
    private int requiredSkills;
    private int[] peopleWithSkills;
    private HashMap<Integer, HashMap<Integer, Long>> visited;
    
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        //minTeamSize=people.size();
        minTeam=0;
        requiredSkills=0;
        visited=new HashMap<Integer, HashMap<Integer, Long>>();
        
        HashMap<String, Integer> skills=new HashMap<String, Integer>();
        for(int i=0; i<req_skills.length; i++) {
            skills.put(req_skills[i], i);
            requiredSkills=(requiredSkills<<1)+1;
        }
        
        peopleWithSkills=new int[people.size()];
        int peopleSkills;
        String skill;
        for(int i=0; i<people.size(); i++){
            peopleSkills=0;
            for(int j=0; j<people.get(i).size(); j++){
                skill=people.get(i).get(j);
                if(skills.containsKey(skill)){
                    peopleSkills=peopleSkills | (1<<(req_skills.length-1-skills.get(skill)));
                }
            }
            // System.out.println(Integer.toBinaryString(peopleSkills));
            peopleWithSkills[i]=peopleSkills;
            minTeam=(minTeam<<1)+1;
        }
        
        minTeam=findSmallestTeam(0);
        //System.out.println("final team: "+Integer.toBinaryString(minTeam));
        int[] res=new int[Long.bitCount(minTeam)];
        int i=0;
        int d=people.size()-1;
        while(minTeam>0){
            if(minTeam%2==1) {
                res[i++]=d;
            }
            minTeam/=2;
            d--;
        }
        
        return res;
    }
    
    private long findSmallestTeam(int index){
        if(requiredSkills==0) return 0;
        else if(index==peopleWithSkills.length) return minTeam;
        else if(visited.containsKey(requiredSkills) && visited.get(requiredSkills).containsKey(index)) return visited.get(requiredSkills).get(index);
        
        
        //System.out.println("Exclude person: "+index+" Team: "+Long.toBinaryString(team));
        //System.out.println("Ex rest required skills: "+Integer.toBinaryString(requiredSkills));
        long excludePerson=findSmallestTeam(index+1);
        int person=peopleWithSkills[index];
        if(!visited.containsKey(requiredSkills)) visited.put(requiredSkills, new HashMap<Integer,Long>());
        
        if(person>0){
            int oldRequiredSkills=requiredSkills;
            //System.out.println("required skills before: "+Integer.toBinaryString(requiredSkills));
            //System.out.println("person's skills: "+Integer.toBinaryString(person));
            requiredSkills=requiredSkills&(requiredSkills^person);
            // requiredSkills=requiredSkills-person;
            //System.out.println("Include person: "+index+" New team: "+Long.toBinaryString(team|((long)1<<(peopleWithSkills.size()-1-index))));
            //System.out.println("In rest required skills: "+Integer.toBinaryString(requiredSkills));
            
            long includePerson=findSmallestTeam(index+1) | ((long)1<<(peopleWithSkills.length-1-index));
            requiredSkills=oldRequiredSkills;
            //System.out.println("back to required skills: "+Integer.toBinaryString(requiredSkills));
            
            long smallestTeam;
            if(Long.bitCount(excludePerson)>Long.bitCount(includePerson)) smallestTeam=includePerson;
            else smallestTeam=excludePerson;
            
            visited.get(requiredSkills).put(index, smallestTeam);
            return smallestTeam;
        }else{
            visited.get(requiredSkills).put(index, excludePerson);
            return excludePerson;
        }
    }
}
