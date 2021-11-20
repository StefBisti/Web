package com.Whist;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddRow_GameServlet")
public class AddRow_GameServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String betsString = req.getParameter("betsString");
		String handsString = req.getParameter("handsString");
		int numberOfPlayers = Integer.parseInt(req.getParameter("numberOfPlayers"));
		int cardsDealt = Integer.parseInt(req.getParameter("cardsDealt"));
		String gameID = req.getParameter("gameID");
		
		ArrayList<Integer> bets = new ArrayList<Integer>();
		for(int i = 0; i < numberOfPlayers; i++) 
			bets.add(Integer.parseInt(Character.toString(betsString.charAt(i))));
		
		ArrayList<Integer> hands = new ArrayList<Integer>();
		for(int i = 0; i < numberOfPlayers; i++) 
			hands.add(Integer.parseInt(Character.toString(handsString.charAt(i))));
		
		String error = "";
		if(!checkHands(hands, cardsDealt))
			error = "Hands are incorrect";
		if(!checkBets(bets, cardsDealt))
			error = "Bets are incorrect";

		if(error == "") {
			String scores = getScores(bets, hands);
			
			int roundNumber = postRow(gameID, betsString, handsString, scores);
			
			boolean gameEnded = roundNumber >= 3 * numberOfPlayers + 12;
			
			String jsonString = "{\"gameID\":\"" + gameID 
							+ "\",\"betsString\":\"" + betsString
							+ "\",\"handsString\":\"" + handsString
							+ "\",\"scores\":\"" + scores
							+ "\",\"rowNumber\":" + roundNumber
							+ ",\"error\":\"" + error
							+ "\",\"gameEnded\":" + gameEnded + "}";
			
			resp.getWriter().write(jsonString);
		}
		else 
			resp.getWriter().write(error);
	}
	
	static boolean checkBets(ArrayList<Integer> bets, int cardsDealt) {
		int sum = 0;
		
		for(int i = 0; i < bets.size(); i++) {	
			if(bets.get(i) > cardsDealt)
				return false;
			sum += bets.get(i);
		}
		if(sum == cardsDealt)
			return false;
		
		return true;
	}
	
	static boolean checkHands(ArrayList<Integer> hands, int cardsDealt) {
		int sum = 0;
		
		for(int i = 0; i < hands.size(); i++) {	
			if(hands.get(i) > cardsDealt)
				return false;
			sum += hands.get(i);
		}
		if(sum != cardsDealt)
			return false;
		
		return true;
	}
	
	static String getScores(ArrayList<Integer> bets, ArrayList<Integer> hands) {
		
		String result = "";
		
		for(int i = 0; i < bets.size(); i++) {
			if(bets.get(i) == hands.get(i)) 
				result += "+" + String.valueOf(5 + bets.get(i));
			else
				result += "-" + String.valueOf(Math.abs(bets.get(i) - hands.get(i)));
		}
		return result;
	}
	
	static int postRow(String gameID, String betsString, String handsString, String pointsString) {
		Connection myConnection = null;
		int roundNumber = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			
			ResultSet myResultSet = myStatement.executeQuery("SELECT MAX(rowID) FROM " + gameID);
			while(myResultSet.next())
				roundNumber = myResultSet.getInt("MAX(rowID)");
			
			myStatement.executeUpdate("INSERT INTO " + gameID + " (bets, hands, points) VALUES ('" + betsString + "', '" + handsString + "', '" + pointsString + "')");

		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} catch (ClassNotFoundException cnfE) {
			cnfE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { myConnection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return roundNumber;
	}
}
