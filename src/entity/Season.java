package entity;

import java.util.ArrayList;

public class Season {
	private int year;
	private ArrayList<Match> matches;
	private int id;
	private int numberOfRounds;
	private int currentRound;
	private LeagueTable leagueTable;
	private League league;

	public Season(League league) {
		this.league = league;
		matches = new ArrayList<Match>();
		leagueTable = new LeagueTable();

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
		leagueTable.updateTable(league.getAllTeamObjects());

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
		return league.getAllTeamObjects();
	}

	public Team getTeamByNumber(int i) {
		return league.getAllTeamObjects().get(i);
	}

	public Team getTeam(String name) {
		for (Team team : league.getAllTeamObjects()) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}
}
