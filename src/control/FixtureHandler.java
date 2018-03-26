package control;

import org.json.*;
import entity.*;

public class FixtureHandler {
	private static JSONObject jsonMatches;
	private static JSONArray fixtures;

	public static void createFixtures(Season season) throws JSONException {
	
			Match match;
			int homeGoals;
			int awayGoals;
			int matchDay;
			jsonMatches = FetchApi.getJsonMatches(season.getId());
			fixtures = jsonMatches.getJSONArray("fixtures");

			for(int i = 0; i<fixtures.length(); i++) {
				String status = fixtures.getJSONObject(i).getString("status");
				if (status.equals("FINISHED")) {

					Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
					Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
					homeGoals = fixtures.getJSONObject(i).getJSONObject("result").getInt("goalsHomeTeam");
					awayGoals = fixtures.getJSONObject(i).getJSONObject("result").getInt("goalsAwayTeam");
					matchDay = fixtures.getJSONObject(i).getInt("matchday");

					match = new Match(homeTeam, awayTeam);

					match.setIsFinished(status);
					match.setHomeGoals(homeGoals);
					match.setAwayGoals(awayGoals);
					match.setRound(matchDay);
					homeTeam.setGoalsFor(matchDay, homeGoals);
					homeTeam.setGoalsAgainst(matchDay, awayGoals);
					awayTeam.setGoalsFor(matchDay, awayGoals);
					awayTeam.setGoalsAgainst(matchDay, homeGoals);
					match.setOutcome();

					season.addMatch(match);
				} else if (status.equals("TIMED")) {
					Team homeTeam = season.getTeam(fixtures.getJSONObject(i).getString("homeTeamName"));
					Team awayTeam = season.getTeam(fixtures.getJSONObject(i).getString("awayTeamName"));
					
					matchDay = fixtures.getJSONObject(i).getInt("matchday");
					
					match = new Match(homeTeam, awayTeam);
					
					match.setRound(matchDay);
					match.setIsFinished(status);
					match.createOdds();
					
					season.addMatch(match);
				}
			}
		
	}
}
