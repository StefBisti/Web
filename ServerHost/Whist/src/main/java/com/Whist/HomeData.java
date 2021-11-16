package com.Whist;

import java.util.ArrayList;

public class HomeData {
	ArrayList<ArrayList<String>> players = new ArrayList<ArrayList<String>>();
	ArrayList<String> winner = new ArrayList<String>();
	ArrayList<Integer> maxScore = new ArrayList<Integer>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> gameIDs = new ArrayList<String>();
	
	public HomeData(ArrayList<ArrayList<String>> players,
			ArrayList<String> winner,
			ArrayList<Integer> maxScore,
			ArrayList<String> date,
			ArrayList<String> gameIDs)
	{
		this.players = players;
		this.winner = winner;
		this.maxScore = maxScore;
		this.date = date;
		this.gameIDs = gameIDs;
	}
	
	public ArrayList<ArrayList<String>> getPlayers() {
		return this.players;
	}
	public ArrayList<String> getWinners() {
		return this.winner;
	}
	public ArrayList<Integer> getMaxScores() {
		return this.maxScore;
	}
	public ArrayList<String> getDates() {
		return this.date;
	}
	public ArrayList<String> getIDs() {
		return this.gameIDs;
	}
}