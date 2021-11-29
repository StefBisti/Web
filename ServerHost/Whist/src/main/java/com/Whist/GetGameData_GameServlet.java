package com.Whist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;


@WebServlet("/GetGameData_GameServlet")
public class GetGameData_GameServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String gameID = (String) req.getParameter("gameID");
		Game gameData = null;
	
		Connection myConnection = null;
		HttpSession session = req.getSession();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			
			ArrayList<String> names = getNames(myStatement, gameID);
			Pair<ArrayList<ArrayList<String>>, ArrayList<Integer>> pair = getScores(myStatement, names.size(), gameID, session);

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
	
	static Pair<ArrayList<ArrayList<String>>, ArrayList<Integer>> getScores(Statement myStatement, int playersNumber, String gameID, HttpSession session) throws SQLException {
		ArrayList<ArrayList<String>> scores = new ArrayList<ArrayList<String>>();
		ResultSet myResultSet = myStatement.executeQuery("SELECT points FROM " + gameID);
		
		ArrayList<Integer> totalScores = new ArrayList<Integer>();
		for(int i = 0; i < playersNumber; i++) 
			totalScores.add(0);
		
		while(myResultSet.next()) {
			String pointsString = myResultSet.getString("points");
			
			
			ArrayList<String> subPoint = new ArrayList<String>();
			int arrayIndex = 0;
			for(int i = 0; i < playersNumber; i++) {
				
				String point = Character.toString(pointsString.charAt(arrayIndex)) + Character.toString(pointsString.charAt(arrayIndex + 1));
				
				if(arrayIndex < pointsString.length() - 2 && !Character.toString(pointsString.charAt(arrayIndex + 2)).equals("+") && !Character.toString(pointsString.charAt(arrayIndex + 2)).equals("-")){
					point += Character.toString(pointsString.charAt(arrayIndex + 2));
					arrayIndex++;
				}
			
				subPoint.add(point);	
				totalScores.set(i, totalScores.get(i) + Integer.parseInt(point));
				
				arrayIndex += 2;
				
			}
			scores.add(subPoint);
		}
		
		
		ArrayList<Integer> frequencyArray = new ArrayList<Integer>();  // premiere
		for(int i = 0; i < playersNumber; i++) {
			frequencyArray.add(0);
			for(int j = playersNumber; j < Math.min(playersNumber * 2 + 12 - 1, scores.size()); j++) {
				if(scores.get(j).get(i).charAt(0) == '+' && Integer.parseInt(scores.get(j).get(i)) < 14)
					frequencyArray.set(i, frequencyArray.get(i) + 1);
				else
					frequencyArray.set(i, 0);
			}
		}		
		session.setAttribute("frequencyArray", frequencyArray);

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