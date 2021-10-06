package com.QuoteIt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int userID = 0;
		HttpSession session = request.getSession();
		response.setContentType("text/plain");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
			ResultSet myResultSet = myStatement.executeQuery("SELECT userID FROM users WHERE username = '" + username + "'");
			while(myResultSet.next()) {
				userID = myResultSet.getInt("userID");
			}
			session.setAttribute("username", username.replace("''", "'"));
			session.setAttribute("userID", userID);
			response.getWriter().write("All good");
		}
		catch (Exception e) {
			response.getWriter().write("Something went wrong");
			e.printStackTrace();
		}
	}
}
