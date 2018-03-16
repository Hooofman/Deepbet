package entity;

import java.util.ArrayList;

import javafx.css.Match;

public class Season {
	private int year;
	private ArrayList<Match> matches;
	private int id;
	private int numberOfRounds;
	private ArrayList<Team> teams;

	public Season() {

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void addMatch(Match match) {
		matches.add(match);
	}

	public void addTeam(Team team) {
		teams.add(team);
	}
}
