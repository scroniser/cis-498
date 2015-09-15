/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plutosched;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 *
 * @author Matt
 */
public class databaseFunctions {
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    static Connection conn;
    private static int eventID=1;
    
    
    public void logEvent(String event) {
        
        Timestamp timeStamp = Timestamp.valueOf(LocalDateTime.now());
        getDatabaseConnection();
        PreparedStatement stmt = null;
        
        try{
            
            
            String query = "insert into EVENTLOG(LOGID,DATETIME,LOGITEM) values(?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, databaseFunctions.eventID);
            stmt.setTimestamp(2, timeStamp);
            stmt.setString(3, event);
            stmt.executeUpdate();
            stmt.close();
            conn.close();            
            databaseFunctions.eventID++;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    protected void getDatabaseConnection(){
      try{         
          conn = DriverManager.getConnection(DB_URL,"userid","userid");
      }//end try
      catch (Exception e){
        System.out.println("could not make connection to database");
        System.out.println(e.getMessage());
      }//end catch
   }//end detDatabaseConnection
    
}
