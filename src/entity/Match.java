package entity;

import java.util.Date;

import org.json.JSONException;

import control.OddsHandler2;

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
	private Odds odds;
	private long sportsMonksId;
	private char recommendation;
	private int strengthRec;
	private String time;
	
	private String status;
	private double calcOutput[];

	public Match(Team homeTeam, Team awayTeam, int round) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.round = round;
		// this.homeTeam.setLocation(1);
		// this.awayTeam.setLocation(0);
		this.isFinished = false;
		this.homeOutcome = 0;
		this.awayOutcome = 0;
		this.drawOutcome = 0;
		this.calcOutput = new double[2];
	}

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

	public void setSportMonksId(long id) {
		this.sportsMonksId = id;
	}

	public long getSportsMonksId() {
		return sportsMonksId;
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

	public void setIsFinished(String status) {
		if (!status.equals("TIMED") || !status.equals("SCHEDULED") || !status.equals("POSTPONED")) {
			isFinished = true;
			homeTeam.addPlayedMatch();
			awayTeam.addPlayedMatch();
		} else {
			isFinished = false;
		}
	}

	public boolean getIsFinished() {
		return isFinished;
	}

	public void setOutcome() {
		if (homeGoals > awayGoals) {
			outcome = 1; // Hemmalaget vunnit matche
			homeOutcome = 1;
			homeTeam.setOutcome(1);
			awayTeam.setOutcome(0);
			homeTeam.setPointsAndLocation(1, 3);
			awayTeam.setPointsAndLocation(0, 0);
		} else if (awayGoals > homeGoals) {
			outcome = -1; // Bortalaget vunnit matchen
			awayOutcome = 1;
			awayTeam.setOutcome(1);
			homeTeam.setOutcome(0);
			homeTeam.setPointsAndLocation(1, 0);
			awayTeam.setPointsAndLocation(0, 3);
		} else {
			outcome = 0;
			drawOutcome = 1;
			homeTeam.setOutcome(0.5);
			awayTeam.setOutcome(0.5);
			homeTeam.setPointsAndLocation(1, 1);
			awayTeam.setPointsAndLocation(0, 1);
		}
	}

	public double[] getMatchArray(int number) {
		double res[] = new double[22];
		double homeTeam[] = this.homeTeam.createInputArray(this.homeTeam.getMatchesPlayed()-1, number);
		double awayTeam[] = this.awayTeam.createInputArray(this.awayTeam.getMatchesPlayed()-1, number);
		for (int i = 0; i < 11; i++) {
			res[i] = homeTeam[i];
			res[i + 11] = awayTeam[i];
		}
		return res;
	}

	public double[] get1X2Outcome() {
		double[] outcome = { this.homeOutcome, this.drawOutcome, this.awayOutcome };
		return outcome;
	}

	public void setRecommendation(char rec) {
		this.recommendation = rec;
	}

	public char getRecommendation() {
		return recommendation;
	}

	public void setStrengthRec(int strengthRec) {
		this.strengthRec = strengthRec;
	}

	public int getStrengthRec() {
		return strengthRec;
	}
}
