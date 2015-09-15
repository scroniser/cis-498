<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Edit preferences</title>

<jsp:include page="includes/head.jsp" /> 
</head>
    <body>
        <jsp:include page="includes/template.jsp" />    
    
                <h2>Change Preferences</h2>
    

    <form method="POST" action='PreferencesController' name="frmAddPreferences">
        Department : <c:out value="${preferences.department}" /><input type="hidden" name="department"
            value="<c:out value="${preferences.department}" />" /> <br /> 
        Instructor : <c:out value="${preferences.instructor}" /><input
            type="hidden" name="instructor"
            value="<c:out value="${preferences.instructor}" />" /> <br /> 
        Sections : <input
            type="text" name="sections"
            value="<c:out value="${preferences.sections}" />" /> <br /> 
        <br /> 
        Northcampus? : <input type="text" name="northcampus"
            value="<c:out value="${preferences.northcampus}" />" /> <br /> 
        Southcampus? : <input type="text" name="southcampus"
            value="<c:out value="${preferences.southcampus}" />" /> <br /> 
        Eastcampus? : <input type="text" name="eastcampus"
            value="<c:out value="${preferences.eastcampus}" />" /> <br /> 
        Westcampus? : <input type="text" name="westcampus"
            value="<c:out value="${preferences.westcampus}" />" /><br /> 
        Weekend? : <input type="text" name="weekend"
            value="<c:out value="${preferences.weekend}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</div>
        </div>
    </body>
</html>