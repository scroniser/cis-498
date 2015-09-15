package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDao;
import java.sql.Time;
import java.util.List;
import model.Sections;

public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String SEARCH = "/search.jsp";
    private static String LIST_RESULTS = "/searchResults.jsp";
    private SearchDao dao;

    public SearchController() {
        super();
        dao = new SearchDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        forward = SEARCH;
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("searchselect");
        if (action.equalsIgnoreCase("callnumber")){
            Long classnumber = Long.parseLong(request.getParameter("textEntry"));
            List<Sections> sections = dao.getSectionByNum(classnumber);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else if (action.equalsIgnoreCase("department")){
            String department = request.getParameter("textEntry");
            List<Sections> sections = dao.getSectionByDpt(department);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else if (action.equalsIgnoreCase("coursenumber")){
            Long coursenumber = Long.parseLong(request.getParameter("textEntry"));
            List<Sections> sections = dao.getSectionByCourseNum(coursenumber);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else if (action.equalsIgnoreCase("days")){
            String days = request.getParameter("textEntry");
            List<Sections> sections = dao.getSectionByDays(days);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else if (action.equalsIgnoreCase("starttime")){
            Time starttime = java.sql.Time.valueOf(request.getParameter("textEntry"));
            List<Sections> sections = dao.getSectionByStart(starttime);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
           
        } else if (action.equalsIgnoreCase("endtime")){
            Time endtime = java.sql.Time.valueOf(request.getParameter("textEntry"));
            List<Sections> sections = dao.getSectionByEnd(endtime);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else if (action.equalsIgnoreCase("mediarequired")){
            String mediarequired = request.getParameter("textEntry");
            List<Sections> sections = dao.getSectionByMedia(mediarequired);
            forward = LIST_RESULTS;
            request.setAttribute("listings", sections);
            
        } else {
            
            RequestDispatcher view = request.getRequestDispatcher(SEARCH);
            view.forward(request, response);
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
        
    }
}