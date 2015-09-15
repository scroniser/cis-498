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
                <% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                <th>Action</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sched" items="${schedule}">
                <tr>
                    <td><c:out value="${sched.callnumber}"/></td>
                    <td><c:out value="${sched.coursenumber}"/></td>
                    <td><c:out value="${sched.department}"/></td>
                    <td><c:out value="${sched.days}"/></td>
                    <td><c:out value="${sched.startime}"/></td>
                    <td><c:out value="${sched.endtime}"/></td>
                    <td><c:out value="${sched.instructor}"/></td>
                    <td><c:out value="${sched.room}"/></td>
                    <td><c:out value="${sched.building}"/></td>
                    <% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                    <td><a href="ScheduleController?action=selectRoom&callNumber=<c:out value="${sched.callnumber}"/>">Reassign</a></td>
                    <% } %>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</div>
        </div>
    </body>
</html>