<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>login</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
 	<script src="index.js"></script>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	
	<div class="topnav">
		<a href="home.jsp">Home</a>
		<a href="blogs.jsp">Blogs</a>
		<a href="signup.jsp" style="float: right">Sign up</a>
		<a class="active" href="login.jsp" style="float: right">Log in</a>
	</div>
	
	<div style="padding-left:16px">
		<h2>This is the login page</h2>
		<p>accessible without login</p>
	</div>
	<br><br>
	<div style="padding-left: 30px">
		<form>
			Enter your username: <input type="text" name="username" id="username" maxlength="20">
			<p style="color: red;margin: 0 0;position: absolute;" id="usernameProblem"></p>
			
			<br><br><br><br>
			
			Enter your password: <input type="text" name="password" id="password" maxlength="20">
			<p style="color: red;margin: 0 0;position: absolute;" id="passwordProblem"></p>
			
			<br><br><br><br>
				
			<input type="button" value="log in" onclick="Login()">
		</form>
	</div>
	
	<div style="padding-left: 30px; padding-top: 30px">
		<c:if test="${username == null}">
			You are not logged in!
		</c:if>
		<c:if test="${username != null}">
			You are logged in as ${username}!
			<br>
			Hello, ${username}!
		</c:if>
	</div>
	
	<c:if test="${username != null}">
		<div style="padding-left: 30px; padding-top: 30px">
			<form action="logout" method="post">
				<input type="submit" value="log out">
			</form>
		</div>
	</c:if>
</body>
</html>