<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : scheduleView
    Created on : Apr 28, 2015, 1:54:51 PM
    Author     : scroniser
--%>
<sql:query var="sched" dataSource="jdbc/courseScheduler">
    SELECT * FROM Schedule
</sql:query>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PFU Course Scheduler: View Schedule</title>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="stylesheet" type="text/css" href="css/dataTables.responsive.css"/>
        <script language="javascript" type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script language="javascript" type="text/javascript" src="js/dataTables.responsive.js"></script>
        <script language="javascript" type="text/javascript">
            $(document).ready(function() {
            $('#responsive-table').DataTable({
                "lengthMenu": [[100,250,500,750,-1], [100,250,500,750,"All"]]
            }
                    ); 
            } );
        </script> 
    </head>
    <body>
        <jsp:include page="includes/template.jsp" />  
        
                <h2>View Schedule
                </h2>

                <table id="responsive-table" class="display responsive no-wrap" cellspacing="0" width="100%">
                    <!-- column headers -->

                    <!-- column data -->
                    <thead>
                        <tr>
                            <th>Call Num</th>
                            <th>Building</th>
                            <th>Room</th>
                            <th>Campus</th>
                            <th>Instructor</th>
                        </tr>
                    </thead>
                    <c:forEach var="sched" items="${sched.rows}">
                        <tr>
                            <td>
                                ${sched.Callnumber}

                            </td>
                            <td>${sched.Building}</td>
                            <td>${sched.Room}</td>
                            <td>${sched.Campus}</td>
                            <td>${sched.Instructor}</td>
                        </tr>
                    </c:forEach>
            </div>
        </div>
    </body>
</html>

