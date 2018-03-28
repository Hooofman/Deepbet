package entity;

import java.util.ArrayList;

public class Season {
	private int year;
	private ArrayList<Match> matches;
	private int id;
	private int numberOfRounds;
	private ArrayList<Team> teams;
	private int currentRound;

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
	
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	
	public int getCurrentRound() {
		return currentRound;
	}
	
	public ArrayList<Team> getAllTeams() {
		return teams;
	}
	
	public Team getTeamByNumber(int i) {
		return teams.get(i);
	}

	public Team getTeam(String name) {
		for (Team team : teams) {
			System.out.println(name +" - " + team.getName() + " - " + team.getName().equals(name));
			if (team.getName().equals(name)) {
				System.out.println(team);
				return team;
			}
		}
		return null;
	}
}
