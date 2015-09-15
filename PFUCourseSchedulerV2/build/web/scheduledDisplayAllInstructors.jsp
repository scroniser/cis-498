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
        
                
                
        <h2>Instructors</h2>
        <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Department</th>
                <th>Instructor</th>                
                <th>Sections</th>
                <th>North Campus</th>
                <th>South Campus</th>
                <th>West Campus</th>
                <th>East Campus</th>
                <th>Weekend</th>
                <% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                <th>Action</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="instructor" items="${instructors}">
                <tr>
                    <td><c:out value="${instructor.department}"/></td>
                    <td><c:out value="${instructor.instructor}"/></td>
                    <td><c:out value="${instructor.sections}"/></td>
                    <td><c:out value="${instructor.northcampus}"/></td>
                    <td><c:out value="${instructor.southcampus}"/></td>
                    <td><c:out value="${instructor.westcampus}"/></td>
                    <td><c:out value="${instructor.eastcampus}"/></td>
                    <td><c:out value="${instructor.weekend}"/></td>
                    <% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                    <td><a href="ScheduleController?action=reschedule&callNumber=<c:out value="${requestScope.callNumber}"/>&selectedRoomNum=<c:out value="${requestScope.selectedRoomNum}"/>&selectedBuilding=<c:out value="${requestScope.selectedBuilding}"/>&selectedInstructor=<c:out value="${instructor.instructor}"/>">Select</a></td>
                    <% } %>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</div>
        </div>
    </body>
</html>