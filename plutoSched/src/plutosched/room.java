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
import static plutosched.professor.conn;

/**
 *
 * @author Matt
 */
public class room implements Comparable {
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    static Connection conn;
    
    private String building, campus;
    private boolean mediaAvailable;
    private int room,capacity,campusIndex;
    private ArrayList<Integer> monday=new ArrayList();
    private ArrayList<Integer> tuesday=new ArrayList();
    private ArrayList<Integer> wednesday=new ArrayList();
    private ArrayList<Integer> thursday=new ArrayList();
    private ArrayList<Integer> friday=new ArrayList();
    private ArrayList<Integer> saturday=new ArrayList();
    private static databaseFunctions logs=new databaseFunctions();
    
    
    public room(int room, String building, int capacity, String campus, String media){
        //This creates the standard room class
        this.room=room;
        this.capacity=capacity;
        this.campus=campus.toUpperCase();
        this.building=building;
        if("YES".equals(media.toUpperCase())){
            this.mediaAvailable=true;
        } 
        else{
            this.mediaAvailable=false;
        }
        insertRoom();
        
    }
    
    private void insertRoom(){
        //this inserts a room record into the database
        getDatabaseConnection();
        PreparedStatement stmt = null;
        try{
           String query = "insert into ROOMS(DEPARTMENT,ROOM,CAMPUS,CAPACITY,MEDIAAVAILABLE) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.building);
            stmt.setInt(2, this.room);
            stmt.setString(3, this.campus);
            stmt.setInt(4, this.capacity);
            stmt.setBoolean(5, this.mediaAvailable);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
//            try (Statement stmt = conn.createStatement()) {
//                String sqlStatement = " INSERT INTO ROOMS " +"(DEPARTMENT,ROOM,CAMPUS,CAPACITY,MEDIAAVAILABLE)"+
//                        "VALUES ('" +this.building+"' , "+this.room+", '"+this.campus+"', "+this.capacity+", '"+this.mediaAvailable+"')";
//                stmt.executeUpdate(sqlStatement);
//                //System.out.println("added room");
//            }//end try with resources
//            conn.close();
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }
    
    public String getCampus(){
        return campus;
    }
    public int getCampusIndex(){
        switch (this.campus.toUpperCase()) {
            case "NORTH":
                campusIndex=0;
                break;
            case "SOUTH":
                campusIndex=1;
                break;
            case "EAST":
                campusIndex=2;
                break;
            case "WEST":
                campusIndex=3;
                break;
            default:
                //log a error
        }
        
        return campusIndex;
    }
    
    public String getBuilding() {
        return building;
    }

    public boolean isMediaAvailable() {
        return mediaAvailable;
    }

    public int getRoom() {
        return room;
    }

    public int getCapacity() {
        return capacity;
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
        }else{
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

    @Override
    public int compareTo(Object roomSeats) {
        //this allows the array of objects to sort in ascending order
        int totSeats=((room)roomSeats).getCapacity();
        //sort ascending order
        return this.capacity-totSeats;
    }
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
