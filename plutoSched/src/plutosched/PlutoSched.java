/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plutosched;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Matt
 */
public class PlutoSched {
 
 protected static final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
 static Connection conn;
 static databaseFunctions logs=new databaseFunctions();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //truncate the table to start
        getDatabaseConnection();      

        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE table DISTANCES");
            stmt.executeUpdate("TRUNCATE table ENROLLMENT");
            stmt.executeUpdate("TRUNCATE table EVENTLOG");
            stmt.executeUpdate("TRUNCATE table PREFERENCES");
            stmt.executeUpdate("TRUNCATE table ROOMS");           
            stmt.executeUpdate("TRUNCATE table SCHEDULE");            
            stmt.executeUpdate("DELETE from USERID.LOGIN");
            stmt.executeUpdate("DELETE from USERID.SECTIONS");
            stmt.close();
            conn.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        createAdmin();
        
        //create arraylist to store objects and load the files 
        ArrayList<enroll> enroll;
        ArrayList<String> enrollFile, intructorFile,roomFile,sectionsFile,timeFile;       
        ArrayList<professor> professor;
        ArrayList<room> rooms;
        ArrayList<sections> section;
        Double[][] travelMatrix;
        logs.logEvent("Begin Reading Files");
        String enrollFileName="C:\\Users\\Matt\\Documents\\ProjectTextFiles\\enroll.txt";
        String instructorFileName="C:\\Users\\Matt\\Documents\\ProjectTextFiles\\preferences.txt";
        String roomFileName="C:\\Users\\Matt\\Documents\\ProjectTextFiles\\rooms.txt";
        String sectionsFileName="C:\\Users\\Matt\\Documents\\ProjectTextFiles\\sections.txt";
        String timeFileName="C:\\Users\\Matt\\Documents\\ProjectTextFiles\\travel.txt";
        logs.logEvent("Finished Reading Files");
        //read in enroll file and create an array of enroll
        enrollFile=readFile(enrollFileName);
        enroll=createEnroll(enrollFile);
        //read in instructor prefs and create an array of insturctors and their prefs
        intructorFile=readFile(instructorFileName);
        professor=createProfessor(intructorFile);
        //read in file of rooms and create an array of room objects
        roomFile=readFile(roomFileName);
        rooms=createRoom(roomFile);
        //read in sections file and create an array of section objects
        sectionsFile=readFile(sectionsFileName);
        section=createSection(sectionsFile);
        addSeatsNeeded(section,enroll);
        //read in time matrix
        timeFile=readFile(timeFileName);
        travelMatrix=timeMatrix(timeFile);
        Collections.sort(section);
        Collections.sort(rooms);
        //schedule the clases
        classScheduler classes=new classScheduler(professor,rooms,section,travelMatrix);
        ArrayList<sections> scheduledClasses=classes.getScheduledClasses();
        //write the classes to a file
        if(scheduledClasses.isEmpty()==false){
            try{
                File schClasFileName=new File("C:\\Users\\Matt\\Documents\\ProjectTextFiles\\schedClasses.txt");
                try (BufferedWriter outFile = new BufferedWriter(new FileWriter(schClasFileName))) {
                    outFile.write("CourseNumber|Department|CallNumber|MeetingDays|MeetingTimes|InstructorName|Room|Building");
                    outFile.newLine();
                    for (sections scheduledClasse : scheduledClasses) {
                        outFile.write(scheduledClasse.toString());
                        outFile.newLine();
                        
                    }//end for
                }//end try with resorces
            }//end try//end try
            catch(IOException e){
                logs.logEvent("Write to file failed");
            }//end catch
        }//end if
        else{
            logs.logEvent("No classes were scheuled nothing to write to file");
        }
        
       
//        System.out.println(enroll.get(749).getCourseNum());
//        System.out.println(professor.get(2).canTeachClasses());
//        System.out.println(rooms.get(3).getBuilding());
//        System.out.println(section.get(5).getCallNumber());
//        System.out.println(section.get(0).getSeatsNeeded());
//        System.out.println(section.get(0).getEndTime());
//        System.out.println(travelMatrix[1][2]);
//        System.out.println(section.get(1).getSeatsNeeded());
        
        
        
        
    }
    
    public static ArrayList<enroll> createEnroll(ArrayList<String> enrollFile){
        //create a arraylist of enroll  objects from the enrollFile arraylist
        ArrayList<enroll> enrollment= new ArrayList();
        
        for(int i=0; i<enrollFile.size();i++){
        List array = new ArrayList();
        String line=enrollFile.get(i);
        StringTokenizer fields = new StringTokenizer(line, "|");
        while(fields.hasMoreTokens()){
            array.add(fields.nextToken());
        }
        
        enroll enrolled=new enroll(Integer.parseInt((String) array.get(0)), (String) array.get(1),Integer.parseInt((String) array.get(2)));
        enrollment.add(enrolled);
        }
        
        return enrollment;
        
    }
    public static ArrayList<sections> createSection(ArrayList<String> sectionFile){
        //create arraylist of sections object from sections arraylist
        ArrayList<sections> section= new ArrayList();
        
        for(int i=0; i<sectionFile.size();i++){
        List array = new ArrayList();
        String line=sectionFile.get(i);
        StringTokenizer fields = new StringTokenizer(line, "|");
        while(fields.hasMoreTokens()){
            array.add(fields.nextToken());
        }
        
        sections sect=new sections(Integer.parseInt((String) array.get(0)),(String) array.get(1),Integer.parseInt((String) array.get(2)),
                Integer.parseInt((String) array.get(3)),Integer.parseInt((String) array.get(4)),(String) array.get(5));
        section.add(sect);
        }
        
        return section;
    }
    
    public static ArrayList<room> createRoom(ArrayList<String> roomFile){
        //create a arraylist of room objects from roomFile Arraylist
        ArrayList<room> rooms= new ArrayList();
        
        for(int i=0; i<roomFile.size();i++){
        List array = new ArrayList();
        String line=roomFile.get(i);
        StringTokenizer fields = new StringTokenizer(line, "|");
        while(fields.hasMoreTokens()){
            array.add(fields.nextToken());
        }
        
        room roomNum=new room(Integer.parseInt((String) array.get(0)),(String) array.get(1),Integer.parseInt((String) array.get(2)),(String) array.get(3),(String) array.get(4));
        rooms.add(roomNum);
        }
        
        return rooms;
        
        
    }
    public static ArrayList<professor> createProfessor(ArrayList<String> instructorFile){
        //create an arraylist of professor objects from instructorFile ArrayList
        ArrayList<professor> professors = new ArrayList();
        
        
        for(int i=0; i<instructorFile.size();i++){
        List array = new ArrayList();
        String line=instructorFile.get(i);
        StringTokenizer fields = new StringTokenizer(line, "|");
        while(fields.hasMoreTokens()){
            array.add(fields.nextToken());
        }
        
        professor instructor=new professor((String) array.get(0),(String) array.get(1),Integer.parseInt((String) array.get(2)),
                (String) array.get(3), (String) array.get(4), (String) array.get(5),(String) array.get(6), (String) array.get(7));
        professors.add(instructor);
        }
        
        return professors;
        
    }
    
    public static ArrayList<String> readFile(String fileName){
        //read in file and put each row into array list   
        ArrayList<String> fileArray=new ArrayList();
        //read in the enroll file and create a array of enrollment objects
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line1=reader.readLine();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                
                fileArray.add(line);
                //enrollArray.add(createEnroll(line));
                //System.out.println(line);
            }
        } catch (Exception e) {
        logs.logEvent(e.getMessage());
        }
        
        return fileArray;
        
    }
    
    public static void addSeatsNeeded(ArrayList<sections> sectionsArray, ArrayList<enroll> enrollArray){
        //add the number of seats from the enroll object to the room object
        for(int i=0; i<sectionsArray.size();i++){
            
            for(int x=0; x<enrollArray.size();x++){
                if(sectionsArray.get(i).getCourseNumber()==enrollArray.get(x).getCourseNum() && sectionsArray.get(i).getDepartment().equals(enrollArray.get(x).getDepartment())){
                    sectionsArray.get(i).setSeatsNeeded(enrollArray.get(x).getNumEnrolled());
                    break;
                }//end if
                
                
            }// end x for
        
        }//end i for
    }// end addSeatsNeeded
    public static Double[][] timeMatrix(ArrayList<String> timeTable){       
        //create the time distance matrix
        Double [][] times=new Double[4][4];
        String[] campusArray={"NORTH","SOUTH","EAST","WEST"};
        for(int i=0; i<timeTable.size();i++){
            List array = new ArrayList();
            String line=timeTable.get(i);
            StringTokenizer fields = new StringTokenizer(line, "|");
                while(fields.hasMoreTokens()){
                    array.add(fields.nextToken());
                }//end while
            times[i][0]=Double.parseDouble((String)array.get(1));
            times[i][1]=Double.parseDouble((String)array.get(2));
            times[i][2]=Double.parseDouble((String)array.get(3));
            times[i][3]=Double.parseDouble((String)array.get(4));
        
        
        }//end for
        for(int i=0;i<4;i++){
        insertTravelTimes(campusArray[i],times[i][0],times[i][1],times[i][2],times[i][3]);
        }
        return times;
    }
 
    protected static void getDatabaseConnection(){
      //create connection to database
      try{         
       conn = DriverManager.getConnection(DB_URL,"userid","userid");
      }//end try
      catch (Exception e){
        System.out.println("could not make connection to database");
        System.out.println(e.getMessage());
      }//end catch
   }//end detDatabaseConnection
    
    private static void insertTravelTimes(String campus,double timeOne,double timeTwo, double timeThree,double timeFour){
        //insert time distance matrix to the database
        getDatabaseConnection();
        PreparedStatement stmt = null;
        try{
            String query = "insert into DISTANCES(CAMPUS,NORTHDISTANCE,SOUTHDISTANCE,EASTDISTANCE,WESTDISTANCE) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, campus);
            stmt.setDouble(2, timeOne);
            stmt.setDouble(3, timeTwo);
            stmt.setDouble(4, timeThree);
            stmt.setDouble(5, timeFour);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
//            try (Statement stmt = conn.createStatement()) {
//                String sqlStatement = " INSERT INTO DISTANCES " +"(CAMPUS,NORTHDISTANCE,SOUTHDISTANCE,EASTDISTANCE,WESTDISTANCE)"+
//                        "VALUES ('" +campus+"' , "+timeOne+", "+timeTwo+", "+timeThree+", "+timeFour+")";
//                stmt.executeUpdate(sqlStatement);
//                System.out.println("added travelTime");
//            }//end try with resources
//            conn.close();
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }//end insertPrefences
    public static void createAdmin(){
        //insert a admin to the login table
        getDatabaseConnection();
        try{
            String queryTwo = "insert into LOGIN(USERID,PASSWORD,USERTYPE) values(?,?,?)";
            PreparedStatement stmtTwo = null;
            stmtTwo = conn.prepareStatement(queryTwo);
            stmtTwo.setString(1, "Admin");
            stmtTwo.setString(2,"password123");
            stmtTwo.setString(3, "A");
            stmtTwo.executeUpdate();
            stmtTwo.close();
            conn.close();
            //System.out.println("added prof");
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }

}
