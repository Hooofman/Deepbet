package entity;

import java.util.ArrayList;

import control.LocationAndPoint;

public class Team {


	private ArrayList<Integer> goalsFor;
	private ArrayList<Integer> goalsAgainst;
	//private ArrayList<Integer> points;
	//private ArrayList<Integer> pointsHome;
	//private ArrayList<Integer> pointsAway;
	private ArrayList<Integer> tablePosition;
	private ArrayList<Double> outcome; // Defines whether the team won or loss the game. 1 is for win, 0, for draw, -1 for loss
	//private ArrayList<Integer> location; // Defines whether the team played at home or away in the game. 1 for home and 0 for away.

	private ArrayList<LocationAndPoint> locationAndPoints;


	//	private static int[] goalsFor;
	//	private static int[] goalsAgainst;
	//	private static int[] points;
	//	private static int[] pointsHome;
	//	private static int[] pointsAway;
	//	private static int[] tablePosition;
	//	private static int[] outcome; 
	//	private static int[] location; 
	private String name;
	private String shortName;
	private static int id;
	private int homeRoundIndex;
	private int awayRoundIndex;

	public Team(String name) {
		this.name = name;
		this.goalsFor = new ArrayList<Integer>(38);
		this.goalsAgainst = new ArrayList<Integer>(38);
		//		this.points = new ArrayList<Integer>(38);
		//		this.pointsHome = new ArrayList<Integer>(38);
		//		this.pointsAway = new ArrayList<Integer>(38);
		this.tablePosition = new ArrayList<Integer>(38);
		this.outcome = new ArrayList<Double>(38);
		//this.location = new ArrayList<Integer>(38);
		this.locationAndPoints = new ArrayList<LocationAndPoint>(38);

		populateArrayLists();
	}

	private void populateArrayLists() {
		System.out.println(getName());
		for(int i = 0; i <= 38; i++) {
			this.goalsFor.add(null);
			this.goalsAgainst.add(null);
			//			this.points.add(null);
			//			this.pointsHome.add(null);
			//			this.pointsAway.add(null);
			this.tablePosition.add(null);
			this.outcome.add(null);
			this.locationAndPoints.add(null);
		}
	}

	public void setLocation(int round, int location) {
		this.locationAndPoints.set(round, new LocationAndPoint(location));
	}

	public int getLocationForASpecificRound(int round) {
		return locationAndPoints.get(round).getLocation();
	}

	public void setOutcome(int round, double outcome) {
		this.outcome.set(round, outcome);
	}

	public double getOutcomeForASpecificRound(int round) {
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
		this.locationAndPoints.set(round, this.locationAndPoints.get(round).setPoints(value));
	}

	public LocationAndPoint getPoints(int round) {
		return locationAndPoints.get(round);
	}

	//	public void setPointsHome(int value) {
	//		this.locationAndPoints.add(value);
	//	}
	//
	//	public int getPointsHome(int round) {
	//		return locationAndPoints.get(round);
	//	}
	//
	//	public void setPointsAway(int round, int value) {
	//		this.locationAndPoints.set(round, value);
	//	}

	public void setTablePosition(int round, int value) {
		this.tablePosition.set(round, value);
	}

	public double getTablePosition(int round) {
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

	public double pointsLastNGames(int round, int number) {
		double sum = 0;

		for (int i=round; i>round-number; i--) {
			if (i>0) {
				sum += locationAndPoints.get(i).getPoints();
			}
		}

		return sum / number;
	}

	public double goalsForLastNGames(int round, int number) {
		double sum = 0;

		for (int i=round; i>round-number; i--) {
			if (i>0) {
				sum += goalsFor.get(i);
			}
		}

		return sum / number;
	}

	public double goalsAgainstLastNGames(int round, int number) {
		double sum = 0;

		for (int i=round; i>round-number; i--) {
			if (i>0) {
				sum -= goalsAgainst.get(i);
			}
		}

		return sum / number;
	}

	public double getSumOfGoals(int currentRound) {
		double sum = 0;
		for (int i=1; i<currentRound; i++) {
			sum += goalsFor.get(i);
		}
		return sum;
	}

	public double getSumOfGoalsAgainst(int currentRound) {
		double sum = 0;
		for (int i=1; i<currentRound; i++) {
			sum -= goalsAgainst.get(i);
		}

		return sum;
	}

	public double getTotalPoints(int currentRound) {
		double sum = 0;
		for (int i=1; i<currentRound; i++) {
			sum += locationAndPoints.get(i).getPoints();
		}
		return sum;
	}

	public double getTotalPointsHome(int currentRound) {
		double sum = 0;

		for (int i=1; i<currentRound; i++) {
			if (locationAndPoints.get(i).getLocation() == 1) {
				sum += locationAndPoints.get(i).getPoints();
			}
		}
		return sum;
	}

	public double getTotalPointsAway(int currentRound) {
		double sum = 0;

		for (int i = 1; i<currentRound; i++) {
			if (locationAndPoints.get(i).getLocation() == 0) {
				sum += locationAndPoints.get(i).getPoints();
			}
		}
		return sum;
	}
	
	public double getLocationAndPointsPoints(int currentRound) {
		return locationAndPoints.get(currentRound).getPoints();
	}
	
	public double getLocationAndPointsLocation(int currentRound) {
		return locationAndPoints.get(currentRound).getLocation();
	}

	public double[] createInputArray(int currentRound) {
		double[] inputArray = new double[11];
		inputArray[0] = getSumOfGoals(currentRound);		
		inputArray[1] = getSumOfGoalsAgainst(currentRound);
		inputArray[2] = getTotalPoints(currentRound);
		inputArray[3] = getTotalPointsHome(currentRound);
		inputArray[4] = getTotalPointsAway(currentRound);
		inputArray[5] = getLocationAndPointsPoints(currentRound);	//Osäker på vad detta är
		inputArray[6] = pointsLastNGames(currentRound, 5);
		inputArray[7] = getTablePosition(currentRound);
		inputArray[8] = goalsForLastNGames(currentRound, 5);
		inputArray[9] = goalsAgainstLastNGames(currentRound, 5);
		inputArray[10] = getLocationAndPointsLocation(currentRound); //Osäker på vad detta är
		return inputArray;
	}

	public String toString() {
		return this.name;
	}

}