<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="logging.css">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="logging.js"></script>
	<meta name="viewport" content= "user-scalable=no">
</head>
<body>
	<div class="header">
		<a href="home">Home</a>
		<a href="profile" id="upperUsername">
			${username}
			<c:if test="${username == undefined}">
				Log in
				<script>
					$("a#upperUsername").css("color", "rgb(0, 128, 255)");
					$("a#upperUsername").css("font-weight", "500");
					$("a#upperUsername").attr("href", "login");
				</script>
			</c:if>
		</a>
	</div>
	
	<div class="login-signup-container">
		<p class="title">Log in</p>
		
		<form autocomplete="off">
			<div class="fieldTitle">Enter your username</div>
			<input type="text" maxlength="20" id="usernameWR56YyOp0">
			<p class="fieldError" id="usernameProblem">Username is incorrect.</p>
			
			<div class="fieldTitle">Enter your password</div>
			<input type="password" maxlength="20" id="password">
			<p class="fieldError" id="passwordProblem">Password is incorrect.</p>
			
			<button type="button" class="submitButton" onclick="Login()">Log in</button>
			
			<div class="register"><a href="signup">Don't have an account? Make one!</a></div>
		</form>
		
	</div>
	
</body>
</html>