<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : schedule
    Created on : May 22, 2015, 8:34:16 AM
    Author     : src726
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PFU Course Scheduler: Schedule</title>
    <jsp:include page="includes/head.jsp" />    
    <body>
        <jsp:include page="includes/template.jsp" />    
        
                
                
        <h2>Schedule</h2>
        <% if ( request.getAttribute("schedule") != null ) {%>
            <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>Call Number</th>
                    <th>Course Number</th>
                    <th>Department</th>
                    <th>Days</th>                
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Instructor</th>
                    <th>Room</th>
                    <th>Building</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><c:out value="${schedule.callnumber}"/></td>
                    <td><c:out value="${schedule.coursenumber}"/></td>
                    <td><c:out value="${schedule.department}"/></td>
                    <td><c:out value="${schedule.days}"/></td>
                    <td><c:out value="${schedule.startime}"/></td>
                    <td><c:out value="${schedule.endtime}"/></td>
                    <td><c:out value="${schedule.instructor}"/></td>
                    <td><c:out value="${schedule.room}"/></td>
                    <td><c:out value="${schedule.building}"/></td>
                </tr>
                </tbody>
            </table>
        <% }
           else { %>
            <h2 style="color:red">This section could not be updated due to instructor and/or room conflicts.</h2>
        <% }%>
    
</div>
        </div>
    </body>
</html>