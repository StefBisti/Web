<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>home</title>
<link rel="stylesheet" type="text/css" href="stylesheet.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	
	<div class="topnav">
		<a class="active" href="home.jsp">Home</a>
		<a href="blogs.jsp">Blogs</a>
		<a href="signup.jsp" style="float: right">Sign up</a>
		<a href="login.jsp" style="float: right">Log in</a>
	</div>
	
	<div style="padding-left:16px">
		<h2>This is the home page</h2>
		<p>accessible without login</p>
	</div>
	
	<div style="padding-left: 30px; padding-top: 30px">
		<c:if test="${username == null}">
			You are not logged in!
			<br>
			<a href="login.jsp">log in</a>
		</c:if>
		<c:if test="${username != null}">
			You are logged in as ${username}!
			<br>
			Hello, ${username}!
		</c:if>
	</div>
	
	<div class="content" style="margin: 0 auto; width: 80%; text-align: center">
		<h1>This is the home page</h1> <br>
		<h2>You can see this even if you are not logged in</h2> <br>
		
		<c:if test="${username != null}">
			<h2>But you are logged in, so hello, ${username}!</h2>
		</c:if>
	</div>
	
</body>
</html>