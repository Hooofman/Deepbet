package control;

import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import boundary.FetchApi;
import entity.League;
import entity.Season;

/**
 * Creates a league-object
 * 
 * @author Oscar Malmqvist
 *
 */

public class LeagueCreator {
	private League league;
	private DataSet trainingSet;
	private int[] apiId;
	private String datasetName;
	private String leagueName;

	/**
	 * Constructor
	 * @param leagueName the name of the league
	 * @param apiId array containing ids to use for the API
	 * @param datasetName the name of the dataset to create
	 */
	public LeagueCreator(String leagueName, int[] apiId, String datasetName) {
		this.apiId = apiId;
		this.trainingSet = new DataSet(22, 3); // Creates a dataset used for training the network
		this.datasetName = datasetName;
		this.leagueName = leagueName;
		this.league = new League(leagueName);
	}

	/**
	 * Starts the thread that creates a league and fills it with data
	 * 
	 * @param leagueName the name of the league
	 * @param apiId id of the league used for connection with the API
	 */
	public void createLeague() {
		// Create new season
		System.out.println("Starts creating league: " + leagueName);
		
		for (int i = 0; i < apiId.length; i++) {
			// Removes the last three teams for each season and resets the variables for the teams that will play in the next season of the league
			if (i != 0) {
				league.removeLast3Teams();
				System.out.println("Last three teams  of the season is removed");
				league.resetTeamsForNewSeason();
				System.out.println("Teams reset for comming season");
			}

			JSONObject jsonSeason = FetchApi.getJsonSeason(apiId[i]);
			System.out.println("Season-object fetched from API");
			
			try {
				// Set variables needed
				int year = jsonSeason.getInt("year");
				int numberOfRounds = jsonSeason.getInt("numberOfMatchdays");
				int numberOfTeams = jsonSeason.getInt("numberOfTeams");
				int numberOfGames = jsonSeason.getInt("numberOfGames");
				int LeagueId = jsonSeason.getInt("id");
				int currentRound = jsonSeason.getInt("currentMatchday");

				// Create the season-object
				Season season = new Season(league, year, LeagueId, numberOfRounds, currentRound);
				
				league.addSeason(season);
				System.out.println("Season added to league..");
				
				// Create the teams
				TeamHandler.populateTeams(season, league);

				// Create the fixtures
				FixtureHandler.createFixtures(season, trainingSet);

				// Update the league-table
				season.updateTable();
				
				System.out.println("Season " + season.getYear() + " completed");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		trainingSet.save(datasetName); // Save the trainingset
		System.out.println("Dataset saved");
	}

	public League getLeague() {
		return league;
	}
}
