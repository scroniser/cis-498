<%-- 
    Document   : displayPreferences
    Created on : May 22, 2015, 12:49:25 AM
    Author     : scroniser
--%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Display preferences</title>

<jsp:include page="includes/head.jsp" /> 
</head>
    <body>
        <jsp:include page="includes/template.jsp" />    
    <div id="logo-text">
                    <h1>PFU Course Scheduling System</h1>
                </div>
            </div>
            <div id="content">
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
                
</div>
        </div>
    </body>
</html>