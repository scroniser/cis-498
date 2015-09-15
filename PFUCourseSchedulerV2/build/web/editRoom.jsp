<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Edit room</title>

<jsp:include page="includes/head.jsp" /> 
</head>
    <body>
        <jsp:include page="includes/template.jsp" />    
    <div id="logo-text">
                    <h1>PFU Course Scheduling System</h1>
                </div>
            </div>
            <div id="content">
                <h2>Change Room</h2>
    

    <form method="POST" action='PreferencesController' name="frmAddPreferences">
        Building : <c:out value="${rooms.department}" /><input type="hidden" name="department"
            value="<c:out value="${rooms.department}" />" /> <br /> 
        Room : <c:out value="${rooms.room}" /><input
            type="hidden" name="room"
            value="<c:out value="${rooms.room}" />" /> <br /> 
        Campus : <input
            type="text" name="campus"
            value="<c:out value="${rooms.campus}" />" /> <br /> 
        <br /> 
        Capacity : <input type="text" name="capacity"
            value="<c:out value="${rooms.capacity}" />" /> <br /> 
        Media Available : <input type="text" name="mediaavailable"
            value="<c:out value="${rooms.mediaavailable}" />" /> <br /> 
        <input
            type="submit" value="Submit" />
    </form>
</div>
        </div>
    </body>
</html>