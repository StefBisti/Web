package com.blogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/createBlog")
public class CreateBlog extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsforlogin", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("INSERT INTO blogs (title, content, userID) VALUES ('" + title + "', '" + content + "', '" + request.getSession().getAttribute("userID") + "')");
			
			HttpSession session = request.getSession();
			ArrayList<ArrayList<String>> blogsArray = new ArrayList<ArrayList<String>>();
			blogsArray = (ArrayList<ArrayList<String>>) session.getAttribute("blogs");
	
			ArrayList<String> blog = new ArrayList<String>();
			blog.add(title);
			blog.add(content);
			blogsArray.add(blog);
			session.setAttribute("blogs", blogsArray);
			
			response.sendRedirect("blogs.jsp");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
