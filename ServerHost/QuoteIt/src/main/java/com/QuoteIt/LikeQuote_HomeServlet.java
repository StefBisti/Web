package com.QuoteIt;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LikeQuote_HomeServlet")
public class LikeQuote_HomeServlet extends HttpServlet{

	private static final long serialVersionUID = 7618102215734849395L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int userID = (int) session.getAttribute("userID");
		int quoteID = Integer.parseInt(request.getParameter("quoteID"));
		
		try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("INSERT INTO likes (userID, quoteID) VALUES ('" + userID + "', '" + quoteID + "')");
			
			int currentLikes = 0;
			ResultSet myResultSet = myStatement.executeQuery("SELECT likes FROM quotes WHERE quoteID = '" + quoteID + "'");
			if(myResultSet.next()) 
				currentLikes = myResultSet.getInt("likes");
			
			myStatement.executeUpdate("UPDATE quotes SET likes = '" + (currentLikes + 1) + "' WHERE quoteID = '" + quoteID + "'");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
