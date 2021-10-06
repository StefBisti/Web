

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Videos</title>
</head>
<body>

	<%
		if(session.getAttribute("username") == null){
			response.sendRedirect("login.jsp");
		}
	%>


	Here are your videos, ${username}!
</body>
</html>