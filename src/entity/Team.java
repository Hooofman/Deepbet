package entity;

import java.util.LinkedList;

/**
 * Class for creating Team objects
 * @author Oscar Malmqvist, Johannes Roos
 *
 */
public class Team implements Comparable<Team>{
	private int matchesPlayed;

	private LinkedList<Double> goalsFor;
	private LinkedList<Double> goalsAgainst;
	private LinkedList<Double> tablePosition;
	private LinkedList<Double> outcome; // Defines whether the team won or lost the game. 1 for win, 0.5 for draw, 0 for loss
	private LinkedList<Double> location;
	private LinkedList<Double> goalsForThisSeason;
	private LinkedList<Double> goalsAgainstThisSeason;
	private LinkedList<Double> totalPointsThisSeason;
	private LinkedList<Double> totalPointsHomeThisSeason;
	private LinkedList<Double> totalPointsAwayThisSeason;
	private LinkedList<Double> totalPoints;

	private String name;

	/** 
	 * Constructs a Team object and arrays holding information about the team
	 * @param name team name
	 */
	public Team(String name) {
		this.name = name;
		this.goalsFor = new LinkedList<Double>();
		this.goalsAgainst = new LinkedList<Double>();
		this.tablePosition = new LinkedList<Double>();
		this.outcome = new LinkedList<Double>(); // Defines whether the team won or lost the game. 1 for win, 0.5 for draw, 0 for loss
		this.location = new LinkedList<Double>();
		this.matchesPlayed = 0;
		this.goalsForThisSeason = new LinkedList<Double>();
		this.goalsAgainstThisSeason = new LinkedList<Double>();
		this.totalPointsThisSeason = new LinkedList<Double>();
		this.totalPointsHomeThisSeason = new LinkedList<Double>();
		this.totalPointsAwayThisSeason = new LinkedList<Double>();
		this.totalPoints = new LinkedList<Double>();
	}

	/**
	 * Adds 1 to matchesPlayed
	 */
	public void addPlayedMatch() {
		this.matchesPlayed++;
	}
	/**
	 * Returns number of matches played for this team
	 * @return 	number of matches played
	 */
	public int getMatchesPlayed() {
		return matchesPlayed;
	}
	/**
	 * Retuns a list of all table positions 
	 * @return	list of table positions
	 */
	public LinkedList<Double> getTablePositions() {
		return tablePosition;
	}
	/**
	 * Adds the outcome of one Match to the list of outcomes 
	 * @param outcome 	the outcome of one Match
	 */
	public void setOutcome(double outcome) {
		this.outcome.add(outcome);
	}

	/**
	 * Gets the outcome of a match in a specific round
	 * @param index round
	 * @return 1 for win, 0.5 for draw, 0 for loss. Null if the match isn't played yet
	 */
	public Double getOutcomeForASpecificRound(int index) {
		if (index < outcome.size()) {
			return outcome.get(index);
		}
		return null;
	}

	/**
	 * Sets how many goals the teams scored in a match and adds it to goalsForThisSeason
	 * @param value goals scored
	 */
	public void setGoalsFor(double value) {
		this.goalsFor.add(value);
		this.goalsForThisSeason.add(value);
	}
	/**
	 * Returns number of goals scored in a particulary match
	 * @param index	the match
	 * @return	number of goals
	 */
	public double getGoalsFor(int index) {
		return goalsFor.get(index);
	}

	/**
	 * Sets how many goals the teams conceded in a match and adds it to goalsAgainstThisSeason
	 * @param value goals conceded
	 */
	public void setGoalsAgainst(double value) {
		this.goalsAgainst.add(value);
		this.goalsAgainstThisSeason.add(value);
	}
	/**
	 * Returns a list of all goals scored 
	 * @return 	the list of goals
	 */
	public LinkedList<Double> getGoalsFor() {
		return goalsFor;
	}
	/**
	 * Returns a list o all goals conceded
	 * @return	List of all goals conceded
	 */
	public LinkedList<Double> getGoalsAgainst() {
		return goalsAgainst;
	}
	/**
	 * Returns number of goals conceded in a particularly match
	 * @param index the match
	 * @return number of goals
	 */
	public double getGoalsAgainst(int index) {
		return goalsAgainst.get(index);
	}

	/**
	 * Sets the location and points for a finished match
	 * @param location 1 if home, 0 if away
	 * @param points points taken by the team in that match
	 */
	public void setPointsAndLocation(double location, double points) {
		if (location == 1) {
			this.totalPointsHomeThisSeason.add(points);
		} else if (location == 0) {
			this.totalPointsAwayThisSeason.add(points);
		}
		this.location.add(location);
		this.totalPointsThisSeason.add(points);
		this.totalPoints.add(points);
	}
	/**
	 * add the current tableposition to the list of table positions
	 * @param value	current tableposition
	 */
	public void setTablePosition(double value) {
		this.tablePosition.add(value);
	}
	/**
	 * Returns the tableposition at a particularly round
	 * @param index	the round
	 * @return		tableposition
	 */
	public double getTablePosition(int index) {
		return tablePosition.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Double> getTotalPointsThisSeason() {
		return this.totalPointsThisSeason;
	}

	/**
	 * Calculates the total sum of all values in a specific list 
	 * @param list a LinkedList with doubles 
	 * @return sum of all values in the list
	 */
	public double getSumOfList(LinkedList<Double> list) {
		double sum = 0;
		for (Double round : list) {
			sum += round;
		}
		return sum;
	}

	/**
	 * Calculates the average in a list from the last N games
	 * @param list a LinkedList with doubles 
	 * @param number the number of games it will count
	 * @return average of last N values in the list
	 */
	public double getAverageForNGames(LinkedList<Double> list, int number) {
		double sum = 0;	
		int loopNumber = Math.max(list.size()-number,0);
		double avgNumber = 0;
		for (int i = loopNumber; i<list.size(); i++) {
			sum += list.get(i);
			avgNumber++;
		}
		return sum / (Math.max(avgNumber,1));
	}

	/**
	 * Returns the last value in a list
	 * @param list a LinkedList with doubles
	 * @return the last of the list
	 */
	public double getLastFromList(LinkedList<Double> list) {
		if (!list.isEmpty()) {
			return list.getLast();
		}
		return 0;
	}

	/**
	 * Creates an array containing all stats of a team which is used by the AI to calculate 
	 * how good that team is
	 * @param currentRound
	 * @param number number of games it checks when calculating average
	 * @return array with all parameters for the team
	 */
	public double[] createInputArray(int number) {
		double[] inputArray = new double[11];
		inputArray[0] = getSumOfList(goalsForThisSeason);
		inputArray[1] = Math.abs(getSumOfList(goalsAgainstThisSeason));
		inputArray[2] = getSumOfList(totalPointsThisSeason);
		inputArray[3] = getSumOfList(totalPointsHomeThisSeason);
		inputArray[4] = getSumOfList(totalPointsAwayThisSeason);
		inputArray[5] = getLastFromList(totalPointsThisSeason);
		inputArray[6] = getAverageForNGames(totalPoints, number);
		inputArray[7] = getLastFromList(tablePosition);
		inputArray[8] =	getAverageForNGames(goalsFor, number);
		inputArray[9] = Math.abs(getAverageForNGames(goalsAgainst, number));
		inputArray[10] = getLastFromList(location);
		return inputArray;
	}

	public String toString() {
		return this.name;
	}

	/**
	 * Defines how the teams are sorted in the league table by comparing with another team 
	 */
	public int compareTo(Team otherTeam) {
		double thisPoints = this.getSumOfList(totalPointsThisSeason);
		double otherPoints = otherTeam.getSumOfList(otherTeam.totalPointsThisSeason);
		double thisGoalDiff = this.getSumOfList(goalsForThisSeason) - this.getSumOfList(goalsAgainstThisSeason);
		double otherGoalDiff = otherTeam.getSumOfList(otherTeam.goalsForThisSeason) - otherTeam.getSumOfList(goalsAgainstThisSeason);
		double thisGoals = this.getSumOfList(goalsForThisSeason);
		double otherGoals = otherTeam.getSumOfList(goalsForThisSeason);

		if( thisPoints > otherPoints ) {
			return -1;
		}else if( thisPoints < otherPoints) {
			return 1;
		}else if(thisGoalDiff > otherGoalDiff) {
			return -1;
		}else if(thisGoalDiff < otherGoalDiff) {
			return 1;
		}else if(thisGoals > otherGoals) {
			return -1;
		}else if(thisGoals < otherGoals) {
			return 1;
		}
		return 0;
	}

	/**
	 * Resets the statistics when a new season starts
	 */
	public void resetForNewSeason() {
		this.matchesPlayed = 0;
		this.tablePosition.clear();
		this.goalsForThisSeason.clear();
		this.goalsAgainstThisSeason.clear();
		this.totalPointsThisSeason.clear();
		this.totalPointsHomeThisSeason.clear();
		this.totalPointsAwayThisSeason.clear();
	}
}