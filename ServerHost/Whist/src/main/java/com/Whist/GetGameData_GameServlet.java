package com.Whist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;


@WebServlet("/GetGameData_GameServlet")
public class GetGameData_GameServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String gameID = (String) req.getParameter("gameID");
		Game gameData = null;
	
		Connection myConnection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			
			ArrayList<String> names = getNames(myStatement, gameID);
			Pair<ArrayList<ArrayList<String>>, ArrayList<Integer>> pair = getScores(myStatement, names.size(), gameID);

			gameData = new Game(names, getBets(myStatement, names.size(), gameID), getHands(myStatement, names.size(), gameID), pair.left, pair.right, getWinner(myStatement, gameID), getWinnerScore(myStatement, gameID));
				
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} catch (ClassNotFoundException cnfE) {
			cnfE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { myConnection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("gameData", gameData);
		session.setAttribute("gameID", gameID);
	}
	
	static ArrayList<String> getNames(Statement myStatement, String gameID) throws SQLException {
		
		ResultSet myResultSet = myStatement.executeQuery("SELECT players FROM games WHERE gameID = " + gameID.replace("game", ""));
		
		ArrayList<String> players = new ArrayList<String>();
		while(myResultSet.next()) {
			String playersString = myResultSet.getString("players");
			players = new ArrayList<String>(Arrays.asList(playersString.split(", ")));
		}
		return players;
	}
	
	static ArrayList<ArrayList<Integer>> getBets(Statement myStatement, int playersNumber, String gameID) throws SQLException {
		ArrayList<ArrayList<Integer>> bets = new ArrayList<ArrayList<Integer>>();
		ResultSet myResultSet = myStatement.executeQuery("SELECT bets FROM " + gameID);
		
		while(myResultSet.next()) {
			String betsString = myResultSet.getString("bets");
			
			ArrayList<Integer> subBet = new ArrayList<Integer>();
			for(int i = 0; i < playersNumber; i++) 
				subBet.add(Integer.parseInt(Character.toString(betsString.charAt(i))));	
			bets.add(subBet);
		}
		
		return bets;
	}
	
	static ArrayList<ArrayList<Integer>> getHands(Statement myStatement, int playersNumber, String gameID) throws SQLException {
		ArrayList<ArrayList<Integer>> hands = new ArrayList<ArrayList<Integer>>();
		ResultSet myResultSet = myStatement.executeQuery("SELECT hands FROM " + gameID);
		
		while(myResultSet.next()) {
			String handsString = myResultSet.getString("hands");
			
			ArrayList<Integer> subHand = new ArrayList<Integer>();
			for(int i = 0; i < playersNumber; i++) 
				subHand.add(Integer.parseInt(Character.toString(handsString.charAt(i))));	
			hands.add(subHand);
		}
		
		return hands;
	}
	
	static Pair<ArrayList<ArrayList<String>>, ArrayList<Integer>> getScores(Statement myStatement, int playersNumber, String gameID) throws SQLException {
		ArrayList<ArrayList<String>> scores = new ArrayList<ArrayList<String>>();
		ResultSet myResultSet = myStatement.executeQuery("SELECT points FROM " + gameID);
		
		ArrayList<Integer> totalScores = new ArrayList<Integer>();
		for(int i = 0; i < playersNumber; i++) 
			totalScores.add(0);
		
		while(myResultSet.next()) {
			String pointsString = myResultSet.getString("points");
			
			
			ArrayList<String> subPoint = new ArrayList<String>();
			for(int i = 0; i < playersNumber * 2; i += 2) {
				
				String point = Character.toString(pointsString.charAt(i)) + Character.toString(pointsString.charAt(i + 1));
				
				if(i < pointsString.length() - 2 && !Character.toString(pointsString.charAt(i + 2)).equals("+") && !Character.toString(pointsString.charAt(i + 2)).equals("-")){
					point += Character.toString(pointsString.charAt(i + 2));
					i++;
				}
			
				subPoint.add(point);	
				totalScores.set(i/2, totalScores.get(i/2) + Integer.parseInt(Character.toString(pointsString.charAt(i)) + Character.toString(pointsString.charAt(i + 1))));
			}
			scores.add(subPoint);
		}
		return new Pair<ArrayList<ArrayList<String>>, ArrayList<Integer>>(scores, totalScores);
	}	
	
	static String getWinner(Statement myStatement, String gameID) throws SQLException {
		ResultSet myResultSet = myStatement.executeQuery("SELECT winner FROM games WHERE gameID = " + gameID.replace("game", ""));
		
		while(myResultSet.next())
			return myResultSet.getString("winner");
		
		return "-";
		
	}
	
	static int getWinnerScore(Statement myStatement, String gameID) throws SQLException {
		ResultSet myResultSet = myStatement.executeQuery("SELECT maxScore FROM games WHERE gameID = " + gameID.replace("game", ""));
		
		while(myResultSet.next())
			return myResultSet.getInt("maxScore");
		
		return -1;
		
	}
}