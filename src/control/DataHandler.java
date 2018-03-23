package control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import entity.*;

public class DataHandler {

	private JSONObject jsonTeams;
	private JSONObject jsonMatches;
	private HttpResponse<JsonNode> jsonOdds;
	
	public DataHandler() {
		Season season = new Season();
		jsonTeams = FetchApi.getJsonTeams();
		jsonMatches = FetchApi.getJsonMatches();
		jsonOdds = FetchApi.getJsonOdds();
		JSONArray teams = null;
		JSONArray matches = null;
		try {

			teams = jsonTeams.getJSONArray("teams");
			for(int i = 0; i<teams.length();i++) {
				season.addTeam(new Team(teams.getJSONObject(i).getString("name")));
			}
			
			matches = jsonMatches.getJSONArray("fixtures");
//			System.out.println(matches);
//			System.out.println(jsonMatches.getInt("count"));
			System.out.println(jsonOdds.getBody().getObject().getJSONObject("25820744967").getJSONObject("home").getString("name"));
			for(int i = 0; i< 200; i++) {
				String status = matches.getJSONObject(i).getString("status");
				if (status.equals("FINISHED")) {
					Team homeTeam = season.getTeam(matches.getJSONObject(i).getString("homeTeamName"));
					Team awayTeam = season.getTeam(matches.getJSONObject(i).getString("awayTeamName"));
					Match match = new Match(homeTeam, awayTeam);
					match.setIsFinished(status);
					int homeGoals = matches.getJSONObject(i).getJSONObject("result").getInt("goalsHomeTeam");
					int awayGoals = matches.getJSONObject(i).getJSONObject("result").getInt("goalsAwayTeam");
					match.setHomeGoals(homeGoals);
					match.setAwayGoals(awayGoals);
					int matchDay = matches.getJSONObject(i).getInt("matchday");
					match.setRound(matchDay);
					season.addMatch(match);
					homeTeam.setGoalsFor(matchDay, homeGoals);
					homeTeam.setGoalsAgainst(matchDay, awayGoals);
					
					awayTeam.setGoalsFor(matchDay, awayGoals);
					awayTeam.setGoalsAgainst(matchDay, homeGoals);
					match.setOutcome();
				}
			}
		
		
		
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		new DataHandler();
	}
}
