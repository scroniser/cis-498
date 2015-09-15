<%-- 
    Document   : template
    Created on : May 19, 2015, 8:08:23 PM
    Author     : scroniser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="container">
    <div id="tools"><div id="tools-links"><ul><li><a href="UserLogout.jsp">Logout</a></li></ul></div></div>
    
    <div id="header">
        
        <div id="logo">
            <img src="img/pfu-shield.png"/>
        </div>
        <div id="logo-text">
            <h1>PFU Course Scheduling System</h1>
        </div>
         
    </div>
    <div id="nav-wrap">
        <div id="nav">
            <ul>
                <% request.getSession(true); %>
                <% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                <li><a href="PreferencesController?action=listPreferences">Access admin to edit prof pref</a></li>
                <li><a href="ScheduleController?action=listSchedule">View Schedule</a></li>
                <li><a href="UnscheduledCoursesController?action=listUnscheduled">View Unscheduled Courses</a></li>
                <li><a href="RoomsController?action=listRooms">List Room Chart</a></li>
                <li><a href="SearchController?action=Search">Search for courses</a></li>
                <% } else if (session.getAttribute("usertype").toString().equalsIgnoreCase("P")){ %>
                <li><a href="SearchController?action=Search">Search for courses</a></li>
                <li><a href="PreferencesController?action=specificProf&userid=${userid}">View Preferences</a></li>
                <li><a href="ScheduleController?action=specificProf&userid=${userid}">View Schedule</a></li>
                <% } else {%>
                <li><br /></li>
                <% } %>
            </ul>
        </div>
    </div>