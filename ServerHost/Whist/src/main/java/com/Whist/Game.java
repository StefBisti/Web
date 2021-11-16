package com.Whist;

import java.util.ArrayList;

public class Game {
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<ArrayList<Integer>> bets = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> hands = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<String>> scores = new ArrayList<ArrayList<String>>();
	ArrayList<Integer> totalScores = new ArrayList<Integer>();
	String winner;
	int winnerScore;
	
	public Game(ArrayList<String> names,
				ArrayList<ArrayList<Integer>> bets,
				ArrayList<ArrayList<Integer>> hands,
				ArrayList<ArrayList<String>> scores,
				ArrayList<Integer> totalScores,
				String winner, int winnerScore) 
	{
		this.names = names;
		this.bets = bets;
		this.hands = hands;
		this.scores = scores;
		this.totalScores = totalScores;
		this.winner = winner;
		this.winnerScore = winnerScore;
		
	}
	
	public ArrayList<String> getNames() {
		return this.names;
	}
	public ArrayList<ArrayList<Integer>> getBets() {
		return this.bets;
	}
	public ArrayList<ArrayList<Integer>> getHands() {
		return this.hands;
	}
	public ArrayList<ArrayList<String>> getScores() {
		return this.scores;
	}
	public ArrayList<Integer> getTotalScores(){
		return this.totalScores;
	}
	public String getWinner(){
		return this.winner;
	}
	public int getWinnerScore() {
		return this.winnerScore;
	}
}