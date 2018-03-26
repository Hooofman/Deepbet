package entity;

public class Team {

	private int[] goalsFor;
	private int[] goalsAgainst;
	private int[] points;
	private int[] pointsHome;
	private int[] pointsAway;
	private int[] tablePosition;
	private String name;
	private String shortName;
	private int id;

	public Team(String name) {
		this.name = name;
	}

	public void setArrayGoalsFor(int numberOfRounds) {
		goalsFor = new int[numberOfRounds];
	}

	public void setArrayGoalsAgainst(int numberOfRounds) {
		goalsAgainst = new int[numberOfRounds];
	}

	public void setArrayPoints(int numberOfRounds) {
		points = new int[numberOfRounds];
	}

	public void setArrayPointsHome(int numberOfRounds) {
		pointsHome = new int[numberOfRounds / 2];
	}

	public void setArrayPointAway(int numberOfRounds) {
		pointsAway = new int[numberOfRounds / 2];
	}

	public void setArrayTablePosition(int numberOfRounds) {
		tablePosition = new int[numberOfRounds];
	}

	public void setGoalsFor(int round, int value) {
		goalsFor[round - 1] = value;
	}

	public int getGoalsFor(int round) {
		return goalsFor[round - 1];
	}

	public void setGoalsAgainst(int round, int value) {
		goalsAgainst[round - 1] = value;
	}

	public int getGoalsAgainst(int round) {
		return goalsAgainst[round - 1];
	}

	public void setPoints(int round, int value) {
		points[round - 1] = value;
	}

	public int getPoints(int round) {
		return points[round - 1];
	}

	public void setPointsHome(int round, int value) {
		pointsHome[round - 1] = value;
	}

	public int getPointsHome(int round) {
		return pointsHome[round - 1];
	}

	public void setPointsAway(int round, int value) {
		pointsAway[round - 1] = value;
	}

	public void setTablePosition(int round, int value) {
		tablePosition[round - 1] = value;
	}

	public int getTablePosition(int round) {
		return tablePosition[round - 1];
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

	public int pointsLastNGames(int round, int number) {

		return points[round] - points[round - number];
	}

	public String toString() {
		return this.name;
	}

}