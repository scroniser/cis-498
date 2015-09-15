<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>PFU Course Scheduler: Search</title>
<jsp:include page="includes/head.jsp" /> 
</head>
    <body>
        <jsp:include page="includes/template.jsp" />    
    
                <h2>Search</h2>        

    <form method="POST" action='SearchController' name="frmAddPreferences">
        <p>
            <select name="searchselect">
                <option value="callnumber">Call Number</option>
                <option value="department">Department</option>
                <option value="coursenumber">Course Number</option>
                <option value="days">Days</option>
                <option value="starttime">Start Time</option>
                <option value="endtime">End Time</option>
                <option value="mediarequired">Media Required</option>                
            </select>
            
            <input type="text" name="textEntry"
            value="Search" /> <br /> 
            <br /> 
            <button type="submit" name="submit">Submit</button></p>
    </form>
            </div>
        </div>
    </body>
</html>