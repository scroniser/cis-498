package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PreferencesDao;
import dao.RoomDao;
import model.Preferences;
import dao.UnscheduledCoursesDao;
import javax.servlet.http.HttpSession;
        

public class UnscheduledCoursesController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/preferences.jsp";
    private static String LIST_UNSCHEDULED = "/listUnscheduled.jsp";
    private static String SELECT_ROOM = "/unscheduledDisplayAllRooms.jsp";
    private static String SELECT_PROF = "/unscheduledDisplayAllInstructors.jsp";
    private static String RESCHEDULE = "/displayUpdatedSchedule.jsp";
    private UnscheduledCoursesDao dao;
    private PreferencesDao prefDao;
    private RoomDao roomDao;

    public UnscheduledCoursesController() {
        super();
        dao = new UnscheduledCoursesDao();
        roomDao = new RoomDao();
        prefDao = new PreferencesDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);

        if (action.equalsIgnoreCase("delete")){
            String name = request.getParameter("name");
            String dpt = request.getParameter("dpt");
            dao.deletePreferences(name, dpt);
            forward = LIST_UNSCHEDULED;
            request.setAttribute("unscheduled", dao.getUnscheduledCourses());    
        }
        else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            String instructor = request.getParameter("name");
            String department = request.getParameter("dpt");
            Preferences preferences = dao.getPreferencesById(instructor, department);
            request.setAttribute("preferences", preferences);
        }
        else if (action.equalsIgnoreCase("listUnscheduled")){
            forward = LIST_UNSCHEDULED;
            request.setAttribute("unscheduled", dao.getUnscheduledCourses());
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
        else {
            forward = LIST_UNSCHEDULED;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Preferences preference = new Preferences();
        preference.setDepartment(request.getParameter("department"));
        preference.setInstructor(request.getParameter("instructor"));
        preference.setSections(request.getParameter("sections"));
        preference.setNorthcampus(request.getParameter("northcampus"));
        preference.setSouthcampus(request.getParameter("southcampus"));
        preference.setEastcampus(request.getParameter("eastcampus"));
        preference.setWestcampus(request.getParameter("westcampus"));
        preference.setWeekend(request.getParameter("weekend"));
        String instructor = request.getParameter("instructor");
        String department = request.getParameter("department");
        if(instructor == null || instructor.isEmpty())
        {
            dao.addPreferences(preference);
        }
        else
        {
            preference.setInstructor(instructor);
            preference.setDepartment(department);
            dao.updatePreferences(preference);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_UNSCHEDULED);
        request.setAttribute("preferences", dao.getUnscheduledCourses());
        view.forward(request, response);
    }
}