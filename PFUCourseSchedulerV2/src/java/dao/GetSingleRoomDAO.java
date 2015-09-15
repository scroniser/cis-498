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
import model.Room;

/**
 *
 * @author Andres
 */
public class GetSingleRoomDAO {
    
    protected final String DB_URL = "jdbc:derby://localhost:1527/Test";
    
    //This method gets a single room based on the building and room selected.
    //The method returns a single Room object.
    public Room GetSingleRoomDAO(String building, Short room) throws SQLException {
        
        Room singleRoom = new Room();
        String sqlQuery = "SELECT * FROM USERID.ROOMS WHERE DEPARTMENT = ? AND ROOM = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_URL, "userid", "userid");
            ps = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, building);
            ps.setInt(2, room);
            rs = ps.executeQuery();
    
            if ( !rs.isBeforeFirst() ) {
                System.out.println("No data");
            }
            else {
                while ( rs.next() ) {
                    System.out.println("Room found!");
                    singleRoom.setDepartment(rs.getString("DEPARTMENT"));
                    singleRoom.setRoom(rs.getShort("ROOM"));
                    singleRoom.setCampus(rs.getString("CAMPUS"));
                    singleRoom.setCapacity(rs.getShort("CAPACITY"));
                    singleRoom.setMediaAvailable(rs.getString("MEDIAAVAILABLE"));
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

        return singleRoom;
    }
}