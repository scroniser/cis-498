<%-- 
    Document   : logout
    Created on : May 7, 2015, 12:14:33 AM
    Author     : Andres
--%>

<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>