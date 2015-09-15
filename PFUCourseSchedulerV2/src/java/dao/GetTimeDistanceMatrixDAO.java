/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.TimeDistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Andres
 */
public class GetTimeDistanceMatrixDAO {
    
    protected final String DB_URL = "jdbc:derby://localhost:1527/ScheduleSystem";
    
    public TimeDistance GetTimeDistanceMatrixDAO(String newRoomCampus) throws SQLException {
        
        TimeDistance timeDistanceMatrix = new TimeDistance();
        String sqlQuery = "SELECT * FROM USERID.DISTANCES WHERE CAMPUS = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_URL, "userid", "userid");
            ps = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, newRoomCampus);
            rs = ps.executeQuery();
    
            if ( !rs.isBeforeFirst() ) {
                System.out.println("No time distance data");
            }
            else {
                //Get the row that contains the time distances of the campus of the new room
                System.out.println("Campus row of new room found!");
                while ( rs.next() ) {
                    timeDistanceMatrix.setCampus(rs.getString("CAMPUS"));
                    timeDistanceMatrix.setNorthDistance(rs.getDouble("NORTHDISTANCE"));
                    timeDistanceMatrix.setSouthDistance(rs.getDouble("SOUTHDISTANCE"));
                    timeDistanceMatrix.setEastDistance(rs.getDouble("EASTDISTANCE"));
                    timeDistanceMatrix.setWestDistance(rs.getDouble("WESTDISTANCE"));
                    System.out.println("Time distance matrix for campus of new room completed.");
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
        
        return timeDistanceMatrix;
    }
}
