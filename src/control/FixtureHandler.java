package control;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.json.*;
import org.neuroph.core.data.DataSet;

import boundary.FetchApi;
import entity.*;

public class FixtureHandler {
	private static JSONObject jsonMatches;
	private static JSONArray fixtures;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
			String dateString = fixtures.getJSONObject(i).getString("date");

			// Create the match-object
			Match match = new Match(homeTeam, awayTeam, matchDay);

			match.setDate(dateString.substring(0, 10));
			match.setTime(dateString.substring(11,19));
			match.setIsFinished(status);
			match.produceInputArray(5);
			
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
				AIHandler.addMatchToDataSet(match, dataSet);
				System.out.println(Arrays.toString(match.get1X2Outcome()));
			}

			// If the match isnt played, just add it to the season
			else {
				season.addMatch(match);
			}
		}
	}
}
