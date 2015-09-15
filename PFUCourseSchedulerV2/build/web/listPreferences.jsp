<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <title>PFU Course Scheduler: List All Professors</title>
    <jsp:include page="includes/head.jsp" />    
    <body>
        <jsp:include page="includes/template.jsp" />    
        
                
<% request.getSession(true); %>
<% if (session.getAttribute("usertype").toString().equalsIgnoreCase("A")) {%>
                <h2>All professors</h2>

                <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Instructor Name</th>
                            <th>Dept</th>
                            <th>Sec.</th>
                            <th>N?</th>
                            <th>S?</th>
                            <th>E?</th>
                            <th>W?</th>
                            <th>Sat?</th>
                            <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${preferences}" var="pref">
                <tr>
                    <td><c:out value="${pref.department}" /></td>
                    <td><c:out value="${pref.instructor}" /></td>
                    <td><c:out value="${pref.sections}" /></td>
                    <td><c:out value="${pref.northcampus}" /></td>
                    <td><c:out value="${pref.southcampus}" /></td>
                    <td><c:out value="${pref.eastcampus}" /></td>
                    <td><c:out value="${pref.westcampus}" /></td>
                    <td><c:out value="${pref.weekend}" /></td>
                    <td><a href="PreferencesController?action=edit&name=<c:out value="${pref.instructor}"/>&dpt=<c:out value="${pref.department}"/>">Update</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<% } else if(session.getAttribute("usertype").toString().equalsIgnoreCase("P")) { %>
<h2>Display Preferences</h2>
    

                <p>
        Department : <c:out value="${preferences.department}" /> <br /> 
        Instructor : <c:out value="${preferences.instructor}" /> <br /> 
        Sections : <c:out value="${preferences.sections}" /> <br /> 
        <br /> 
        Northcampus? : <c:out value="${preferences.northcampus}" /> <br /> 
        Southcampus? : <c:out value="${preferences.southcampus}" /> <br /> 
        Eastcampus? : <c:out value="${preferences.eastcampus}" />  <br /> 
        Westcampus? : <c:out value="${preferences.westcampus}" /><br /> 
        Weekend? : <c:out value="${preferences.weekend}" /> <br /> 
        <a href="PreferencesController?action=edit&name=<c:out value="${preferences.instructor}"/>&dpt=<c:out value="${preferences.department}"/>">Update</a>
                </p>
                <% } else { %>
                <p>Sorry, it doesn't look like you have permission to view this information</p>
                <% } %>
</div>
        </div>
    </body>
</html>