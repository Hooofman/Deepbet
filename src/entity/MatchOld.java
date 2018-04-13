package entity;

import java.util.Date;
import org.json.JSONException;
/**
 * Class for creating a Match object
 * @author 
 *
 */
public class MatchOld {
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
	private Odds odds;
	private char recommendation;
	private int strengthRec;
	private String time;
	
	private String status;
	private double calcOutput[];
	
	/**
	 * Constructs a Match object
	 * @param homeTeam home team
	 * @param awayTeam away team
	 * @param round round in which the match is played 
	 */
	public MatchOld(Team homeTeam, Team awayTeam, int round) {
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
	 * Creates an Odds object for the match with the latest odds
	 */
	public void createOdds() {
		this.odds = new Odds();
		try {
			odds.setOddsHome(OddsHandler2.getHomeOddsMap(homeTeam.getShortName(), awayTeam.getShortName()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setCalcOutput(double[] output) {
		this.calcOutput = output;
	}

	public double[] getCalcOutput() {
		return calcOutput;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setHomeTeam(Team team) {
		this.homeTeam = team;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setAwayTeam(Team team) {
		this.awayTeam = team;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setHomeGoals(int goals) {
		this.homeGoals = goals;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public void setAwayGoals(int goals) {
		this.awayGoals = goals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getRound() {
		return round;
	}

	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}

	public int getOutcome() {
		return outcome;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}
	
	/**
	 * Set the status of a match to finished after it is played  
	 * @param status status of Match
	 */
	public void setIsFinished(String status) {
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
		} else if (awayGoals > homeGoals) {
			outcome = -1; // The away team has won the match
			awayOutcome = 1;
			awayTeam.setOutcome(1);
			homeTeam.setOutcome(0);
			homeTeam.setPointsAndLocation(1, 0); // 1 for playing home, 0 points for a loss
			awayTeam.setPointsAndLocation(0, 3); // 0 for playing away, 3 points for a win
		} else {
			outcome = 0; // The match is a draw
			drawOutcome = 1;
			homeTeam.setOutcome(0.5);
			awayTeam.setOutcome(0.5);
			homeTeam.setPointsAndLocation(1, 1); // 1 for playing home, 1 point for a draw
			awayTeam.setPointsAndLocation(0, 1); // 0 for playing away, 1 point for a draw
		}
	}
	
	/**
	 * Returns an array containing all parameters in a Match object which is 
	 * used by the neural network
	 * @param number specifying how many matches back it should check
	 * @return array of parameters in a Match object 
	 */
	public double[] getMatchArray(int number) {
		double res[] = new double[22];
		double homeTeam[] = this.homeTeam.createInputArray(this.homeTeam.getMatchesPlayed()-2, number);
		double awayTeam[] = this.awayTeam.createInputArray(this.awayTeam.getMatchesPlayed()-2, number);
		for (int i = 0; i < 11; i++) {
			res[i] = homeTeam[i];
			res[i + 11] = awayTeam[i];
		}
		return res;
	}

	/**
	 * Returns an array with the outcome of a match
	 * @return 
	 */
	public double[] get1X2Outcome() {
		double[] outcome = { this.homeOutcome, this.drawOutcome, this.awayOutcome };
		return outcome;
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
}
