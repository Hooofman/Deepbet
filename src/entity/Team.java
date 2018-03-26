package entity;

public class Team {

	private int[] goalsFor = new int[38];
	private int[] goalsAgainst = new int[38];
	private int[] points = new int[38];
	private int[] pointsHome = new int[19];
	private int[] pointsAway = new int[19];
	private int[] tablePosition = new int[38];
	private String name;
	private int id;

	public Team(String name) {
		this.name = name; //
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

	public int pointsLastNGames(int round, int number) {

		return points[round] - points[round - number];
	}

	public String toString() {
		return this.name;
	}

}