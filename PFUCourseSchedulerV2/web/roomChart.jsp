<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Show All Unscheduled Courses</title>
    <jsp:include page="includes/head.jsp" />    
    <body>
        <jsp:include page="includes/template.jsp" />    
        
                
                <h2>Room Chart</h2>

                <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Building</th>
                <th>Room Num</th>
                <th>Campus</th>
                <th>Capacity</th>
                <th>Media Avail</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${rooms}" var="roomChart">
                <tr>
                    <td><c:out value="${roomChart.department}" /></td>
                    <td><c:out value="${roomChart.room}" /></td>
                    <td><c:out value="${roomChart.campus}" /></td>
                    <td><c:out value="${roomChart.capacity}" /></td>
                    <td><c:out value="${roomChart.mediaavailable}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</div>
        </div>
    </body>
</html>