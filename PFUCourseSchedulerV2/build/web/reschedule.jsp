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
        <title>PFU Course Scheduler: Reschedule</title>
    <jsp:include page="includes/head.jsp" />    
    <body>
        <jsp:include page="includes/template.jsp" />    
        
                
                
        <h2>Reschedule</h2>
        <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Department</th>
                <th>Room</th>
                <th>Campus</th>                
                <th>Capacity</th>
                <th>Media Available</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <td><c:out value="${room.department}"/></td>
                    <td><c:out value="${room.room}"/></td>
                    <td><c:out value="${room.campus}"/></td>
                    <td><c:out value="${room.capacity}"/></td>
                    <td><c:out value="${room.mediaavailable}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</div>
        </div>
    </body>
</html>