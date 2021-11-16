package com.Whist;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetGames_HomeServlet")
public class GetGames_HomeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ArrayList<ArrayList<String>> playerss = new ArrayList<ArrayList<String>>();
		ArrayList<String> winners = new ArrayList<String>();
		ArrayList<Integer> maxScores = new ArrayList<Integer>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> gameIDs = new ArrayList<String>();
		
		Connection myConnection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM games ORDER BY date DESC");
			
			while(myResultSet.next()) {
				
				String playersString = myResultSet.getString("players");
				ArrayList<String> subPlayers = new ArrayList<String>(Arrays.asList(playersString.split(",")));
				playerss.add(subPlayers);
				
				String subWinner = myResultSet.getString("winner");
				winners.add(subWinner);
				
				int subMaxScore = myResultSet.getInt("maxScore");
				maxScores.add(subMaxScore);
				
				Date subDate = myResultSet.getDate("date");
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMMM Y");
				dates.add(dateFormat2.format(subDate));
				
				String subID = myResultSet.getString("gameID");
				gameIDs.add(subID);
			}

		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} catch (ClassNotFoundException cnfE) {
			cnfE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { myConnection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		HomeData homeData = new HomeData(playerss, winners, maxScores, dates, gameIDs);
		
		HttpSession session = req.getSession();
		session.setAttribute("homeData", homeData);
		
		RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
		rd.forward(req, resp);
	}
}