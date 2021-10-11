package com.QuoteIt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UnlikeQuote_HomeServlet")
public class UnlikeQuote_HomeServlet extends HttpServlet {

	private static final long serialVersionUID = -3171859008700848227L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userID") == null) {
			response.getWriter().write("Not logged in");
			return;
		}
		
		int userID = (int) session.getAttribute("userID");
		int quoteID = Integer.parseInt(request.getParameter("quoteID"));
		
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			myStatement.executeUpdate("DELETE FROM likes WHERE userID = '" + userID + "' AND quoteID = '" + quoteID + "'");
			
			int currentLikes = 0;
			ResultSet myResultSet = myStatement.executeQuery("SELECT likes FROM quotes WHERE quoteID = '" + quoteID + "'");
			if(myResultSet.next()) 
				currentLikes = myResultSet.getInt("likes");
			
			myStatement.executeUpdate("UPDATE quotes SET likes = '" + (currentLikes - 1) + "' WHERE quoteID = '" + quoteID + "'");
		}
		catch(SQLSyntaxErrorException sqlException) {
			sqlException.printStackTrace();
			response.getWriter().write("Something went wrong with the SQL!");
		}
		catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Something went wrong!");
		}
	}
}
