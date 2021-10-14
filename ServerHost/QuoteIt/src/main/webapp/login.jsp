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
		<div id="upperUsername" onclick="upperUsernameClick()">
			${username}
			<c:if test="${userID == undefined}">
				Log in
				<script>
					$("div#upperUsername").css("color", "rgb(0, 128, 255)");
					$("div#upperUsername").css("font-weight", "500");
					$("div#upperUsername").attr("onclick", "window.location.href='login';");
				</script>
			</c:if>
		</div>
		<div class="dropdown" id="topDropdown">
			<button id="LogInButton" onclick="window.location.href='login';">Log in</button>
			<button id="LogOutButton" onclick="Logout()">Sign out</button>
		</div>
	</div>
	
	<div class="login-signup-container">
		<p class="title">Log in</p>
		
		<form id="loggingform" autocomplete="off">
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
	
	
	<script type="text/javascript">
		$(document).ready(function() {
		    $('input').keyup(function(event) {
		        if (event.which === 13) {
		            event.preventDefault();
		            Login();
		        }
		    });
		});
	</script>
</body>
</html>