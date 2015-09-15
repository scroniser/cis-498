<%-- 
    Document   : index
    Created on : May 4, 2015, 11:42:21 PM
    Author     : Andres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="stylesheet" type="text/css" href="css/dataTables.responsive.css"/>
        <link href="http://fonts.googleapis.com/css?family=Slabo+27px" rel="stylesheet" type="text/css">
        
        <link rel="stylesheet" href="http://jqueryvalidation.org/files/demo/site-demos.css">
    </head>
    <body class="login">
        <div id="login-container">
            <div id="login-header">
                <div id="login-logo">
                     <img src="img/pfu-shield.png"/>
                </div>
                <div id="login-logo-text">
                    <h1>PFU Course Scheduling System</h1>
                </div>
            </div>
            <div id="login-content">
                <form id="myform" action="UserLogin.jsp" method="POST">
                    <label for="uname">Username: </label>&#160;<input id="uname" name="uname">
                    <br/>
                    <label for="password">Password:</label>&#160;&#160;<input type="password" name="password" id="password"/>
                    <br/>
                    <%
                        if ( session.getAttribute("errmsg") != null ) {
                    %>
                            <h7><%= session.getAttribute("errmsg") %></h7>
                    <%
                            session.invalidate();
                        }
                    %>
                    <input type="submit" value="Submit">
                </form>
                <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
                <script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
                <script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
                <script>jQuery.validator.setDefaults({debug:false,success:"valid"});$("#myform").validate({rules:{password:"required",uname:"required"}});</script>
            </div>
        </div>
    </body>
</html>
