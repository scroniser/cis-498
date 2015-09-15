<%-- 
    Document   : head
    Created on : May 19, 2015, 7:57:19 PM
    Author     : scroniser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="stylesheet" type="text/css" href="css/dataTables.responsive.css"/>
        <link href="http://fonts.googleapis.com/css?family=Slabo+27px" rel="stylesheet" type="text/css">
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
