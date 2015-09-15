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

/**
 *
 * @author Andres
 */
public class UpdateSectionRoomDAO {
    
    protected final String DB_URL = "jdbc:derby://localhost:1527/Test";
    
    //This method updates the room and building for a particular section in the database.
    //The call number is used to filter on the section.
    //The method returns the number of rows affected by the database update.
    public int UpdateSectionRoomDAO (Long callNumber, Short room, String building) throws SQLException {
        
        int rowsAffected = 0;
        String sqlQuery = "UPDATE USERID.SCHEDULE SET ROOM = ?, BUILDING = ? WHERE CALLNUMBER = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_URL, "userid", "userid");
            ps = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setShort(1, room);
            ps.setString(2, building);
            ps.setLong(3, callNumber);
            rowsAffected = ps.executeUpdate();
            System.out.println("Schedule updated with new room!");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            ps.close();
            conn.close();
        }
        
        return rowsAffected;
    }
}
