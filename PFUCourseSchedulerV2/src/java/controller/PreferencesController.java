package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PreferencesDao;
import javax.servlet.http.HttpSession;
import model.Preferences;

public class PreferencesController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/preferences.jsp";
    private static String LIST_PREFERENCES = "/listPreferences.jsp";
    private PreferencesDao dao;

    public PreferencesController() {
        super();
        dao = new PreferencesDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        
        if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            String instructor = request.getParameter("name");
            String department = request.getParameter("dpt");
            Preferences preferences = dao.getPreferencesById(instructor, department);
            request.setAttribute("preferences", preferences);
        } else if (action.equalsIgnoreCase("listPreferences")){
            forward = LIST_PREFERENCES;
            request.setAttribute("preferences", dao.getAllPreferences());
        } else if(action.equalsIgnoreCase("specificProf")){
            forward = LIST_PREFERENCES;
            String userid = request.getParameter("userid");
            Preferences preferences = dao.specificProfPreferences(userid);
            request.setAttribute("preferences", preferences);
        } else {
            forward = LIST_PREFERENCES;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Preferences preference = new Preferences();
        RequestDispatcher view;
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
        HttpSession session = request.getSession(true);
        if(session.getAttribute("usertype").toString().equalsIgnoreCase("P") && session.getAttribute("userid") != null){
            view = request.getRequestDispatcher(LIST_PREFERENCES);
            String userid = session.getAttribute("userid").toString();
            Preferences preferences = dao.specificProfPreferences(userid);
            request.setAttribute("preferences", preferences);
        } else {
            view = request.getRequestDispatcher(LIST_PREFERENCES);
            request.setAttribute("preferences", dao.getAllPreferences());
        }
        
        view.forward(request, response);
    }
}