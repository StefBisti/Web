package com.Whist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CreateGame_HomeServlet")
public class CreateGame_HomeServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String playersString = req.getParameter("players");
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String todaysDate = dateFormat.format(date);
		
		String gameID = null;
		
		Connection myConnection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();

			myStatement.executeUpdate("INSERT INTO games (players, winner, maxScore, date) VALUES ('" + playersString + "', '-', 0, '" + todaysDate + "')");
			ResultSet myResultSet = myStatement.executeQuery("SELECT MAX(gameID) FROM games");
			
			int gameName = 0;
			while(myResultSet.next()) {
				gameName = myResultSet.getInt("MAX(gameID)");
				gameID = "game" + gameName;
			}
	
			myStatement.executeUpdate("CREATE TABLE game" + gameName + " ("
					+ "	   rowID int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "    bets varchar(10) NOT NULL,"
					+ "    hands varchar(10) NOT NULL,"
					+ "    points varchar(20) NOT NULL"
					+ ")");
									
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
		
		ArrayList<Integer> hostedGamesIDs = new ArrayList<Integer>();
		if(session.getAttribute("hostedGamesIDs") != null)
			hostedGamesIDs = (ArrayList<Integer>) session.getAttribute("hostedGamesIDs");

		hostedGamesIDs.add(Integer.parseInt(gameID.replace("game", "")));
		session.setAttribute("hostedGamesIDs", hostedGamesIDs);
		
		resp.getWriter().write(gameID);
	}
}
