package com.QuoteIt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetQuotes_HomeServlet")
public class GetQuotes_HomeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 837109260126364750L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int userID = -1;
		if(session.getAttribute("userID") != null)
			userID = ((int) session.getAttribute("userID")); 
		
		ArrayList<ArrayList<String>> bestQuotes = GetBestQuotes();
		ArrayList<ArrayList<String>> newQuotes = GetNewQuotes();
		ArrayList<ArrayList<String>> myQuotes = null;
		ArrayList<Integer> likedQuotes = null;
		
		if(userID != -1) {
			myQuotes = GetMyQuotes(userID);
			likedQuotes = GetLikedQuotes(userID);
		}
		
		if(myQuotes != null) {
			int totalLikes = 0;
			for(ArrayList<String> quote : myQuotes) {
				totalLikes += Integer.parseInt(quote.get(4));
			}
			
			session.setAttribute("numberOfQuotes", myQuotes.size());
			session.setAttribute("totalLikes", totalLikes);
		}
			
		session.setAttribute("bestQuotes", bestQuotes);
		session.setAttribute("newQuotes", newQuotes);
		session.setAttribute("myQuotes", myQuotes);
		session.setAttribute("likedQuotes", likedQuotes);
			
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM Y");
		session.setAttribute("date", dateFormat.format(date));
		
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}	
	
	static ArrayList<Integer> GetLikedQuotes(int userID) {
		ArrayList<Integer> likedQuotes = new ArrayList<Integer>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT quoteID FROM likes WHERE userID = '" + userID + "'");
			
			while(myResultSet.next()) {	
				likedQuotes.add(myResultSet.getInt("quoteID"));
			}
			
		} catch (Exception e) {
			return null;
		}
		return likedQuotes;
	}
	
	static ArrayList<ArrayList<String>> GetBestQuotes() {
		ArrayList<ArrayList<String>> quotesArray = new ArrayList<ArrayList<String>>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM quotes ORDER BY likes DESC");
			
			while(myResultSet.next()) {	
				ArrayList<String> singleQuote = new ArrayList<String>();
				singleQuote.add(myResultSet.getString("quoteID"));
				singleQuote.add(myResultSet.getString("content"));
				singleQuote.add(myResultSet.getString("realAuthor"));
				singleQuote.add(myResultSet.getString("userAuthor"));
				singleQuote.add(String.valueOf(myResultSet.getInt("likes")));
				
				Date date = myResultSet.getDate("date");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM Y");
				singleQuote.add(dateFormat.format(date));
				
				quotesArray.add(singleQuote);
			}
			
		} catch (Exception e) {
			return null;
		}
		return quotesArray;
	}
	static ArrayList<ArrayList<String>> GetNewQuotes() {
		ArrayList<ArrayList<String>> quotesArray = new ArrayList<ArrayList<String>>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM quotes ORDER BY date DESC, likes DESC");
			
			while(myResultSet.next()) {	
				ArrayList<String> singleQuote = new ArrayList<String>();
				singleQuote.add(myResultSet.getString("quoteID"));
				singleQuote.add(myResultSet.getString("content"));
				singleQuote.add(myResultSet.getString("realAuthor"));
				singleQuote.add(myResultSet.getString("userAuthor"));
				singleQuote.add(String.valueOf(myResultSet.getInt("likes")));
				
				Date date = myResultSet.getDate("date");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM Y");
				singleQuote.add(dateFormat.format(date));
				
				quotesArray.add(singleQuote);
			}
			
		} catch (Exception e) {
			return null;
		}
		return quotesArray;
	}
	static ArrayList<ArrayList<String>> GetMyQuotes(int userID) {
		ArrayList<ArrayList<String>> quotesArray = new ArrayList<ArrayList<String>>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quoteit", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM quotes WHERE userID = '" + userID + "' ORDER BY likes DESC");
			
			while(myResultSet.next()) {	
				ArrayList<String> singleQuote = new ArrayList<String>();
				singleQuote.add(myResultSet.getString("quoteID"));
				singleQuote.add(myResultSet.getString("content"));
				singleQuote.add(myResultSet.getString("realAuthor"));
				singleQuote.add(myResultSet.getString("userAuthor"));
				singleQuote.add(String.valueOf(myResultSet.getInt("likes")));
				
				Date date = myResultSet.getDate("date");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM Y");
				singleQuote.add(dateFormat.format(date));
				
				quotesArray.add(singleQuote);
			}
			
		} catch (Exception e) {
			return null;
		}
		return quotesArray;
	}
}
