package entity;

import java.util.Date;
import org.json.JSONException;
/**
 * Class for creating a Match object
 * @author Sven Lindqvist, Oscar Malmqvist, Johannes Roos och Oskar Engström
 *         Magnusson.
 *
 */
public class Match {
	private Team homeTeam;
	private Team awayTeam;
	private int homeGoals;
	private int awayGoals;
	private int round;
	private String date;
	private int outcome;
	private int homeOutcome;
	private int drawOutcome;
	private int awayOutcome;
	private boolean isFinished;
	private char recommendation;
	private int strengthRec;
	private String time;
	private char outcomeChar;
	
	private String status;
	private double calcOutput[];
	private double inputArray[];
	private double outputArray[];
	
	/**
	 * Constructs a Match object
	 * @param homeTeam home team
	 * @param awayTeam away team
	 * @param round round in which the match is played 
	 */
	public Match(Team homeTeam, Team awayTeam, int round) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.round = round;
		this.isFinished = false;
		this.homeOutcome = 0;	// 1 for a win, -1 for a loss and 0 for a draw
		this.awayOutcome = 0;	// 1 for a win, -1 for a loss and 0 for a draw
		this.drawOutcome = 0;	// 1 for a win, -1 for a loss and 0 for a draw
		this.calcOutput = new double[2]; // Saves the output for the match in an array
	}
	/**
	 * Sets the time the match is or was played
	 * @param time Time as a String
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * Returns the time the match is or was played
	 * @return time as a String
	 */
	public String getTime() {
		return time;
	}
	/**
	 * Sets the calculated output for this match 
	 * @param output Array of calculated outputs
	 */
	public void setCalcOutput(double[] output) {
		this.calcOutput = output;
	}
	/**
	 * Returns the calculated output for this match
	 * @return Array of calculated outputs
	 */
	public double[] getCalcOutput() {
		return calcOutput;
	}
	/**
	 * Returns the status for this match
	 * @return Status, as a String
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the hometeam for this match
	 * @param team as a Team
	 */
	public void setHomeTeam(Team team) {
		this.homeTeam = team;
	}
	/**
	 * Returns the hometeam for this match
	 * @return homeTeam as a Team
	 */
	public Team getHomeTeam() {
		return homeTeam;
	}
	/**
	 * Sets the awayteam for this match
	 * @param awayteam as a Team
	 */
	public void setAwayTeam(Team team) {
		this.awayTeam = team;
	}
	/**
	 * Returns the awayteam for this match
	 * @return awayteam as a Team
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}
	/**
	 * Sets numbers of goals scored by hometeam
	 * @param goals number of goals
	 */
	public void setHomeGoals(int goals) {
		this.homeGoals = goals;
	}
	/**
	 * Returns numbers of goals scored by hometeam
	 * @return goals number of goals
	 */
	public int getHomeGoals() {
		return homeGoals;
	}
	/**
	 * Sets numbers of goals scored by awayteam
	 * @param goals number of goals
	 */
	public void setAwayGoals(int goals) {
		this.awayGoals = goals;
	}
	/**
	 * Returns numbers of goals scored by awayteam
	 * @return goals number of goals
	 */
	public int getAwayGoals() {
		return awayGoals;
	}
	/**
	 * Sets which round this match was played in
	 * @param round roundnumber
	 */
	public void setRound(int round) {
		this.round = round;
	}
	/**
	 * Returns which round this match was played in
	 * @return roundNumber
	 */
	public int getRound() {
		return round;
	}
	/**
	 * Sets the outcome of this Match
	 * 1 = hometeam won
	 * 0 = draw
	 * -1 = awayteam won
	 * @param outcome outcome
	 */
	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}
	/**
	 * Returns the outcome of this Match
	 * 1 = hometeam won
	 * 0 = draw
	 * -1 = awayteam won
	 * @return outcome
	 */
	public int getOutcome() {
		return outcome;
	}
	/**
	 * Sets the date this Match was played
	 * @param date, date as String
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	  * Returns the date this Match was played 
	 * @return date as a String
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Returns a char instead of and int which represent the outcome of the Match
	 * 1 = hometeam won
	 * X = draw
	 * 2 = Awayteam won
	 * @return
	 */
	public char getOutcomeChar() {
		return outcomeChar;
	}
	
	/**
	 * Set the status of a match to finished after it is played  
	 * @param status status of Match
	 */
	public void setIsFinished(String status) {
		this.status = status;
		if (!status.equals("TIMED") || !status.equals("SCHEDULED") || !status.equals("POSTPONED")) {
			isFinished = true;
			homeTeam.addPlayedMatch();
			awayTeam.addPlayedMatch();
		} else {
			isFinished = false;
		}
	}
	
	/**
	 * Check if a match is finished or not
	 * @return true if finished. Otherwise false
	 */
	public boolean getIsFinished() {
		return isFinished;
	}
	
	/**
	 * Sets the outcome of a match based on goals for home/away team
	 */
	public void setOutcome() {
		if (homeGoals > awayGoals) {
			outcome = 1; // The home team has won the match
			homeOutcome = 1; 
			homeTeam.setOutcome(1);
			awayTeam.setOutcome(0);
			homeTeam.setPointsAndLocation(1, 3); // 1 for playing home, 3 points for a win 
			awayTeam.setPointsAndLocation(0, 0); // 0 for playing away, 0 points for a loss
			outcomeChar = '1';
		} else if (awayGoals > homeGoals) {
			outcome = -1; // The away team has won the match
			awayOutcome = 1;
			awayTeam.setOutcome(1);
			homeTeam.setOutcome(0);
			homeTeam.setPointsAndLocation(1, 0); // 1 for playing home, 0 points for a loss
			awayTeam.setPointsAndLocation(0, 3); // 0 for playing away, 3 points for a win
			outcomeChar = '2';
		} else {
			outcome = 0; // The match is a draw
			drawOutcome = 1;
			homeTeam.setOutcome(0.5);
			awayTeam.setOutcome(0.5);
			homeTeam.setPointsAndLocation(1, 1); // 1 for playing home, 1 point for a draw
			awayTeam.setPointsAndLocation(0, 1); // 0 for playing away, 1 point for a draw
			outcomeChar = 'X';
		}
	}
	
	/**
	 * Produces a inputArray to the AI by combining two inputarrays from the home and away team
	 * @param number of games it checks when calculating average in Team-object
	 */
	public void produceInputArray(int number) {
		double[] array= new double[22];
		
		double homeTeam[] = this.homeTeam.createInputArray(number);
		double awayTeam[] = this.awayTeam.createInputArray(number);
		for (int i = 0; i < 11; i++) {
			array[i] = homeTeam[i];
			array[i + 11] = awayTeam[i];
		}
		
		inputArray = array;
	}
	
	/**
	 * Returns an array containing all parameters in a Match object which is 
	 * used by the neural network
	 * @param number specifying how many matches back it should check
	 * @return array of parameters in a Match object 
	 */
	public double[] getMatchArray() {
		return inputArray;
	}

	/**
	 * Produces an outputarray based on the outcome, used in the AI when training
	 */
	public void produceOutputArray() {
		this.outputArray = new double[] { this.homeOutcome, this.drawOutcome, this.awayOutcome };
	}
	/**
	 * Returns an array with the outcome of a match
	 * @return 
	 */
	public double[] get1X2Outcome() {
		return outputArray;
	}
	
	/**
	 * Set a recommendation for a match based on the output from the AI
	 * @param rec 1 for home win, X for draw, 2 for away win
	 */
	public void setRecommendation(char rec) {
		this.recommendation = rec;
	}
	
	public char getRecommendation() {
		return recommendation;
	}
	
	/**
	 * Sets how confident the recommendation is based on the AI-output
	 * @param strengthRec a scale from 1-5. 1 is the worst, 5 is the best
	 */
	public void setStrengthRec(int strengthRec) {
		this.strengthRec = strengthRec;
	}

	public int getStrengthRec() {
		return strengthRec;
	}
	
	public String toString() {
		return homeTeam.getName() + " - " + awayTeam.getName();
	}
}
