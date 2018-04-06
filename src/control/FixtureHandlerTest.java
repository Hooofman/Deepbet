package control;

import java.io.FileNotFoundException;

import org.json.*;
import org.neuroph.core.data.DataSet;

import entity.*;

public class FixtureHandlerTest {
	private static JSONObject jsonMatches;
	private static JSONArray fixtures;
//	private static JSONObject sportsMonksMatches;

	
	/**
	 * Fetch fixtures and create match-objects for an entire season
	 * @param season the season to look for
	 * @throws JSONException
	 */
	
	public static void createFixtures(Season season, DataSet dataSet, int matchesToGetDataFor) throws JSONException {
		
		/**
		 * Get all fixtures from JSON
		 */
		if(season.getYear()== 2017) {
			jsonMatches = FetchApi.getJsonMatches(season.getId());
		}else {
			jsonMatches = FetchApi.getJsonMatchesFromHome(season.getYear(), "matches");
		}	

		fixtures = jsonMatches.getJSONArray("fixtures");

		int matchesToLoop = fixtures.length();
		if (season.getYear() == 2017) {
			matchesToLoop = matchesToGetDataFor;
		}

		for (int i = 0; i < matchesToLoop; i++) {
			
			// Check if the match is finished
			String status = fixtures.getJSONObject(i).optString("status");
			if (!status.equals("TIMED") || !status.equals("SCHEDULED") || !status.equals("POSTPONED")) {

				// Get data for the match
				Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
				Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
				int homeGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsHomeTeam");
				int awayGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsAwayTeam");
				int matchDay = fixtures.getJSONObject(i).getInt("matchday");
				
				// Create the match-object
				Match match = new Match(homeTeam, awayTeam, matchDay);

				// Set variables needed
				match.setIsFinished(status);
				match.setHomeGoals(homeGoals);
				match.setAwayGoals(awayGoals);
				homeTeam.setGoalsFor(homeGoals);
				homeTeam.setGoalsAgainst(awayGoals);
				awayTeam.setGoalsFor(awayGoals);
				awayTeam.setGoalsAgainst(homeGoals);
				match.setOutcome();

				// Add the match to the season it belongs to
				season.addMatch(match);
				
				season.updateTable();
				
				AIHandler.addMatchToDataSet(match, dataSet);
			}
		}

	}
}