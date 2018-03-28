package entity;

public class Team {

	private static int[] goalsFor;
	private static int[] goalsAgainst;
	private static int[] points;
	private static int[] pointsHome;
	private static int[] pointsAway;
	private static int[] tablePosition;
	private static int[] outcome; // Defines whether the team won or loss the game. 1 is for win, 0, for draw, -1 for loss
	private static int[] location; // Defines whether the team played at home or away in the game. 1 for home and 0 for away.
	private static String name;
	private static String shortName;
	private static int id;

	public Team(String name) {
		this.name = name;
	}
	
	public void setLocation(int round, int location) {
		this.location[round] = location;
	}
	
	public int getLocationForASpecificRound(int round) {
		return location[round];
	}
	
	public void setOutcome(int round, int outcome) {
		this.outcome[round]  = outcome;
	}
	
	public int getOutcomeForASpecificRound(int round) {
		return outcome[round];
	}
	
	public void setArrayLocation(int numberOfRounds) {
		location = new int[numberOfRounds];
	}
	
	public void setArrayOutcome(int numberOfRounds) {
		outcome = new int[numberOfRounds];
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

	public static int getGoalsFor(int round) {
		return goalsFor[round - 1];
	}

	public void setGoalsAgainst(int round, int value) {
		goalsAgainst[round - 1] = value;
	}

	public static int getGoalsAgainst(int round) {
		return goalsAgainst[round - 1];
	}

	public void setPoints(int round, int value) {
		points[round - 1] = value;
	}

	public static int getPoints(int round) {
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
	
	public static int getSumOfGoals(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum += goalsFor[i];
		}
		return sum;
	}
	
	public static int getSumOfGoalsAgainst(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum -= goalsFor[i];
		}
		return sum;
	}
	
	public static int getTotalPoints(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			sum += points[i];
		}
		return sum;
	}
	
	public static int getTotalPointsHome(int currentRound) {
		int sum = 0;
		for (int i=0; i<currentRound; i++) {
			if (pointsHome[i] != null) {
				sum += pointsHome[i];
			}
		}
		return sum;
	}
	
	public static int[] createInputArray(int currentRound) {
		int[] inputArray = new int[11];
		inputArray[0] = getSumOfGoals(currentRound);		
		inputArray[1] = getSumOfGoalsAgainst(currentRound);
		inputArray[2] = getTotalPoints(currentRound);
		inputArray[3] = getTotalPointsHome(currentRound);
	}

	public String toString() {
		return this.name;
	}

}