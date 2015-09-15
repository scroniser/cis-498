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
public class enroll {
    private int courseNum, numEnrolled;
    private String department;
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    static Connection conn;
    private static databaseFunctions logs=new databaseFunctions();
    public enroll(int courseNum, String department, int numEnrolled){
        //create the enrollment object
        this.courseNum=courseNum;
        this.department=department;
        this.numEnrolled=numEnrolled;
        insertEnrollment();
    }
    
    private void insertEnrollment(){
        //insert enrollment to database
        getDatabaseConnection();
        PreparedStatement stmt = null;
        try{
            String query = "insert into ENROLLMENT(DEPARTMENT,COURSENUMBER,NUMBERENROLLED) values(?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.department);
            stmt.setInt(2, this.courseNum);
            stmt.setInt(3, this.numEnrolled);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            
//            try (Statement stmt = conn.createStatement()) {
//                String sqlStatement = " INSERT INTO ENROLLMENT " +"(DEPARTMENT,COURSENUMBER,NUMBERENROLLED)"+
//                        "VALUES ('" +this.department+"' , "+this.courseNum+", "+this.numEnrolled+")";
//                stmt.executeUpdate(sqlStatement);
//                //System.out.println("added enrollment");
//            }//end try with resources
//            conn.close();
        }
        catch(Exception e){
            logs.logEvent(e.getMessage());
        }
    }
    
    public int getNumEnrolled(){
        return this.numEnrolled;
    }
    public int getCourseNum(){
        return this.courseNum;
    }
    public String getDepartment(){
        return this.department;
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
