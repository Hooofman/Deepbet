package entity;

import java.util.Date;

public class Match {
	private Team homeTeam;
	private Team awayTeam;
	private int homeGoals;
	private int awayGoals;
	private int round;
	private Date date;
	private int outcome;

	public Match(Team homeTeam, Team awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
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
}
