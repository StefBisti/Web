package com.QuoteIt;

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

@WebServlet("/CheckPassword_LoginServlet")
public class CheckPassword_LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7815715726834612841L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/plain");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT password FROM users WHERE username = '" + username + "'");
			if(myResultSet.next()) {
				if(myResultSet.getString("password").equals(password))
					response.getWriter().write("Password is corect");
				else
					response.getWriter().write("Password is not corect");
			}
			else {
				response.getWriter().write("Username does not exist");
			}
		} catch (Exception e) {
			response.getWriter().write("Something went wrong!");
		}
	
	}
}
