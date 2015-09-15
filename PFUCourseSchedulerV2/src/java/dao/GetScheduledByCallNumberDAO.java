/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Schedule;

/**
 *
 * @author Andres
 */
public class GetScheduledByCallNumberDAO {
    
    protected final String DB_URL = "jdbc:derby://localhost:1527/Test";
    
    public Schedule GetScheduledByCallNumberDAO(int callNumber) throws SQLException {
        
        Schedule scheduledSection = new Schedule();
        String sqlQuery = "SELECT * FROM USERID.SCHEDULE WHERE CALLNUMBER = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, "userid", "userid");
            ps = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, callNumber);
            rs = ps.executeQuery();

            if ( !rs.isBeforeFirst() ) {
                System.out.println("No data in Schedule.");
            }
            else {
                while ( rs.next() ) {
                    scheduledSection.setCallnumber(rs.getLong("CALLNUMBER"));
                    scheduledSection.setCoursenumber(rs.getLong("COURSENUMBER"));
                    scheduledSection.setDepartment(rs.getString("DEPARTMENT"));
                    scheduledSection.setDays(rs.getString("DAYS"));
                    scheduledSection.setStartime(rs.getTime("STARTIME"));
                    scheduledSection.setEndtime(rs.getTime("ENDTIME"));
                    scheduledSection.setInstructor(rs.getString("INSTRUCTOR"));
                    scheduledSection.setRoom(rs.getShort("ROOM"));
                    scheduledSection.setBuilding(rs.getString("BUILDING"));
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            rs.close();
            ps.close();
            conn.close();
        }
       return scheduledSection; 
    }
}
