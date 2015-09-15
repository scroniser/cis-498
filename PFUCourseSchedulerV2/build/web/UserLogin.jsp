<%-- 
    Document   : UserLoginDB
    Created on : May 4, 2015, 10:24:21 PM
    Author     : Andres
--%>

<%@ page import ="java.sql.*" %>
<%
    String user = request.getParameter("uname").trim();    
    String pwd = request.getParameter("password").trim();
    String sql = "SELECT USERID, USERTYPE FROM USERID.LOGIN WHERE USERID = '" + user + "' AND PASSWORD = '" + pwd + "'";
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    String errmsg = "";

    try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/ScheduleSystem", "userid", "userid");
        st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(sql);
    
        if ( rs.next() ) {
            String usrtype = rs.getString("USERTYPE");
            session.setAttribute("userid", user);
            session.setAttribute("usertype", usrtype);
            if (usrtype.equalsIgnoreCase("A")) {
                request.setAttribute("userid", user);
            }
            
            response.sendRedirect("success.jsp");
            
        }
        else {
            errmsg = "Invalid username and/or password";
            session.setAttribute("errmsg", errmsg);
            response.sendRedirect("index.jsp");
        }
    }
    catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    rs.close();
    st.close();
    conn.close();
%>
