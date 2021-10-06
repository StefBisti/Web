<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>signup</title>
	<link rel="stylesheet" type="text/css" href="stylesheet.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="index.js"></script>   
               
</head>

<body>
	<div class="topnav">
		<a href="home.jsp">Home</a>
		<a href="blogs.jsp">Blogs</a>
		<a class="active" href="signup.jsp" style="float: right">Sign up</a>
		<a href="login.jsp" style="float: right">Log in</a>
	</div>
	
	<div style="padding-left:16px">
		<h2>This is the signup page</h2>
		<p>accessible without login</p>
	</div>
	
	<br><br>
	<div style="padding-left: 30px">
		<form>
			Choose an username: <input type="text" name="username" id="username" maxlength="20">
			<p style="color: red;margin: 0 0;position: absolute;" id="usernameProblem"></p>
			
			<br><br><br><br>
			
			Choose a password: <input type="text" name="password" id="password" maxlength="20">
			<p style="color: red;margin: 0 0;position: absolute;" id="passwordProblem"></p>
						
			<br><br><br><br>
			
			<input type="button" value="sign up" onclick="checkForSignUp()">
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
	
	
</body>
</html>