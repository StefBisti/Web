package com.Whist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EndGame_GameServlet")
public class EndGame_GameServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gameID = (String) req.getParameter("gameID");
		String winner = (String) req.getParameter("winner");
		String winnerScore = (String) req.getParameter("winnerScore");
		
		HttpSession session = req.getSession();
		ArrayList<Integer> hostedGamesIDs = (ArrayList<Integer>) session.getAttribute("hostedGamesIDs");
		hostedGamesIDs.remove(Integer.valueOf(gameID.replace("game", "")));
		session.setAttribute("hostedGamesIDs", hostedGamesIDs);
		
		Connection myConnection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/whist", "Stefan", "@T3f!,");
			Statement myStatement = myConnection.createStatement();
			
			myStatement.executeUpdate("UPDATE games SET winner = '" + winner + "', maxScore = " + winnerScore + " WHERE gameID = " + gameID.replace("game", ""));
			
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} catch (ClassNotFoundException cnfE) {
			cnfE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { myConnection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
}
