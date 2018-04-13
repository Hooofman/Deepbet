package entity;

import java.util.ArrayList;
import java.util.LinkedList;


import entity.LocationAndPoint;

/**
 * Class for creating Team objects
 * @author
 *
 */

public class TeamOld implements Comparable<TeamOld>{

	private int matchesPlayed;

	private ArrayList<Integer> goalsFor;
	private ArrayList<Integer> goalsAgainst;
	private LinkedList<Integer> tablePosition;
	private ArrayList<Double> outcome; // Defines whether the team won or lost the game. 1 for win, 0.5 for draw, 0 for loss
	private ArrayList<LocationAndPoint> locationAndPoints;
	private ArrayList<Integer> goalsForThisSeason;
	private ArrayList<Integer> goalsAgainstThisSeason;
	private ArrayList<Integer> totalPointsThisSeason;
	private ArrayList<Integer> totalPointsHomeThisSeason;
	private ArrayList<Integer> totalPointsAwayThisSeason;

	private String name;
	private String shortName;
	private static int id;

	/** 
	 * Constructs a Team object and arrays holding information about the team
	 * @param name team name
	 */
	public TeamOld(String name) {
		this.name = name;
		this.goalsFor = new ArrayList<Integer>();
		this.goalsAgainst = new ArrayList<Integer>();
		this.tablePosition = new LinkedList<Integer>();
		this.outcome = new ArrayList<Double>(); // Defines whether the team won or lost the game. 1 for win, 0.5 for draw, 0 for loss
		this.locationAndPoints = new ArrayList<LocationAndPoint>();
		this.matchesPlayed = 0;
		this.goalsForThisSeason = new ArrayList<Integer>();
		this.goalsAgainstThisSeason = new ArrayList<Integer>();
		this.totalPointsThisSeason = new ArrayList<Integer>();
		this.totalPointsHomeThisSeason = new ArrayList<Integer>();
		this.totalPointsAwayThisSeason = new ArrayList<Integer>();
	}

	/**
	 * Adds 1 to matchesPlayed
	 */
	public void addPlayedMatch() {
		this.matchesPlayed++;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setLocation(int location) {
		this.locationAndPoints.add(new LocationAndPoint(location));
	}

	/**
	 * Gets the location the team played at for a specific round
	 * @param index round
	 * @return 1 if home, 0 if away
	 */
	public int getLocationForASpecificRound(int index) {
		return locationAndPoints.get(index).getLocation();
	}

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
	public void setGoalsFor(int value) {
		this.goalsFor.add(value);
		this.goalsForThisSeason.add(value);
	}

	public int getGoalsFor(int index) {
		return goalsFor.get(index);
	}

	/**
	 * Sets how many goals the teams conceded in a match and adds it to goalsAgainstThisSeason
	 * @param value goals conceded
	 */
	public void setGoalsAgainst(int value) {
		this.goalsAgainst.add(value);
		this.goalsAgainstThisSeason.add(value);
	}

	public int getGoalsAgainst(int index) {
		return goalsAgainst.get(index);
	}

	/**
	 * Sets the location and points for a finished match
	 * @param location 1 if home, 0 if away
	 * @param points points taken by the team in that match
	 */
	public void setPointsAndLocation(int location, int points) {
		if (location == 1) {
			this.totalPointsHomeThisSeason.add(points);
		} else if (location == 0) {
			this.totalPointsAwayThisSeason.add(points);
		}
		this.locationAndPoints.add(new LocationAndPoint(location, points));
		this.totalPointsThisSeason.add(points);
	}

	public void setPoints(int index, int value) {
		this.locationAndPoints.add(this.locationAndPoints.get(index).setPoints(value));
	}

	public LocationAndPoint getPoints(int index) {
		return locationAndPoints.get(index);
	}

	public void setTablePosition(int value) {
		this.tablePosition.add(value);
	}

	public double getTablePosition(int index) {
		return tablePosition.get(index);
	}

	public double getLastTablePosition() {
		return tablePosition.getLast();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	/**
	 * Calculates the average number points taken by the team in last N games
	 * @param round current round
	 * @param number how many games it should check
	 * @return average number of points in last N games
	 */
	public double pointsLastNGames(int round, int number) {
		double sum = 0;
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum += this.locationAndPoints.get(i).getPoints();
				avgNbr++;
			}
		}

		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	/**
	 * Calculates the average number of goals scored by the team in last N games
	 * @param round current round
	 * @param number how many games it should check
	 * @return average goals scored in last N games
	 */
	public double goalsForLastNGames(int round, int number) {
		double sum = 0;
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum += this.goalsFor.get(i);
				avgNbr++;
			}
		}
		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	/**
	 * Calculates the average number of goals conceded by the team in last N games
	 * @param round current round
	 * @param number how many games it should check
	 * @return average goals conceded in last N games
	 */
	public double goalsAgainstLastNGames(int round, int number) {
		double sum = 0;
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum -= this.goalsAgainst.get(i);
				avgNbr++;
			}
		}
		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	/**
	 * Returns total goals scored in the season by a specific round
	 * @param round 
	 * @return sum of total goals scored
	 */
	public double getSumOfGoals(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.goalsForThisSeason.size()) {
				sum += this.goalsForThisSeason.get(i);
			}
		}
		return sum;
	}

	/**
	 * Returns total goals conceded in the season by a specific round
	 * @param round 
	 * @return sum of total goals conceded
	 */
	public double getSumOfGoalsAgainst(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.goalsAgainstThisSeason.size()) {
				sum -= this.goalsAgainstThisSeason.get(i);
			}
		}

		return sum;
	}

	/**
	 * Returns amount of points in the season by a specific round
	 * @param round 
	 * @return sum of total points
	 */
	public double getTotalPoints(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.totalPointsThisSeason.size()) {
				sum += this.totalPointsThisSeason.get(i);
			}
		}
		return sum;
	}

	/**
	 * Returns amount of points taken at home by a specific round
	 * @param round 
	 * @return sum of total points taken at home
	 */
	public double getTotalPointsHome(int round) {
		double sum = 0;

		for (int i=0; i<=round; i++) {
			if (i < this.totalPointsHomeThisSeason.size()) {
				sum += this.totalPointsHomeThisSeason.get(i);
			}
		}
		return sum;
	}

	/**
	 * Returns amount of points taken away by a specific round
	 * @param round 
	 * @return sum of total points taken away
	 */
	public double getTotalPointsAway(int round) {
		double sum = 0;

		for (int i = 0; i<=round; i++) {
			if (i < this.totalPointsAwayThisSeason.size()) {
				sum += this.totalPointsAwayThisSeason.get(i);
			}
		}
		return sum;
	}

	public double getLocationAndPointsPoints(int round) {
		return locationAndPoints.get(round).getPoints();
	}

	public double getLocationAndPointsLocation(int round) {
		return locationAndPoints.get(round).getLocation();
	}

	/**
	 * Creates an array containing all stats of a team which is used by the AI to calculate 
	 * how good that team is
	 * @param currentRound
	 * @param number number of games it checks when calculating average
	 * @return array with all parameters for the team
	 */
	public double[] createInputArray(int currentRound, int number) {
		if(currentRound == -1) {
			currentRound = matchesPlayed-1;
		}
		double[] inputArray = new double[11];
		inputArray[0] = getSumOfGoals(currentRound);	
		inputArray[1] = Math.abs(getSumOfGoalsAgainst(currentRound));
		inputArray[2] = getTotalPoints(currentRound);
		inputArray[3] = getTotalPointsHome(currentRound);
		inputArray[4] = getTotalPointsAway(currentRound);
		inputArray[5] = getLocationAndPointsPoints(currentRound);
		inputArray[6] = pointsLastNGames(currentRound, number);
		inputArray[7] = getTablePosition(currentRound);
		inputArray[8] = goalsForLastNGames(currentRound, number);
		inputArray[9] = Math.abs(goalsAgainstLastNGames(currentRound, number));
		inputArray[10] = getLocationAndPointsLocation(currentRound); 
		return inputArray;
	}

	public String toString() {
		return this.name;
	}

	/**
	 * Defines how the teams are sorted in the league table by comparing with another team 
	 */
	public int compareTo(TeamOld otherTeam) {
		double thisPoints = this.getTotalPoints(this.getMatchesPlayed()-1);
		double otherPoints = otherTeam.getTotalPoints(otherTeam.getMatchesPlayed()-1);
		double thisGoalDiff = this.getSumOfGoals(this.getMatchesPlayed()-1) - this.getSumOfGoalsAgainst(this.getMatchesPlayed()-1);
		double otherGoalDiff = otherTeam.getSumOfGoals(otherTeam.getMatchesPlayed()-1) - otherTeam.getSumOfGoalsAgainst(otherTeam.getMatchesPlayed()-1);
		double thisGoals = this.getSumOfGoals(this.getMatchesPlayed()-1);
		double otherGoals = otherTeam.getSumOfGoals(otherTeam.getMatchesPlayed()-1);

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