<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
    <h1>Admin Page</h1>
    <a href="PreferencesController?action=listPreferences">Access admin to edit prof pref</a><br />
    <a href="ScheduleController?action=listSchedule">View Schedule</a><br />
    <a href="UnscheduledCoursesController?action=listUnscheduled">View Unscheduled Courses</a><br />
    <a href="RoomsController?action=listRooms">List Room Chart</a><br />
    <a href="SearchController?action=Search">Search for courses</a>
</body>
</html>