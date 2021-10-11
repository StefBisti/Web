package com.QuoteIt;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
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

	private static final long serialVersionUID = -1882529755686175430L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		int userID = (int) session.getAttribute("userID");
		String content = request.getParameter("content");
		String username = (String) session.getAttribute("username");
		String author = request.getParameter("author") == null ? request.getParameter("author") : username;
		
		
		System.out.println(author);
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String todaysDate = dateFormat.format(date);
		
		try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("INSERT INTO quotes (userID, content, realAuthor, userAuthor, likes, date) VALUES ('" + userID + "', '" + content + "', '" + author + "', '" + username + "', '" + 0 + "', '" + todaysDate + "')");
		}
		catch(SQLSyntaxErrorException sqlException) {
			sqlException.printStackTrace();
			response.getWriter().write("Something went wrong with the SQL!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			response.getWriter().write("Something went wrong!");
		}
	}
}
