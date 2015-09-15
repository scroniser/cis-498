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
import model.Schedule;
import model.Sections;
import dao.RoomDao;
import model.Rooms;
        

public class RoomsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String LIST_ROOMS = "/roomChart.jsp";
    private static String EDIT = "/editRoom.jsp";
    private RoomDao dao;
   

    public RoomsController() {
        super();
        dao = new RoomDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("edit")){
            forward = EDIT;
            String department = request.getParameter("department");
            Short room = Short.parseShort(request.getParameter("room"));
            Rooms rooms = dao.getSpecificRoom(department, room);
            request.setAttribute("rooms", rooms);
        } else if (action.equalsIgnoreCase("listRooms")){
            forward = LIST_ROOMS;
            request.setAttribute("rooms", dao.getAllRooms());
        } else {
            forward = LIST_ROOMS;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Rooms room = new Rooms();
        room.setDepartment(request.getParameter("department"));
        room.setRoom(Short.parseShort(request.getParameter("room")));
        room.setCampus(request.getParameter("campus"));
        room.setCapacity(Short.parseShort(request.getParameter("capacity")));
        room.setMediaavailable(Boolean.parseBoolean(request.getParameter("mediaavailable")));
        String department = request.getParameter("department");
        String specificRoom = request.getParameter("room");
        
        dao.updateRooms(room);
        
        RequestDispatcher view = request.getRequestDispatcher(LIST_ROOMS);
        request.setAttribute("rooms", dao.getAllRooms());
        view.forward(request, response);
    }

}