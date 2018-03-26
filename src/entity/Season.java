package entity;

import java.util.ArrayList;

public class Season {
	private int year;
	private ArrayList<Match> matches;
	private int id;
	private int numberOfRounds;
	private ArrayList<Team> teams;

	public Season() {
		teams = new ArrayList<Team>();
		matches = new ArrayList<Match>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public void setNumberOfRounds(int number) {
		this.numberOfRounds = number;
	}

	public int getNumberOfRounds() {
		return numberOfRounds;
	}

	public Team getTeam(String name) {
		for (Team team : teams) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}
}
