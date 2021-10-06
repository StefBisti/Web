<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>blogs</title>
<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div class="topnav">
		<a href="home.jsp">Home</a>
		<a class="active" href="blogs.jsp">Blogs</a>
		<a href="signup.jsp" style="float: right">Sign up</a>
		<a href="login.jsp" style="float: right">Log in</a>
	</div>
	
	<div style="padding-left:16px">
		<h2>This is the blogs page</h2>
		<p>accessible only with login</p>
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
	
	
	<c:if test="${username == null}">
		<div class="content" style="margin: 0 auto; width: 80%; text-align: center">
			<h2>You need to be logged in to see this!</h2>
			<a href="login.jsp">log in</a>
		</div>
	</c:if>
	<br><br><br><br><br><br>
	<c:if test="${username != null}">
		<h1  style="padding-left: 30px"> Create blog </h1>
		<br>
		<div style="padding-left: 30px">
			<form action="createBlog" method="post">
				Title:   <input type="text" name="title"> <br>
				Content: <input type="text" name="content"> <br>
				<input type="submit" value="create">
			</form>
		</div>
		
		<hr>
		
		<c:if test="${blogs.size() - 1 >= 0}">
			<c:forEach var="i" begin="0" end="${blogs.size() - 1}">
				<div class="blog" style="">
					<h2>${blogs.get(i).get(0)}</h2>
					<h3>${blogs.get(i).get(1)}</h3>
				</div>
				<br>
			</c:forEach>
		</c:if>
		
		
	</c:if>
	
	
</body>
</html>