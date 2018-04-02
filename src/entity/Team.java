package entity;

import java.util.ArrayList;

import control.LocationAndPoint;

public class Team implements Comparable<Team>{

	private int matchesPlayed;

	private ArrayList<Integer> goalsFor;
	private ArrayList<Integer> goalsAgainst;
	private ArrayList<Integer> tablePosition;
	private ArrayList<Double> outcome; // Defines whether the team won or lost the game. 1 for win, 0.5 for draw, 0 for loss
	private ArrayList<LocationAndPoint> locationAndPoints;
	private ArrayList<Integer> goalsForThisSeason;
	private ArrayList<Integer> goalsAgainstThisSeason;
	private ArrayList<Integer> totalPointsThisSeason;
	private ArrayList<Integer> totalPointsHomeThisSeason;
	private ArrayList<Integer> totalPointsAwayThisSeason;

	private String name;
	private String shortName;
	private static int id;

	public Team(String name) {
		this.name = name;
		this.goalsFor = new ArrayList<Integer>();
		this.goalsAgainst = new ArrayList<Integer>();
		this.tablePosition = new ArrayList<Integer>();
		this.outcome = new ArrayList<Double>();
		this.locationAndPoints = new ArrayList<LocationAndPoint>();
		this.matchesPlayed = 0;
		this.goalsForThisSeason = new ArrayList<Integer>();
		this.goalsAgainstThisSeason = new ArrayList<Integer>();
		this.totalPointsThisSeason = new ArrayList<Integer>();
		this.totalPointsHomeThisSeason = new ArrayList<Integer>();
		this.totalPointsAwayThisSeason = new ArrayList<Integer>();
	}

	public void addPlayedMatch() {
		this.matchesPlayed++;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setLocation(int location) {
		this.locationAndPoints.add(new LocationAndPoint(location));
	}

	public int getLocationForASpecificRound(int index) {
		return locationAndPoints.get(index).getLocation();
	}

	public void setOutcome(double outcome) {
		this.outcome.add(outcome);
	}

	public double getOutcomeForASpecificRound(int index) {
		return outcome.get(index);
	}

	public void setGoalsFor(int value) {
		this.goalsFor.add(value);
		this.goalsForThisSeason.add(value);
	}

	public int getGoalsFor(int index) {
		return goalsFor.get(index);
	}

	public void setGoalsAgainst(int value) {
		this.goalsAgainst.add(value);
		this.goalsAgainstThisSeason.add(value);
	}

	public int getGoalsAgainst(int index) {
		return goalsAgainst.get(index);
	}

	public void setPointsAndLocation(int location, int points) {
		if (location == 1) {
			this.totalPointsHomeThisSeason.add(points);
		} else if (location == 0) {
			this.totalPointsAwayThisSeason.add(points);
		}
		this.locationAndPoints.add(new LocationAndPoint(location, points));
		this.totalPointsThisSeason.add(points);
	}

	public void setPoints(int index, int value) {
		this.locationAndPoints.add(this.locationAndPoints.get(index).setPoints(value));
	}

	public LocationAndPoint getPoints(int index) {
		return locationAndPoints.get(index);
	}

	public void setTablePosition(int value) {
		this.tablePosition.add(value);
	}

	public double getTablePosition(int index) {
		return tablePosition.get(index);
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
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum += this.locationAndPoints.get(i).getPoints();
				avgNbr++;
			}
		}

		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	public double goalsForLastNGames(int round, int number) {
		double sum = 0;
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum += this.goalsFor.get(i);
				avgNbr++;
			}
		}
		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	public double goalsAgainstLastNGames(int round, int number) {
		double sum = 0;
		int avgNbr = 0;

		for (int i=round; i>=round-number; i--) {
			if (i>=0) {
				sum -= this.goalsAgainst.get(i);
				avgNbr++;
			}
		}
		if (avgNbr != 0) {
			return sum / avgNbr;
		}

		return 0;
	}

	public double getSumOfGoals(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.goalsForThisSeason.size()) {
				sum += this.goalsForThisSeason.get(i);
			}
		}
		return sum;
	}

	public double getSumOfGoalsAgainst(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.goalsAgainstThisSeason.size()) {
				sum -= this.goalsAgainstThisSeason.get(i);
			}
		}

		return sum;
	}

	public double getTotalPoints(int round) {
		double sum = 0;
		for (int i=0; i<=round; i++) {
			if (i < this.totalPointsThisSeason.size()) {
				sum += this.totalPointsThisSeason.get(i);
			}
		}
		return sum;
	}

	public double getTotalPointsHome(int round) {
		double sum = 0;

		for (int i=0; i<=round; i++) {
			if (i < this.totalPointsHomeThisSeason.size()) {
				sum += this.totalPointsHomeThisSeason.get(i);
			}
		}
		return sum;
	}

	public double getTotalPointsAway(int round) {
		double sum = 0;

		for (int i = 0; i<=round; i++) {
			if (i < this.totalPointsAwayThisSeason.size()) {
				sum += this.totalPointsAwayThisSeason.get(i);
			}
		}
		return sum;
	}

	public double getLocationAndPointsPoints(int round) {
		return locationAndPoints.get(round).getPoints();
	}

	public double getLocationAndPointsLocation(int round) {
		return locationAndPoints.get(round).getLocation();
	}

	public double[] createInputArray(int currentRound, int number) {
		double[] inputArray = new double[11];
		inputArray[0] = getSumOfGoals(currentRound);	
		inputArray[1] = getSumOfGoalsAgainst(currentRound);
		inputArray[2] = getTotalPoints(currentRound);
		inputArray[3] = getTotalPointsHome(currentRound);
		inputArray[4] = getTotalPointsAway(currentRound);
		inputArray[5] = getLocationAndPointsPoints(currentRound);	//Osäker på vad detta är
		inputArray[6] = pointsLastNGames(currentRound, number);
		inputArray[7] = getTablePosition(currentRound);
		inputArray[8] = goalsForLastNGames(currentRound, number);
		inputArray[9] = goalsAgainstLastNGames(currentRound, number);
		inputArray[10] = getLocationAndPointsLocation(currentRound); //Osäker på vad detta är
		return inputArray;
	}

	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Team otherTeam) {
		double thisPoints = this.getTotalPoints(this.getMatchesPlayed()-1);
		double otherPoints = otherTeam.getTotalPoints(otherTeam.getMatchesPlayed()-1);
		double thisGoalDiff = this.getSumOfGoals(this.getMatchesPlayed()-1) - this.getSumOfGoalsAgainst(this.getMatchesPlayed()-1);
		double otherGoalDiff = otherTeam.getSumOfGoals(otherTeam.getMatchesPlayed()-1) - otherTeam.getSumOfGoalsAgainst(otherTeam.getMatchesPlayed()-1);
		double thisGoals = this.getSumOfGoals(this.getMatchesPlayed()-1);
		double otherGoals = otherTeam.getSumOfGoals(otherTeam.getMatchesPlayed()-1);

		if( thisPoints > otherPoints ) {
			return -1;
		}else if( thisPoints < otherPoints) {
			return 1;
		}else if(thisGoalDiff > otherGoalDiff) {
			return -1;
		}else if(thisGoalDiff < otherGoalDiff) {
			return 1;
		}else if(thisGoals > otherGoals) {
			return -1;
		}else if(thisGoals < otherGoals) {
			return 1;
		}
		return 0;
	}

	public void resetForNewSeason() {
		this.goalsForThisSeason.clear();;
		this.goalsAgainstThisSeason.clear();
		this.totalPointsThisSeason.clear();
		this.totalPointsHomeThisSeason.clear();
		this.totalPointsAwayThisSeason.clear();
	}
}