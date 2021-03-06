<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Show All Search Results</title>
<jsp:include page="includes/head.jsp" /> 
</head>
    <body>
        <jsp:include page="includes/template.jsp" />    
        
                <h2>View search results</h2>

                <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Call Number</th>
                <th>Department</th>
                <th>Course Num</th>
                <th>Days</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Media Required</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listings}" var="courses">
                <tr>
                    <td><c:out value="${courses.callnumber}" /></td>
                    <td><c:out value="${courses.department}" /></td>
                    <td><c:out value="${courses.coursenumber}" /></td>
                    <td><c:out value="${courses.days}" /></td>
                    <td><c:out value="${courses.starttime}" /></td>
                    <td><c:out value="${courses.endtime}" /></td>
                    <td><c:out value="${courses.mediarequired}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</div>
        </div>
    </body>
</html>