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
import java.util.ArrayList;
import model.Schedule;

/**
 *
 * @author Andres
 */
public class GetInstructorAssignmentsDAO {
    
    protected final String DB_URL = "jdbc:derby://localhost:1527/Test";
    
    //This method gets all of the classes assigned to a single instructor.
    //The method returns an ArrayList of Section objects to the calling method.
    public ArrayList<Schedule> GetInstructorAssignmentsDAO(String instructor) throws SQLException {
        
        ArrayList<Schedule> instructorAssignmentList = new ArrayList<Schedule>();
        String sqlQuery = "SELECT * FROM USERID.SCHEDULE WHERE INSTRUCTOR = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

    try {
        //Class.forName("org.apache.derby.jdbc.ClientDriver");
        conn = DriverManager.getConnection(DB_URL, "userid", "userid");
        ps = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, instructor);
        rs = ps.executeQuery();
    
        if ( !rs.isBeforeFirst() ) {
            //TODO sendRedirect or RequestDispatcher
            System.out.println("No data");
        }
        else {
            System.out.println("Instructor found!");
            while ( rs.next() ) {
                Schedule schedule = new Schedule();
                schedule.setCallnumber(rs.getLong("CALLNUMBER"));
                schedule.setCoursenumber(rs.getLong("COURSENUMBER"));
                schedule.setDepartment(rs.getString("DEPARTMENT"));
                schedule.setDays(rs.getString("DAYS"));
                schedule.setStartime(rs.getTime("STARTIME"));
                schedule.setEndtime(rs.getTime("ENDTIME"));
                schedule.setInstructor(rs.getString("INSTRUCTOR"));
                schedule.setRoom(rs.getShort("ROOM"));
                schedule.setBuilding(rs.getString("BUILDING"));
                instructorAssignmentList.add(schedule);
                System.out.println("Scheduled section added to list.");
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

    return instructorAssignmentList;
    }
}
