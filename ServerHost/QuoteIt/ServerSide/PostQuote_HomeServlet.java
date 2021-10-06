package com.QuoteIt;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PostQuote_HomeServlet")
public class PostQuote_HomeServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		int userID = (int) session.getAttribute("userID");
		String content = request.getParameter("content");
		String author = request.getParameter("author");
		String username = (String) session.getAttribute("username");
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String todaysDate = dateFormat.format(date);
		
		System.out.println(author);
		
		try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("INSERT INTO quotes (userID, content, realAuthor, userAuthor, date) VALUES ('" + userID + "', '" + content + "', '" + author + "', '" + username + "', '" + todaysDate + "')");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
