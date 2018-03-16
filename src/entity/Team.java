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
	
	public Team (String name) {
		this.name = name;
	}
	
	public void setGoalsFor(int round, int value) {
		goalsFor[round] = value;
	}
	
	public int getGoalsFor(int round) {
		return goalsFor[round];
	}
	
	public void setGoalsAgainst(int round, int value) {
		goalsAgainst[round] = value;
	}
	
	public int getGoalsAgainst(int round) {
		return goalsAgainst[round];
	}
	
	public void setPoints(int round, int value) {
		points[round] = value;
	}
	
	public int getPoints(int round) {
		return points[round];
	}
	
	public void setPointsHome(int round, int value) {
		pointsHome[round] = value;
	}
	
	public int getPointsHome(int round) {
		return pointsHome[round];
	}
	
	public void setPointsAway(int round, int value) {
		pointsAway[round] = value;
	}
	
	public void setTablePosition(int round, int value) {
		tablePosition[round] = value;
	}
	
	public int getTablePosition(int round) {
		return tablePosition[round];
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
	
}
