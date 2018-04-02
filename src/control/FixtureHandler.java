package control;

import org.json.*;
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
	
	public static void createFixtures(Season season) throws JSONException {
		
		/**
		 * Get all fixtures from JSON
		 */
		jsonMatches = FetchApi.getJsonMatches(season.getId());
		fixtures = jsonMatches.getJSONArray("fixtures");
		
//		sportsMonksMatches = FetchApi.getJSonFixturesFromSportMonks();


		for (int i = 0; i < fixtures.length(); i++) {
			
			// Check if the match is finished
			String status = fixtures.getJSONObject(i).optString("status");
			if (status.equals("FINISHED")) {

				// Get data for the match
				Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
				Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
				int homeGoals = fixtures.getJSONObject(i).getJSONObject("result").getInt("goalsHomeTeam");
				int awayGoals = fixtures.getJSONObject(i).getJSONObject("result").getInt("goalsAwayTeam");
				int matchDay = fixtures.getJSONObject(i).getInt("matchday");
				
				// Create the match-object
				Match match = new Match(homeTeam, awayTeam, matchDay);

				// Set variables needed
//				match.setIsFinished(status);
				match.setHomeGoals(homeGoals);
				match.setAwayGoals(awayGoals);
				homeTeam.setGoalsFor(homeGoals);
				homeTeam.setGoalsAgainst(awayGoals);
				awayTeam.setGoalsFor(awayGoals);
				awayTeam.setGoalsAgainst(homeGoals);
				match.setOutcome();

				// Add the match to the season it belongs to
				season.addMatch(match);
				
				// } else if (status.equals("TIMED")) {
				// Team homeTeam =
				// season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
				// Team awayTeam =
				// season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
				// matchDay = fixtures.getJSONObject(i).getInt("matchday");
				//
				// match = new Match(homeTeam, awayTeam);
				//
				// match.setRound(matchDay);
				// match.setIsFinished(status);
				// match.createOdds();
				//
				// season.addMatch(match);
			}
		}

	}
}
