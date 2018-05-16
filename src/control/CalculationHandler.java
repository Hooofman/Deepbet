package control;

import java.util.ArrayList;

import org.neuroph.core.data.DataSet;

import boundary.ConnectDatabase;
import boundary.PrintListener;
import control.LeagueCreator;
import control.Norm;
import control.ProduceOutput;
import entity.League;
import entity.Match;
import entity.Season;

/**
 * Handles the calculation of the network. Decides what matches to test and what to not test. Connects to the database
 * and updates this aswell.
 * 
 * @author Sven Lindqvist, Oscar Malmqvist, Johannes Roos och Oskar Engström Magnusson.
 *
 */
public class CalculationHandler extends Thread implements PrintListener {
	private static Norm norm = new Norm();
	private Controller controller;
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

	/**
	 * Constructor that sets all the parameters
	 * 
	 * @param controller the controller connected to this class
	 * @param iterations Number of interations
	 * @param learningRate Learningrate
	 * @param momentum Momentum
	 * @param searchPath Path to the .nnet file to use in calculation
	 * @param datasetName Name of the dataset to use
	 * @param finalNNName Path to where the .nnet will be saved after calculations
	 * @param leagueName Leaguename
	 * @param leagueAPIid Array of API-IDs to get data from
	 * @param table Table to save the data in in the database
	 * @param connection The connections to use to connecct to the database
	 */
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
	}

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
			System.out.println(e + "\nERROR! INCORRECT INPUT IN LEAGUE INPUT");
			this.interrupt();
		}
		calculate();
	}

	/**
	 * Calculates the outcome of the upcoming matches and puts them into the database
	 */
	public void calculate() {
		LeagueCreator ligaSkapare = null;
	
		connection.connect();
		System.out.println("Database connected");
		
		System.out.println("Starts leaguecreator..");
		ligaSkapare = new LeagueCreator(leagueName, leagueAPIId, datasetName);
		ligaSkapare.createLeague();

		// Get the created league
		League league = ligaSkapare.getLeague();

		// Load the trainingset
		DataSet trainingSet = DataSet.load(datasetName);
		System.out.println("Dataset loaded..");

		// Train the AI with the trainingset
		System.out.println("Starts ai-handler..");
		new AIHand(this).trainNetwork(trainingSet, norm, iterations, learningRate, momentum, searchPath, finalNNName);
		ArrayList<Season> seasons = league.getSeasons(); // Get the seasons from league
		ArrayList<Match> matchesToTest = new ArrayList<Match>(); // Create a list that will contain the upcoming matches
		Season seasonToTest = seasons.get(seasons.size() - 1); // The current season of the league
		ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches(); // List of all the matches in the season

		// Get the upcoming matches and add them to the list of matches that will be tested
		System.out.println("Starts updating old matches and produce output for unplayed matches..");
		for (Match match : matchesFromSeason) {
			if (match.getStatus().equals("TIMED")) {
				matchesToTest.add(match);
			} else if (match.getStatus().equals("FINISHED")) {
				connection.updateCalculatedMatches(match);
			}
		}
		
		// Produce the calculation for each match and save it to the database.
		System.out.println("Starts ProduceOutput");
		ProduceOutput produceOutput = new ProduceOutput(finalNNName);
		
		for (Match match : matchesToTest) {
			produceOutput.getOutputForMatch(match, norm);
			connection.insertIntoTable(table);
			connection.createNewMatch(match, seasonToTest.getYear(), league.getName());
		}

		// Close the db-conection
		connection.disconnect();
		System.out.println("Db-connection closed");
		controller.enableButtons();
		System.out.println("Completed!");
	}

	@Override
	public void updateText(String text) {
		controller.addToConsoleText(text);
	}

	/**
	 * Updates the progressbar in the GUI
	 */
	public void updateProgress(int current, int max) {
		controller.updateProgress(current, max);
	}
}
