package com.blogs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckForDuplicateUsernames")
public class CheckForDuplicateUsernames extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		response.setContentType("text/plain");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsforlogin", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT userID FROM users WHERE username = '" + username + "'");
			if(myResultSet.next()) {
				response.getWriter().write("Username already taken!");
			}
		} catch (Exception e) {
			response.getWriter().write("Something went wrong!");
		}
	}
}
