package gui;

import java.util.ArrayList;

import org.neuroph.core.data.DataSet;

import boundary.ConnectDatabase;
import control.AIHandler;
import control.LeagueCreator;
import control.Norm;
import control.ProduceOutput;
import entity.League;
import entity.Match;
import entity.Season;

public class CalculationHandler extends Thread implements PrintListener {
	private static Norm norm = new Norm();
	private Controller controller;
	private String text;
	private int iterations;
	private double learningRate;
	private double momentum;
	private String searchPath;
	private String datasetName;
	private String finalNNName;
	private String leagueName;
	private String table;
	private int[] leagueAPIId;
	private String[] temp; // Temporary holder for leagueAPIid.
	private ConnectDatabase connection;

	public CalculationHandler(Controller controller, int iterations, double learningRate, double momentum,
			String searchPath, String datasetName, String finalNNName, String leagueName, String[] leagueAPIid,
			String table, ConnectDatabase connection) {
		this.controller = controller;
		this.iterations = iterations;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.searchPath = searchPath;
		this.datasetName = datasetName;
		this.finalNNName = finalNNName;
		this.leagueName = leagueName;
		this.table = table;
		this.connection = connection;
		temp = leagueAPIid;
		start();
		// this.leagueAPIId = convertStringToIntArray(leagueAPIid);
	}

	// public int[] convertStringToIntArray(String[] strArray) {
	// int[] array = new int[strArray.length];
	// try {
	// for (int i = 0; i < array.length; i++) {
	// array[i] = Integer.parseInt(strArray[i]);
	// }
	//
	// } catch (NumberFormatException e) {
	// controller.addToConsoleText("ERROR! INCORRECT INPUT IN LEAGUE INPUT");
	// Thread.currentThread().interrupt();
	// }
	// return array;
	// }
	/**
	 * Converts the String[] temp to an int-array.
	 */
	public void run() {
		int[] array = new int[temp.length];
		try {
			for (int i = 0; i < array.length; i++) {
				array[i] = Integer.parseInt(temp[i]);
			}
			this.leagueAPIId = array;

		} catch (NumberFormatException e) {
			updateText(e + "\nERROR! INCORRECT INPUT IN LEAGUE INPUT");
			this.interrupt();
		}
		calculate();
	}

	public void calculate() {
		// Disable calc-button
		controller.toggleCalcButton();
		
		updateText("Calculation started");
		
		// Ids used by the API to get the seasons
		// int[] plApiId = { 113, 114, 4, 301, 341, 354, 398, 426, 445 };
		// int[] plApiId = {398, 426, 445};
		// int[] plApiId = {445};
		LeagueCreator ligaSkapare = null;

		// Create db-connection
		connection.connect();
		updateText("Database connection created");

		// Create the leagueCreator and start it
		ligaSkapare = new LeagueCreator(leagueName, leagueAPIId, this, datasetName);
		ligaSkapare.createLeague();
		updateText("Leaguecreator created league: " + leagueName);
		
		// Get the created league
		League league = ligaSkapare.getLeague();

		// Load the trainingset
		DataSet trainingSet = DataSet.load(datasetName);
		updateText("Dataset loaded: " + datasetName);
		
		// Train the AI with the trainingset
		updateText("Starts network training");
		new AIHand(this).trainNetwork(trainingSet, norm, iterations, learningRate, momentum, searchPath, finalNNName);
		updateText("Network training finished");
		
		updateText("Get all seasons from league..");
		ArrayList<Season> seasons = league.getSeasons(); // Get the seasons from league
		ArrayList<Match> matchesToTest = new ArrayList<Match>(); // Create a list that will contain the upcoming matches
		
		updateText("Get the current season from league..");
		Season seasonToTest = seasons.get(seasons.size() - 1); // The current season of the league
		
		updateText("Get all matches from current season..");
		ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches(); // List of all the matches in the season
		
		updateText("Starts getting upcoming matches and update already calculated matches in database..");
		// Get the upcoming matches and add them to the list of matches that will be tested
		for (Match match : matchesFromSeason) {
			if (match.getStatus().equals("TIMED")) {
				matchesToTest.add(match);
				updateText(match.toString() + " is upcoming. Will be calculated..");
			} else if (match.getStatus().equals("FINISHED")) {
				connection.updateCalculatedMatches(match);
			}
		}
		updateText("All upcoming matches fetched. Finished matches updated");

		updateText("Starts testing of upcoming matches..");
		ProduceOutput produceOutput = new ProduceOutput(this, finalNNName);
		// Produce the calculation for each match and save it to the database

		for (Match match : matchesToTest) {
			produceOutput.getOutputForMatch(match, norm);
			connection.insertIntoTable(table);
			connection.createNewMatch(match, seasonToTest.getYear(), league.getName());
		}
		updateText("All upcoming matches calculated.");

		// Close the db-conection
		connection.disconnect();
		updateText("Database connection closed");
		
		updateText("Calculation finished");
		
		// Enable calc-button
		controller.toggleCalcButton();
	}

	@Override
	public void updateText(String text) {
		controller.addToConsoleText(text);
	}
}
