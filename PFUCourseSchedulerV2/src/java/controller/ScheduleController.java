/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PreferencesDao;
import dao.RoomDao;
import dao.ScheduleDao;
import java.io.IOException;
import java.sql.Time;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author src726
 */
public class ScheduleController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String LIST_SCHEDULE = "/schedule.jsp";
    private static String RESCHEDULE = "/displayUpdatedSchedule.jsp";
    private static String SELECT_ROOM = "/scheduledDisplayAllRooms.jsp";
    private static String SELECT_PROF = "/scheduledDisplayAllInstructors.jsp";
    private static String ERROR = "/error.jsp";
    private PreferencesDao prefDao;
    private ScheduleDao dao;
    private RoomDao roomDao;
    
    public ScheduleController() {
        super();
        dao = new ScheduleDao();
        roomDao = new RoomDao();
        prefDao = new PreferencesDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        if(action.equalsIgnoreCase("listSchedule") && session.getAttribute("usertype").toString().equalsIgnoreCase("A")){
            forward = LIST_SCHEDULE;
            request.setAttribute("schedule", dao.getEntireSchedule());
        }
        else if(action.equalsIgnoreCase("specificProf")) {
            forward = LIST_SCHEDULE;
            String userid = request.getParameter("userid");
            request.setAttribute("schedule", dao.getProfSchedule(userid));
        }
        else if (action.equalsIgnoreCase("selectRoom") && session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {
            forward = SELECT_ROOM;
            request.setAttribute("callNumber", request.getParameter("callNumber"));
            request.setAttribute("rooms", roomDao.getAllRooms());
        }
        else if (action.equalsIgnoreCase("selectProf") && session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {
            forward = SELECT_PROF;
            request.setAttribute("callNumber", request.getParameter("callNumber"));
            request.setAttribute("selectedBuilding", request.getParameter("selectedBuilding"));
            request.setAttribute("selectedRoomNum", request.getParameter("selectedRoomNum"));
            request.setAttribute("instructors", prefDao.getAllPreferences());
        }
        else if(action.equalsIgnoreCase("reschedule") && session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {
            forward = RESCHEDULE;
            Long callNumber = Long.valueOf(request.getParameter("callNumber"));
            String selectedInstructor = request.getParameter("selectedInstructor");
            String selectedBuilding = request.getParameter("selectedBuilding");
            Short selectedRoomNum = Short.valueOf(request.getParameter("selectedRoomNum"));
            request.setAttribute("schedule", dao.doScheduleAssignment(callNumber, selectedInstructor, selectedBuilding, selectedRoomNum));
        }
        else{
            forward = "ERROR";
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           //todo:write stuff for post
       }
}
