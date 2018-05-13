package oldUnused;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;

import boundary.ConnectDatabase;
import entity.League;
import entity.Match;
import entity.Season;
import entity.LocationAndPoint;
import control.*;

/**
 * Main-class of the application in its current state. Produces calculations for
 * the currently upcoming fixtures
 *
 */
public class TestAIUpcoming {
	private static Norm norm = new Norm();

	public static void main(String[] args) {
		// Ids used by the API to get the seasons
		int[] plApiId = { 113, 114, 4, 301, 341, 354, 398, 426, 445 };
		// int[] plApiId = {398, 426, 445};
		// int[] plApiId = {445};
		LeagueCreator ligaSkapare = null;

		// Create db-connection
		ConnectDatabase connection = new ConnectDatabase();
		connection.connect();

		// Create the leagueCreator and start it
		ligaSkapare = new LeagueCreator("PL", plApiId);
		ligaSkapare.start();

		// Get the created league
		League league = ligaSkapare.getLeague();

		// Load the trainingset
		DataSet trainingSet = DataSet.load("test");

		// Train the AI with the trainingset
		new AIHandler().trainNetwork(trainingSet, norm);

		ArrayList<Season> seasons = league.getSeasons(); // Get the seasons from league
		ArrayList<Match> matchesToTest = new ArrayList<Match>(); // Create a list that will contain the upcoming matches
		Season seasonToTest = seasons.get(seasons.size() - 1); // The current season of the league
		ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches(); // List of all the matches in the season
		//
		// Get the upcoming matches and add them to the list of matches that will be
		// tested
		for (Match match : matchesFromSeason) {
			if (match.getStatus().equals("TIMED")) {
				matchesToTest.add(match);
			} else if (match.getStatus().equals("FINISHED")) {
				connection.updateCalculatedMatches(match);
			}
		}

		// Produce the calculation for each match and save it to the database
		for (Match match : matchesToTest) {
			ProduceOutput.getOutputForMatch(match, norm);
			connection.insertIntoTable("games");
			connection.createNewMatch(match, seasonToTest.getYear(), league.getName());
		}

		// Close the db-conection
		connection.disconnect();
	}
}