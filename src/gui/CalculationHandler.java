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

	
	public CalculationHandler(Controller controller, int iterations, double learningRate, double momentum, String searchPath, String datasetName, String finalNNName, String leagueName, String[] leagueAPIid, String table) {
		this.controller = controller;
		this.iterations = iterations;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.searchPath = searchPath;
		this.datasetName = datasetName;
		this.finalNNName = finalNNName;
		this.leagueName = leagueName;
		this.table = table;
		this.leagueAPIId = convertStringToIntArray(leagueAPIid);
	}
	
	public int[] convertStringToIntArray(String[] strArray) {
		int[] array = new int[strArray.length];
		for (int i = 0; i<array.length; i++) {
			array[i] = Integer.parseInt(strArray[i]);
		}
		return array;
	}
	
	public void run() {
		calculate();
	}

	public void calculate() {
		// Ids used by the API to get the seasons
		// int[] plApiId = { 113, 114, 4, 301, 341, 354, 398, 426, 445 };
		// int[] plApiId = {398, 426, 445};
		// int[] plApiId = {445};
		LeagueCreator ligaSkapare = null;

		// Create db-connection
		ConnectDatabase connection = new ConnectDatabase();
		connection.connect();

		// Create the leagueCreator and start it

		ligaSkapare = new LeagueCreator(leagueName, leagueAPIId, this, datasetName);

		ligaSkapare.createLeague();

		////////////////////////////////////////////////////////////////// Test

		// Get the created league
		League league = ligaSkapare.getLeague();

		// Load the trainingset
		DataSet trainingSet = DataSet.load(datasetName);

		// Train the AI with the trainingset
		new AIHand(this).trainNetwork(trainingSet, norm, iterations, learningRate, momentum, searchPath, finalNNName);
		ArrayList<Season> seasons = league.getSeasons(); // Get the seasons from league
		ArrayList<Match> matchesToTest = new ArrayList<Match>(); // Create a list that will contain the upcoming matches
		Season seasonToTest = seasons.get(seasons.size() - 1); // The current season of the league
		ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches(); // List of all the matches in the season
		//
		// Get the upcoming matches and add them to the list of matches that will be
		// tested

		updateText(matchesFromSeason.size() + "");
		updateText(seasonToTest.toString());
		updateText(seasons.size() + "");
		updateText(matchesFromSeason.get(1).getStatus());
		for (Match match : matchesFromSeason) {
			if (match.getStatus().equals("TIMED")) {
				matchesToTest.add(match);
			} else if (match.getStatus().equals("FINISHED")) {
				connection.updateCalculatedMatches(match);
			}
		}

		ProduceOutput produceOutput = new ProduceOutput(this, finalNNName);
		// Produce the calculation for each match and save it to the database

		////////////////////// Finns inga matcher att testa!
		for (Match match : matchesToTest) {
			produceOutput.getOutputForMatch(match, norm);
			connection.insertIntoTable(table);
			connection.createNewMatch(match, seasonToTest.getYear(), league.getName());
		}

		// Close the db-conection
		connection.disconnect();
	}

	@Override
	public void updateText(String text) {
		controller.addToConsoleText(text);
	}
}