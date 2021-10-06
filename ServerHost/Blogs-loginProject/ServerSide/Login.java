package com.blogs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int userID = 0;
		System.out.println(0);
		ArrayList<ArrayList<String>> blogsArray = new ArrayList<ArrayList<String>>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsforlogin", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT userID FROM users WHERE username = '" + username + "'");
			
			while(myResultSet.next()) {
				userID = myResultSet.getInt("userID");
			}
			
			ResultSet blogResultSet = myStatement.executeQuery("SELECT * FROM blogs WHERE userID = '" + userID + "'");
			while(blogResultSet.next()) {	
				ArrayList<String> blog = new ArrayList<String>();
				blog.add(blogResultSet.getString("title"));
				blog.add(blogResultSet.getString("content"));
				
				blogsArray.add(blog);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

		HttpSession session = request.getSession();
		session.setAttribute("username", username.replace("''", "'"));
		session.setAttribute("userID", userID);
		session.setAttribute("blogs", blogsArray);
		
		response.getWriter().write("All good");

	}
}
