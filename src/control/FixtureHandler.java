package control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import boundary.FetchApi;
import entity.Match;
import entity.Season;
import entity.Team;

/**
 * Creates match-objects for an entire season and adds matches to dataset used by the ANN
 * 
 * @author Oscar Malmqvist
 *
 */
public class FixtureHandler {
	private static JSONObject jsonMatches;
	private static JSONArray fixtures;

	/**
	 * Fetch fixtures and create match-objects for an entire season
	 * 
	 * @param season the season to look for
	 * @throws JSONException if JSONObject och JSONArray cant be read or fetched
	 */
	public static void createFixtures(Season season, DataSet dataSet) throws JSONException {
		/**
		 * Get all fixtures from JSON
		 */
		System.out.println("Starts creating matches: " + season.getLeageName() + " / " + season.getYear());
		
		if (season.getYear() == 2018) { // TODO: Remove this part later. Get everything from external API when we are done, not from home.
			jsonMatches = FetchApi.getJsonMatches(season.getId());
		} else {
			jsonMatches = FetchApi.getJsonMatchesFromHome(season.getId());
		}
		fixtures = jsonMatches.getJSONArray("fixtures");
		System.out.println("Fixtures fetched from API");

		// Loop through all fixtures
		for (int i = 0; i < fixtures.length(); i++) {

			// Check if the match is finished
			String status = fixtures.getJSONObject(i).optString("status");

			// Get the teamnames from API and compare with the seasons team-objects to get the right team
			Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
			Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
			int matchDay = fixtures.getJSONObject(i).getInt("matchday");
			String dateString = fixtures.getJSONObject(i).getString("date");

			// Create the match-object
			Match match = new Match(homeTeam, awayTeam, matchDay);

			match.setDate(dateString.substring(0, 10));
			match.setTime(dateString.substring(11, 19));
			match.setIsFinished(status);
			
			match.produceInputArray(5); // Produce the input array for the match used in the dataset

			// What to do if the match is already played
			if (!status.equals("TIMED") && !status.equals("SCHEDULED") && !status.equals("POSTPONED")) {

				// Get data for the match
				int homeGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsHomeTeam");
				int awayGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsAwayTeam");

				// Set variables needed
				match.setHomeGoals(homeGoals);
				match.setAwayGoals(awayGoals);
				homeTeam.setGoalsFor(homeGoals);
				homeTeam.setGoalsAgainst(awayGoals);
				awayTeam.setGoalsFor(awayGoals);
				awayTeam.setGoalsAgainst(homeGoals);
				match.setOutcome();
				match.produceOutputArray();
				
				// Add match to the season and update the table for the season
				season.addMatch(match);
				season.updateTable();

				// Add the match to the dataset used for training the AI
				AIHand.addMatchToDataSet(match, dataSet);
			}

			// If the match isnt played, just add it to the season
			else {
				season.addMatch(match);
			}
		}
		
		System.out.println("Fixtures created for season: " + season.getLeageName() + " / " + season.getYear());
		
	}
}
