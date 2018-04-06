package control;

import java.io.FileNotFoundException;

import org.json.*;
import org.neuroph.core.data.DataSet;

import entity.*;

public class FixtureHandler {
	private static JSONObject jsonMatches;
	private static JSONArray fixtures;
	//	private static JSONObject sportsMonksMatches;


	/**
	 * Fetch fixtures and create match-objects for an entire season
	 * @param season the season to look for
	 * @throws JSONException
	 */

	public static void createFixtures(Season season, DataSet dataSet) throws JSONException {
		/**
		 * Get all fixtures from JSON
		 */
		if(season.getYear()== 2017) {
			jsonMatches = FetchApi.getJsonMatches(season.getId());
		}else {
			jsonMatches = FetchApi.getJsonMatchesFromHome(season.getYear(), "matches");
		}
		fixtures = jsonMatches.getJSONArray("fixtures");

		for (int i = 0; i < fixtures.length(); i++) {

			// Check if the match is finished
			String status = fixtures.getJSONObject(i).optString("status");

			// Get the teamnames from API and compare with the seasons team-objects to get the right team
			Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
			Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
			int matchDay = fixtures.getJSONObject(i).getInt("matchday");

			// Create the match-object
			Match match = new Match(homeTeam, awayTeam, matchDay);

			match.setStatus(status);
			match.setIsFinished(status);

			// What to do if the match is already played
			if (!status.equals("TIMED") || !status.equals("SCHEDULED") || !status.equals("POSTPONED")) {

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
				
				// Add match to the season and update the table for the season
				season.addMatch(match);
				season.updateTable();
				
				// Add the match to the dataset used for training the AI
				AIHandler.addMatchToDataSet(match, dataSet);

				// For testing. Check if the match is played during the round we want to test
//				if (!(season.getYear() == 2017 && matchDay >= matchesToGetDataFor)) {
//					AIHandler.addMatchToDataSet(match, dataSet);
//				}
			}
			
			// If the match isnt played, just add it to the season
			else {
				season.addMatch(match);
			}
		}
	}
}
