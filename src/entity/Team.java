package entity;

import java.util.ArrayList;

public class Team {

	
	private ArrayList<Integer> goalsFor;
	private ArrayList<Integer> goalsAgainst;
	private ArrayList<Integer> points;
	private ArrayList<Integer> pointsHome;
	private ArrayList<Integer> pointsAway;
	private ArrayList<Integer> tablePosition;
	private ArrayList<Integer> outcome; // Defines whether the team won or loss the game. 1 is for win, 0, for draw, -1 for loss
	private ArrayList<Integer> location; // Defines whether the team played at home or away in the game. 1 for home and 0 for away.
//	private static int[] goalsFor;
//	private static int[] goalsAgainst;
//	private static int[] points;
//	private static int[] pointsHome;
//	private static int[] pointsAway;
//	private static int[] tablePosition;
//	private static int[] outcome; 
//	private static int[] location; 
	private static String name;
	private static String shortName;
	private static int id;
	private int homeRoundIndex;
	private int awayRoundIndex;

	public Team(String name) {
		this.name = name;
		goalsFor = new ArrayList<Integer>();
		goalsAgainst = new ArrayList<Integer>();
		points = new ArrayList<Integer>();
		pointsHome = new ArrayList<Integer>();
		pointsAway = new ArrayList<Integer>();
		tablePosition = new ArrayList<Integer>();
		outcome = new ArrayList<Integer>();
		location = new ArrayList<Integer>();
	}
	
	public void setLocation(int round, int location) {
		this.location.set(round, location);
	}
	
	public int getLocationForASpecificRound(int round) {
		return location.get(round);
	}
	
	public void setOutcome(int round, int outcome) {
		this.outcome.set(round, outcome);
	}
	
	public int getOutcomeForASpecificRound(int round) {
		return outcome.get(round);
	}

	public void setGoalsFor(int round, int value) {
		this.goalsFor.set(round, value);
	}

	public int getGoalsFor(int round) {
		return goalsFor.get(round);
	}

	public void setGoalsAgainst(int round, int value) {
		this.goalsAgainst.set(round, value);
	}

	public int getGoalsAgainst(int round) {
		return goalsAgainst.get(round);
	}

	public void setPoints(int round, int value) {
		this.points.set(round, value);
	}

	public int getPoints(int round) {
		return points.get(round);
	}

	public void setPointsHome(int value) {
		this.pointsHome.add(value);
	}

	public int getPointsHome(int round) {
		return pointsHome.get(round);
	}

	public void setPointsAway(int round, int value) {
		this.pointsAway.set(round, value);
	}

	public void setTablePosition(int round, int value) {
		this.tablePosition.set(round, value);
	}

	public int getTablePosition(int round) {
		return tablePosition.get(round);
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
		int sum = 0;
		
		for (int i=round; i>round-number; i--) {
			sum += points.get(i);
		}
		
		return sum / number;
	}
	
	public int goalsForLastNGames(int round, int number) {
		int sum = 0;
		
		for (int i=round; i>round-number; i--) {
			sum += goalsFor.get(i);
		}
		
		return sum / number;
	}
	
	public int goalsAgainstLastNGames(int round, int number) {
		int sum = 0;
		
		for (int i=round; i>round-number; i--) {
			sum -= goalsAgainst.get(i);
		}
		
		return sum / number;
	}
	
	public int getSumOfGoals(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum += goalsFor.get(i);
		}
		return sum;
	}
	
	public int getSumOfGoalsAgainst(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum -= goalsAgainst.get(i);
		}
		
		return sum;
	}
	
	public int getTotalPoints(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum += points.get(i);
		}
		return sum;
	}
	
	public int getTotalPointsHome(int currentRound) {
		int sum = 0;
		
		for (int i=0; i<currentRound; i++) {
			if (location.get(i) == 1) {
				sum += points.get(i);
			}
		}
		return sum;
	}
	
	public int getTotalPointsAway(int currentRound) {
		int sum = 0;
		
		for (int i = 0; i<currentRound; i++) {
			if (location.get(i) == 0) {
				sum += points.get(i);
			}
		}
		return sum;
	}
	
	public double[] createInputArray(int currentRound) {
		double[] inputArray = new double[11];
		inputArray[0] = getSumOfGoals(currentRound);		
		inputArray[1] = getSumOfGoalsAgainst(currentRound);
		inputArray[2] = getTotalPoints(currentRound);
		inputArray[3] = getTotalPointsHome(currentRound);
		inputArray[4] = getTotalPointsAway(currentRound);
		inputArray[5] = points.get(currentRound);
		inputArray[6] = pointsLastNGames(currentRound, 5);
		inputArray[7] = tablePosition.get(currentRound);
		inputArray[8] = goalsForLastNGames(currentRound, 5);
		inputArray[9] = goalsAgainstLastNGames(currentRound, 5);
		inputArray[10] = location.get(currentRound);
		return inputArray;
	}

	public String toString() {
		return this.name;
	}

}