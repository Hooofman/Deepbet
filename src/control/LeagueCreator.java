package control;

import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import boundary.FetchApi;
import entity.League;
import entity.Season;

/**
 * Creates a league-object
 * @author Oscar
 *
 */

public class LeagueCreator extends Thread {

	private League league;
	private DataSet trainingSet;

	/**
	 * Starts the thread that creates a league and fills it with data
	 * @param leagueName the name of the league
	 * @param apiId id of the league used for connection with the API
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public void start(String leagueName, int[] apiId) throws JSONException, InterruptedException {
		
		trainingSet = new DataSet(22, 3); // Creates a dataset used for training the network
		league = new League(leagueName);
		int[] seasonsId = apiId;
		
		// Create new season
		for (int i= 0; i<seasonsId.length; i++) {
			
			// Removes the last three teams for each season and resets the variables for the teams that will play in the next season of the league
			if (i != 0) {
				league.removeLast3Teams();
				System.out.println("Sista 3 lag borttagna");
				league.resetTeamsForNewSeason();
				System.out.println("Lag nollställda inför ny säsong");
			}
			
			JSONObject jsonSeason = FetchApi.getJsonSeason(seasonsId[i]);
			Season season = new Season(league);
			int year = jsonSeason.getInt("year");
			int numberOfRounds = jsonSeason.getInt("numberOfMatchdays");
			int numberOfTeams = jsonSeason.getInt("numberOfTeams");
			int numberOfGames = jsonSeason.getInt("numberOfGames");
			int LeagueId = jsonSeason.getInt("id");
			int currentRound = jsonSeason.getInt("currentMatchday");
			season.setId(LeagueId);
			season.setYear(year);
			season.setNumberOfRounds(numberOfRounds);
			season.setCurrentRound(currentRound);
			league.addSeason(season);

			// Create the teams
			TeamHandler.populateTeams(season, league);

			// Create the fixtures
			FixtureHandler.createFixtures(season, trainingSet);
			
			System.out.println("Säsong skapad " + season.getYear());

			// Update the league-table
			season.updateTable();
			
			Thread.sleep(1000);
		}
		trainingSet.save("test"); // Save the trainingset
		System.out.println("Dataset sparat");
		
	}
	
	public League getLeague() {
		return league;
	}
}
