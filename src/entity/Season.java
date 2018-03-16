package entity;

public class Season {
	private int year;
	private Match[] matches;
	private int id;
	private int numberOfRounds;
	private Team[] teams;

	public Season() {

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Match[] getMatches() {
		return matches;
	}

	public void setMatches(Match[] matches) {
		this.matches = matches;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}

	public Team[] getTeams() {
		return teams;
	}

	public void setTeams(Team[] teams) {
		this.teams = teams;
	}
}
