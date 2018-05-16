package entity;

import java.util.ArrayList;

/**
 * Class for creating Season objects
 * @author Sven Lindqvist, Oscar Malmqvist, Johannes Roos och Oskar Engström
 *         Magnusson.
 *
 */
public class Season {
	private int year;
	private ArrayList<Match> matches;
	private int id;
	private int numberOfRounds;
	private int currentRound;
	private LeagueTable leagueTable;
	private League league;

	/**
	 * Constructs a Season object, puts Match objects into an ArrayList and creates a league table
	 * @param league a League object
	 * @param year the year the season started
	 * @param id the id used by the API
	 * @param numberOfRounds how many rounds of fixtures there is in this season
	 * @param currentRound what round is currently being played
	 */
	public Season(League league, int year, int id, int numberOfRounds, int currentRound) {
		this.league = league;
		this.matches = new ArrayList<Match>();
		this.leagueTable = new LeagueTable();
		this.year = year;
		this.id = id;
		this.numberOfRounds = numberOfRounds;
		this.currentRound = currentRound;
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

	/**
	 * Adds a match to the season
	 * @param match a Match object
	 */
	public void addMatch(Match match) {
		matches.add(match);
	}

	/**
	 * Updates the league table after a match is finished 
	 */
	public void updateTable() {
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

	/**
	 * Returns an ArrayList with all teams in a season
	 * @return ArrayList of Team objects
	 */
	public ArrayList<Team> getAllTeams() {
		return league.getAllTeamObjects();
	}

	/**
	 * Gets a Team object based on the number it has in the list
	 * @param i number in list
	 * @return Team object
	 */
	public Team getTeamByNumber(int i) {
		return league.getAllTeamObjects().get(i);
	}

	/**
	 * Gets a Team object based on the team name
	 * @param name team name
	 * @return Team object if it exists. Otherwise null
	 */
	public Team getTeam(String name) {
		for (Team team : league.getAllTeamObjects()) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}

	/**
	 * Returns an ArrayList containing all matches in a the season
	 * @return ArrayList with Match objects
	 */
	public ArrayList<Match> getAllMatches(){
		return matches;
	}
	
	/**
	 * Returns the leaguename that the season belongs to
	 * @return
	 */
	public String getLeageName() {
		return league.getName();
	}

	/**
	 * Returns an ArrayList with all matches that has a specific status
	 * @param status the status of the match
	 * @return ArrayList with Match objects
	 */
	public ArrayList<Match> getMatchesByStatus(String status) {
		ArrayList<Match> matchesToReturn = new ArrayList<Match>();
		for (Match match : matches) {
			if (match.getStatus().equals(status)) {
				matchesToReturn.add(match);
			}
		}
		return matchesToReturn;
	}
}
