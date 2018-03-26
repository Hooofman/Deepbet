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
	private Date date;
	private int outcome;
	private boolean isFinished;
	private Odds odds;
	private long sportsMonksId;

	public Match(Team homeTeam, Team awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.isFinished = false;
	}
	
	public void createOdds() {
		this.odds = new Odds();
		try {
			odds.setOddsHome(OddsHandler2.getHomeOddsMap(homeTeam.getShortName(), awayTeam.getShortName()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
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

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
	
	public void setIsFinished(String status) {
		if (status.equals("FINISHED")) {
			isFinished = true;
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
		} else if (awayGoals > homeGoals) {
			outcome = -1; // Bortalaget vunnit matchen
		} else {
			outcome = 0;
		}
	}
}
