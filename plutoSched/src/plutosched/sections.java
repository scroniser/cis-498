/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plutosched;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 *
 * @author Matt
 */
public class sections implements Comparable{
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    static Connection conn;
    
    private int courseNumber, callNumber,time, seatsNeeded, days, endTime,startTime,room;
    private String department, mediaRequired, instructor,building,stringEndTime,stringStartTime;
    //private boolean monday=false, tuesday=false,wedensday=false, thursday=false;
    private boolean scheduled=false;
    private java.sql.Time sqlStartTime,sqlEndTime;
    private static databaseFunctions logs=new databaseFunctions();
    
    public sections(int courseNumber, String department, int callNumber, int days, int time, String mediaRequired){
        //create the sections object
        this.courseNumber=courseNumber;
        this.department=department;
        this.callNumber=callNumber;
        this.days=days;
        this.time=time;
        this.mediaRequired=mediaRequired;
       
        String stringTime=Integer.toString(time);
        
        this.stringEndTime = stringTime.substring(Math.max(0, stringTime.length() - 4));
        String endHours=this.stringEndTime.substring(0,2);
        String endMins=this.stringEndTime.substring(2,4);
        String constructedEndTime=endHours+":"+endMins+":00";
        this.sqlEndTime=java.sql.Time.valueOf(constructedEndTime);
        
        
        if(stringTime.length()==7){
            String firstFour = stringTime.substring(0,3);
            this.stringStartTime=firstFour;
            this.startTime=Integer.parseInt(firstFour);
            String firstDigit=firstFour.substring(0,1);
            String minDigits=firstFour.substring(1,3);
            String constructedTime="0"+firstDigit+":"+minDigits+":00";
            this.sqlStartTime= java.sql.Time.valueOf(constructedTime);
        }
        else if(stringTime.length()==8){
            String firstFour = stringTime.substring(0,4);
            this.stringStartTime=firstFour;
            this.startTime=Integer.parseInt(firstFour);
            String firstDigit=firstFour.substring(0,2);
            String minDigits=firstFour.substring(2,4);
            String constructedTime=firstDigit+":"+minDigits+":00";
            this.sqlStartTime= java.sql.Time.valueOf(constructedTime);
        }
        else{
            //print to error log
        }
        endTime=Integer.parseInt(stringEndTime);
        //System.out.println(this.sqlStartTime);
        //System.out.println(this.sqlEndTime);
        insertSection();
    }
    
    public void setSeatsNeeded(int seatsNeeded){
        this.seatsNeeded=seatsNeeded;
        
    }
    public void setRoom(int room){
        this.room=room;
    }
    public int getRoom(){
        return this.room;
    }
    public void setBuilding(String building){
        this.building=building;
    }
    public String getBuilding(){
        return this.building;
    }
    public void setInstructor(String instructor){
        this.instructor=instructor;
        this.scheduled=true;
    }
    public String getInstructor(){
        return this.instructor;
    }
    public int getStartTime(){
        return this.startTime;
    }
    public int getEndTime(){
        return this.endTime;
    }
    
    public int getSeatsNeeded(){
        return this.seatsNeeded;
    }
    public int getCourseNumber() {
        return courseNumber;
    }

    public int getCallNumber() {
        return callNumber;
    }

  

    public int getTime() {
        return time;
    }
    
    public int getDays(){
        return days;
    }

    public String getDepartment() {
        return department;
    }

    public String getMediaRequired() {
        return mediaRequired;
    }
    

    
    



    @Override
    public int compareTo(Object classTime) {
        //sort sections in ascending order 
        int compTime=((sections)classTime).getTime();
        //return in ascending order
        return this.time-compTime;
       
    }
    
    @Override
    public String toString(){
        //method for printing the schedule
        if(scheduled==true){
            return this.getCourseNumber()+"|"+this.getDepartment()+"|"+
                    this.getCallNumber()+"|"+this.getDays()+"|"+this.getStartTime()+
                    " |"+this.getEndTime()+"|"+this.getInstructor()+"|"+this.getRoom()+"|"+this.getBuilding();
        }
        else{
            return "Course Number: "+this.getCourseNumber()+" Call Number: "+this.getCallNumber()+" Was Not Scheduled";
        }
    }
    
    private void insertSection(){
        //insert section into database
        getDatabaseConnection();
        PreparedStatement stmt = null;
        String stringDays;
        int stringLength=Integer.toString(this.days).length();
           if(stringLength==2){
               stringDays=("00000"+Integer.toString(this.days));
           }
           else if(stringLength==6){
               stringDays=("0"+Integer.toString(this.days));
           }
           else{
               stringDays=Integer.toString(this.days);
           }
        try{
           String query ="INSERT INTO SECTIONS(CALLNUMBER,DEPARTMENT,COURSENUMBER,DAYS,STARTTIME,ENDTIME,MEDIAREQUIRED) values(?,?,?,?,?,?,?)";  
           stmt = conn.prepareStatement(query);
           stmt.setInt(1, this.callNumber);
           stmt.setString(2, this.department);
           stmt.setInt(3, this.courseNumber);
           stmt.setString(4, stringDays);
           stmt.setTime(5, this.sqlStartTime);
           stmt.setTime(6, this.sqlEndTime);
           stmt.setString(7, this.mediaRequired);
           stmt.executeUpdate();
           stmt.close(); 
           conn.close(); 
            
            
//            try (Statement stmt = conn.createStatement()) {                 
//                
//                String sqlStatement = " INSERT INTO SECTIONS " +"(CALLNUMBER,DEPARTMENT,COURSENUMBER,DAYS,STARTTIME,ENDTIME,MEDIAREQUIRED)"+
//                        "VALUES (" +this.callNumber+" , '"+this.department+"', "+this.courseNumber+", '"+this.days+"', '"+this.sqlStartTime+"', '"+this.sqlEndTime+"', '"+this.mediaRequired+"')";
//                stmt.executeUpdate(sqlStatement);
//               // System.out.println("added section");
//            }//end try with resources
//            conn.close();
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }//end section
    
 public void insertSqlSched(){
        //insert schedule row in database
        getDatabaseConnection();
        PreparedStatement stmt = null;
        String stringDays;
        int stringLength=Integer.toString(this.days).length();
           if(stringLength==2){
               stringDays=("00000"+Integer.toString(this.days));
           }
           else if(stringLength==6){
               stringDays=("0"+Integer.toString(this.days));
           }
           else{
               stringDays=Integer.toString(this.days);
           }
        try{
           String query ="INSERT INTO SCHEDULE(CALLNUMBER,COURSENUMBER,DEPARTMENT,DAYS,STARTIME,ENDTIME,INSTRUCTOR,ROOM,BUILDING) values(?,?,?,?,?,?,?,?,?)";  
           stmt = conn.prepareStatement(query);
           stmt.setInt(1, this.callNumber);
           stmt.setInt(2, this.courseNumber);
           stmt.setString(3, this.department);
           stmt.setString(4, stringDays);
           stmt.setTime(5,this.sqlStartTime );
           stmt.setTime(6, this.sqlEndTime);
           stmt.setString(7, this.instructor);
           stmt.setInt(8, this.room);
           stmt.setString(9, this.building);
           stmt.executeUpdate();
           stmt.close(); 
           conn.close();
           
//            try (Statement stmt = conn.createStatement()) {
//                String sqlStatement = " INSERT INTO SCHEDULE " +"(CALLNUMBER,COURSENUMBER,DEPARTMENT,DAYS,STARTIME,ENDTIME,INSTRUCTOR,ROOM,BUILDING)"+
//                        "VALUES (" +this.callNumber+" , "+this.courseNumber+", '"+this.department+"', '"+this.days+"','"+this.sqlStartTime+"','"+this.sqlEndTime+"','"+this.instructor+"',"+this.room+",'"+this.building+"')";
//                stmt.executeUpdate(sqlStatement);
//                System.out.println("added class");
//            }//end try with resources
//            conn.close();
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }
    
    
    protected void getDatabaseConnection(){
      //connect to database
      try{
         
          conn = DriverManager.getConnection(DB_URL,"userid","userid");
      }//end try
      catch (Exception e){
        System.out.println("could not make connection to database");
        System.out.println(e.getMessage());
      }//end catch
   }//end detDatabaseConnection

   

   
    
}
