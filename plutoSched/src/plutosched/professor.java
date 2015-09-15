/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plutosched;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class professor {
   //change campus readouts to north south east west not yes and no
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    static Connection conn;
    private String department, instructorName, northCampus, southCampus, westCampus,eastCampus,weekend,instructorId;
    private int numSectionsToTeach, sectionsTeaching=0;
    private boolean canTeach=true;
    private ArrayList<Integer> monday=new ArrayList();
    private ArrayList<Integer> tuesday=new ArrayList();
    private ArrayList<Integer> wednesday=new ArrayList();
    private ArrayList<Integer> thursday=new ArrayList();
    private ArrayList<Integer> friday=new ArrayList();
    private ArrayList<Integer> saturday=new ArrayList();
    private ArrayList<Integer> mondayCampusIndex=new ArrayList();
    private ArrayList<Integer> tuesdayCampusIndex=new ArrayList();
    private ArrayList<Integer> wednesdayCampusIndex=new ArrayList();
    private ArrayList<Integer> thursdayCampusIndex=new ArrayList();
    private ArrayList<Integer> fridayCampusIndex=new ArrayList();
    private ArrayList<Integer> saturdayCampusIndex=new ArrayList();
    private static ArrayList<String> instructorIdList = new ArrayList();
    private static databaseFunctions logs=new databaseFunctions();
    
    

    public int getSectionsTeaching() {
        return sectionsTeaching;
    }

    public int getMondayEnd() {
        if(monday.isEmpty()==true){
            return 0;
        }
        else{
            return monday.get(monday.size()-1);
        }
    }

    public int getTuesdayEnd() {
        if(tuesday.isEmpty()==true){
            return 0;
        }
        else{
            return tuesday.get(tuesday.size()-1);
        }
    }

    public int getWednesdayEnd() {
        if(wednesday.isEmpty()==true){
            return 0;
        }
        else{
            return wednesday.get(wednesday.size()-1);
        }
    }

    public int getThursday() {
        if(thursday.isEmpty()==true){
            return 0;
        }
        else{
            return thursday.get(thursday.size()-1);
        }
    }

    public int getFriday() {
        if(friday.isEmpty()==true){
            return 0;
        }
        else{
            return friday.get(friday.size()-1);
        }
    }

    public int getSaturday() {
        if(saturday.isEmpty()==true){
            return 0;
        }
        else{
            return saturday.get(saturday.size()-1);
        }
    }

    public void setMondayEnd(int monday) {
        this.monday.add(monday);
    }

    public void setTuesdayEnd(int tuesday) {
        this.tuesday.add(tuesday);
    }

    public void setWednesdayEnd(int wednesday) {
        this.wednesday.add(wednesday);
    }

    public void setThursdayEnd(int thursday) {
        this.thursday.add(thursday);
    }

    public void setFridayEnd(int friday) {
        this.friday.add(friday);
    }

    public void setSaturdayEnd(int saturday) {
        this.saturday.add(saturday);
    }



    
    public professor(String department, String instructorName, int numSectionsToTeach, String northCampus, String southCampus,
            String westCampus, String eastCampus, String weekend){
        //creates a professor object
        this.department=department;
        this.eastCampus=eastCampus;
        this.instructorName=instructorName;
        this.northCampus=northCampus;
        this.numSectionsToTeach=numSectionsToTeach;
        this.southCampus=southCampus;
        this.weekend=weekend;
        this.westCampus=westCampus;  
        Random rand = new Random();
        
        
        if(this.numSectionsToTeach==this.sectionsTeaching){
            this.canTeach=false;
        }
        boolean idFree=false;
        while(idFree==false){
           this.instructorId = Integer.toString(10000000 + rand.nextInt(90000000));
           if(!instructorIdList.contains(this.instructorId)){
               instructorIdList.add(this.instructorId);
               idFree=true;
           }
        }
       insertPrefernces();
    }

    public String getDepartment() {
        return department;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getNorthCampus() {
        if("YES".equals(northCampus.toUpperCase())){
            return "NORTH";
        }
        else{
            return northCampus;
        }
    }

    public String getSouthCampus() {
        if("YES".equals(southCampus.toUpperCase())){
            return "SOUTH";
        }
        else{
            return southCampus;
        }
    }

    public String getWestCampus() {
        if("YES".equals(westCampus.toUpperCase())){
            return "WEST";
        }
        else{
            return westCampus;
        }
    }

    public String getEastCampus() {
        if("YES".equals(eastCampus.toUpperCase())){
            return "EAST";
        }
        else{
            return eastCampus;
        }
    }

    public String getWeekend() {
        return weekend;
    }

    public int getNumSectionsToTeach() {
        return numSectionsToTeach;
    }
    public boolean canTeachClasses(){
        return this.canTeach;
    }
    
    public void addSection(){
        this.sectionsTeaching++;
        if(this.numSectionsToTeach==this.sectionsTeaching){
            this.canTeach=false;
        }
        
    }
    
    public void setOnMondayCampus(int campus){
        mondayCampusIndex.add(campus);
    }
    public void setOnTuesdayCampus(int campus){
        tuesdayCampusIndex.add(campus);
    }
    public void setOnWednesdayCampus(int campus){
        wednesdayCampusIndex.add(campus);
    }
    public void setOnThursdayCampus(int campus){
        thursdayCampusIndex.add(campus);
    }
    public void setOnFridayCampus(int campus){
        fridayCampusIndex.add(campus);
    }
    public void setOnSaturdayCampus(int campus){
        saturdayCampusIndex.add(campus);
    }
    
    public int getOnMondayCampus(){
        if(mondayCampusIndex.isEmpty()==true){
            return -1;
        }
        else{
            return mondayCampusIndex.get(mondayCampusIndex.size()-1);
        }
    }
    public int getOnTuesdayCampus(){
        if(tuesdayCampusIndex.isEmpty()==true){
            return -1;
        }
        else{
            return tuesdayCampusIndex.get(tuesdayCampusIndex.size()-1);
        }
    }
    public int getOnWednesdayCampus(){
        if(wednesdayCampusIndex.isEmpty()==true){
            return -1;
        }
        else{
            return wednesdayCampusIndex.get(wednesdayCampusIndex.size()-1);
        }
    }
    public int getOnThursdayCampus(){
        if(thursdayCampusIndex.isEmpty()==true){
            return -1;
        }
        else{
            return thursdayCampusIndex.get(thursdayCampusIndex.size()-1);
        }
    }
    
    public int getOnFridayCampus(){
        if(fridayCampusIndex.isEmpty()==true){
            return -1;
        }
        else{
            return fridayCampusIndex.get(fridayCampusIndex.size()-1);
        }
    }
    
    public int getOnSaturdayCampus(){        
        if(saturdayCampusIndex.isEmpty()==true){            
            return -1;
        }
        else{
            return saturdayCampusIndex.get(saturdayCampusIndex.size()-1);
        }
    }
    
    private void insertPrefernces(){
        //insert a record into the database with a login information along with prefernces  
        getDatabaseConnection();
        try{
            String queryTwo = "insert into LOGIN(USERID,PASSWORD,USERTYPE) values(?,?,?)";
            PreparedStatement stmtTwo = null;
            stmtTwo = conn.prepareStatement(queryTwo);
            stmtTwo.setString(1, this.instructorId);
            stmtTwo.setString(2,"1234");
            stmtTwo.setString(3, "P");
            stmtTwo.executeUpdate();
            stmtTwo.close();
            conn.close();
            //System.out.println("added prof");
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
        
        getDatabaseConnection();
        PreparedStatement stmt = null;
        
        try{
           String query = "insert into PREFERENCES(INSTRUCTOR,USERID,DEPARTMENT,SECTIONS,NORTHCAMPUS,SOUTHCAMPUS,WESTCAMPUS,EASTCAMPUS,WEEKEND) values(?,?,?,?,?,?,?,?,?)";
           stmt = conn.prepareStatement(query);
           stmt.setString(1, this.instructorName);
           stmt.setString(2, this.instructorId);
           stmt.setString(3, this.department);           
           stmt.setInt(4, this.numSectionsToTeach);
           stmt.setString(5, this.northCampus);
           stmt.setString(6, this.southCampus);
           stmt.setString(7, this.westCampus);
           stmt.setString(8, this.eastCampus);
           stmt.setString(9, this.weekend);
           stmt.executeUpdate();
              //try (Statement stmt = conn.createStatement()) {
              //  String sqlStatement = " INSERT INTO PREFERENCES " +"(DEPARTMENT,INSTRUCTOR,SECTIONS,NORTHCAMPUS,SOUTHCAMPUS,WESTCAMPUS,EASTCAMPUS,WEEKEND)"+
              //          "VALUES ('" +this.department+"' , '"+this.instructorName+"', "+this.numSectionsToTeach+", '"+this.northCampus+"', '"+this.southCampus+"', '"+this.westCampus+"', '"+this.eastCampus+"', '"+this.weekend+"')";
              //  stmt.executeUpdate(sqlStatement);
             
           // }//end try with resources
           stmt.close(); 
           conn.close();
            
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
      
    }//end insertPrefences
    protected void getDatabaseConnection(){
      //creates a connection to the database
        try{
         
          conn = DriverManager.getConnection(DB_URL,"userid","userid");
      }//end try
      catch (Exception e){
        System.out.println("could not make connection to database");
        System.out.println(e.getMessage());
      }//end catch
   }//end detDatabaseConnection
    
    
}
