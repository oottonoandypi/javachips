package practice;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.Scanner;

public class CourseScheduler {
	class Course{
        int courseNum;
        List<Course> prereqs;
        
        public Course(){}
        public Course(int courseNum){
            this.courseNum=courseNum;
            this.prereqs = new ArrayList<Course>();
        }
    }
	
	public boolean canFinishCourses(int numCourses, int[][] prerequisites) { // runtime: prerequisites*(numCourses+edges/connections between courses)
        Course[] courses = new Course[numCourses];
        for(int i=0; i<courses.length; i++) courses[i]=new Course(i);
        
        for(int[] prerequisite: prerequisites){
            int course = prerequisite[0];
            int prereq = prerequisite[1];
            
            if(course==prereq) return false;
            
            // if courses[prereq] and courses[course] both existed; 
            // need to scan if course has already been taken in prereqs of prereq.
            if(lookupCourse(course, courses[prereq].prereqs, new boolean[numCourses])) return false;
            else courses[course].prereqs.add(courses[prereq]);
        }
        
        return true;
    }
    
    /* public boolean canFinishCourses_deprecated(int numCourses, int[][] prerequisites) { // runtime: prerequisites*(numCourses+edges/connections between courses)
        Course[] courses = new Course[numCourses];
        
        for(int[] prerequisite: prerequisites){
            int course = prerequisite[0];
            int prereq = prerequisite[1];
            
            if(course==prereq) return false;
            
            if(courses[prereq]==null && courses[course]==null){
                courses[prereq]=new Course(prereq);
                courses[course]=new Course(course);
                courses[course].prereqs.add(courses[prereq]);
            }else if(courses[course]==null) {
                courses[course]=new Course(course);
                courses[course].prereqs.add(courses[prereq]);
            }else if(courses[prereq]==null){
                courses[prereq]=new Course(prereq);
                courses[course].prereqs.add(courses[prereq]);
            }else{
                // if courses[prereq] and courses[course] both existed; 
                // need to scan if course has already been taken in prereqs of prereq.
                if(lookupCourse(course, courses[prereq].prereqs, new boolean[numCourses])) return false;
                else courses[course].prereqs.add(courses[prereq]);
            }
        }
        
        return true;
    }*/
    
    // helper function that searches if course has been taken as a prereq already
    private boolean lookupCourse(int course, List<Course> prereqs, boolean[] hasVisited){
        if(prereqs.size()==0) return false;
        
        boolean found = false;
        for(Course prereq: prereqs){
            if(!hasVisited[prereq.courseNum]){
                if(prereq.courseNum==course) return true;
                found = found || lookupCourse(course, prereq.prereqs, hasVisited);
                hasVisited[prereq.courseNum]=true;
            }
        }
        return found;
    }
    
    private static int[][] convertStr_2DArray(String str, int rows, int cols){
    	int[][] _2DArray = new int[rows][cols];
    	int index = 0;
		int countInt = 0;
		int courseNum = 0;
		
		
		for(int i=0; i<str.length() && index<rows; i++) {
			char c = str.charAt(i);
			
			if(c>='0' && c<='9') {
				courseNum=courseNum*10+(int)(c-48);
			}else if((c==',' || c==']') && i-1>=0 && str.charAt(i-1)>='0'&& str.charAt(i-1)<='9') {
				_2DArray[index][countInt++]=courseNum;
				courseNum=0;
				if(countInt==cols) {
					countInt=0;
					index++;
				}
			}
		}
		
		return _2DArray;
    }
    
    public static void main(String[] args) {
    	try {
    		File inputFile = new File("src/practice/CourseScheduler_canFinishCourses_TestInput.txt");
    		File outputFile = new File("src/practice/CourseScheduler_canFinishCourses_TestOutput.txt");
    		
    		Scanner inScan = new Scanner(inputFile);
    		Scanner outScan = new Scanner(outputFile);
    		
    		while(inScan.hasNextLine() && outScan.hasNextLine()) {
    			int numCourses=inScan.nextInt();
    			if(inScan.hasNextLine()) {
    				int prerequisitesSize = inScan.nextInt();
        			String prerequisitesArr_str=inScan.next();
        			// Convert prerequisitesArr_str to 2D array;
        			int[][] prerequisites = convertStr_2DArray(prerequisitesArr_str, prerequisitesSize, 2);
        			CourseScheduler scheduler = new CourseScheduler();
        			boolean canFinish = scheduler.canFinishCourses(numCourses, prerequisites);
        			
        			// validate
        			String testoutput = outScan.next();
        			System.out.print("Result: "+canFinish+"; ");
        			System.out.print("TestOutput: "+testoutput+"; ");
        			if((canFinish && testoutput.equals("true")) || (!canFinish && testoutput.equals("false"))) System.out.println("CORRECT");
        			else System.out.println("NOT CORRECT");
    			}else {
    				System.out.println("ERROR:: INVALID INPUT.");
    			}
    		}
    		inScan.close();
    		outScan.close();
    	}catch(Exception e) {
    		System.out.println("ERROR:: INVALID FILE. "+e);
    	}
    }
}
