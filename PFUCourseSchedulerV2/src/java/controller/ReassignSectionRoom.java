/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GetInstructorAssignmentsDAO;
import dao.GetScheduledByCallNumberDAO;
import dao.GetSingleRoomDAO;
import dao.GetTimeDistanceMatrixDAO;
import dao.UpdateSectionRoomDAO;

import model.Room;
import model.Schedule;

import util.TimeDistanceConflicts;

import java.sql.*;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andres
 */
public class ReassignSectionRoom extends HttpServlet {
    
    private GetScheduledByCallNumberDAO gsbcnDAO;
    private GetInstructorAssignmentsDAO giaDAO;
    private GetSingleRoomDAO gsrDAO;
    private GetTimeDistanceMatrixDAO gtdmDAO;
    private UpdateSectionRoomDAO usrDAO;
    
    public ReassignSectionRoom() {
        
        super();
        //Instantiate the DAO classes to make the database calls
        gsbcnDAO = new GetScheduledByCallNumberDAO();
        giaDAO = new GetInstructorAssignmentsDAO();
        gsrDAO = new GetSingleRoomDAO();
        gtdmDAO = new GetTimeDistanceMatrixDAO();
        usrDAO = new UpdateSectionRoomDAO();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        //Get user type to determine if user is an administrator
        String userType = (String)session.getAttribute("utype");
        //Get the call number of the section we are trying to assign/reassign
        String callNum = request.getParameter("callNum");
        System.out.println(callNum);
        //Get the new building and room selected by the admin
        String selectedBuilding = request.getParameter("building");
        Short selectedRoom = Short.parseShort(request.getParameter("room"));
        //URL that we will redirect to after method is complete
        String redirectURL = "";
        
//        if (userType.equalsIgnoreCase("a")) {
            //Get schedule information for section
            //TODO is this a request parameter or a session parameter?
            Schedule schedule = new Schedule();
            try {
                schedule = gsbcnDAO.GetScheduledByCallNumberDAO(Short.parseShort(callNum));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(schedule);
//            schedule.setCallNumber(Integer.parseInt(request.getParameter("callNumber")));
//            schedule.setCourseNumber(Integer.parseInt(request.getParameter("courseNumber")));
//            schedule.setDepartment(request.getParameter("department"));
//            schedule.setDays(request.getParameter("days"));
//            schedule.setStartTime(request.getParameter("startTime"));
//            schedule.setEndTime(request.getParameter("endTime"));
//            schedule.setInstructor(request.getParameter("instructor"));
//            schedule.setRoom(Integer.parseInt(request.getParameter("room")));
//            schedule.setBuilding(request.getParameter("building"));
            
            //Set up variables to hold instructor sections, the current room and the newly selected room
            ArrayList<Schedule> instructorAssignmentList = new ArrayList<Schedule>();
            Room currentRoom = new Room();
            Room newRoom = new Room();

            try {
                instructorAssignmentList = giaDAO.GetInstructorAssignmentsDAO(schedule.getInstructor());
                currentRoom = gsrDAO.GetSingleRoomDAO(schedule.getBuilding(), schedule.getRoom());
                newRoom = gsrDAO.GetSingleRoomDAO(selectedBuilding, selectedRoom);
                
                //If the campus of the new room is the same as the campus of the old room
                if (currentRoom.getCampus().equals(newRoom.getCampus())) {
                    System.out.println("Rooms are on the same campus.");
                    //Update the section room in the Schedule table of the database with the new room
                    int success = usrDAO.UpdateSectionRoomDAO(schedule.getCallnumber(), newRoom.getRoom(), newRoom.getDepartment());
                    //Only one row in the Schedule table should be affected
                    if ( success == 1 ) {
                        //TODO what is the redirect url?
                        System.out.println("1 row successfully updated in Schedule table.");
                        request.setAttribute("updatedSchedule", gsbcnDAO.GetScheduledByCallNumberDAO(Short.parseShort(callNum)));
                        redirectURL = "";
                    }//end if campus is the same and we have successfully updated the Schedule table in the database
                    else {
                        System.out.println("0 or more than 1 row updated in Schedule table.");
                        redirectURL = "";
                    }//end else
                }
                //Otherwise, if the campuses are different
                else {
                    System.out.println("Rooms are on different campuses.");
                    TimeDistanceConflicts util = new TimeDistanceConflicts();
                    
                    //Check to see if there are any conflicts 
                    int conflictCounter = util.TimeDistanceConflicts(schedule, newRoom, instructorAssignmentList, gtdmDAO.GetTimeDistanceMatrixDAO(newRoom.getCampus()));
                    //If there are any conflicts do not update the Schedule database
                    if ( conflictCounter > 0 ) {
                        System.out.println("There was 1 or more time conflicts. Section not reassigned to new room.");
                        redirectURL = "";
                    }
                    else {
                        int success;
                        success = usrDAO.UpdateSectionRoomDAO(schedule.getCallnumber(), newRoom.getRoom(), newRoom.getDepartment());
                        //Only one row in the Schedule table should be affected
                        if ( success == 1 ) {
                            //TODO what is the redirect url?
                            System.out.println("1 row successfully updated in Schedule table.");
                            request.setAttribute("updatedSchedule", gsbcnDAO.GetScheduledByCallNumberDAO(Short.parseShort(callNum)));
                            redirectURL = "";
                            //TODO do I need to pass any parameters?
                        }//end if campus is the same and we have successfully updated the Schedule table in the database
                        else {
                            System.out.println("0 or more than 1 row updated in Schedule table.");
                            redirectURL = "";
                        }
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
//        }
//        else {
//            System.out.println("You do not have administrator privileges.");
//            redirectURL = "";
//        }
        RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
