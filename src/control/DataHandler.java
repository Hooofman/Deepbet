package control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private HttpResponse<JsonNode> jsonOddsLeague;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public DataHandler() throws JSONException {
		Season season = new Season();
		jsonTeams = FetchApi.getJsonTeams();
		jsonMatches = FetchApi.getJsonMatches();
		jsonOddsLeague = FetchApi.getJsonOddsLeague();
		
	
			System.out.println( jsonOddsLeague.getBody().getObject().names().get(0));
			System.out.println(jsonOddsLeague.getBody().getObject().length());
			System.out.println(jsonOddsLeague.getBody().getArray().get(0));
			
			long id = 0;
			for (int i=0; i<jsonOddsLeague.getBody().getObject().length(); i++) {
				id = jsonOddsLeague.getBody().getObject().names().getLong(i);
				HttpResponse<JsonNode> jsonOdds = FetchApi.getJsonOdds(id);
				//double odds = jsonOdds.getBody().getObject().getJSONObject("odds").getJSONObject("1").getJSONObject("1").getJSONObject("1").getDouble("2");
//				System.out.println(jsonOdds.getBody().getObject().getJSONObject("" + id).getJSONObject("datetime").getString("value"));
//				System.out.println(sdf.format(new Date()));
				
				Date matchDate = new Date();
				Date currentDate = new Date();
				currentDate.setDate(currentDate.getDate() +1);
				try {
					matchDate = sdf.parse(jsonOdds.getBody().getObject().getJSONObject("" + id).getJSONObject("datetime").getString("value"));
					System.out.println(sdf.format(matchDate));
					if (matchDate.before(currentDate)) {
						
						System.out.println(jsonOdds.getBody().getObject().getJSONObject("" + id).getJSONObject("odds"));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
//				HttpResponse<JsonNode> jsonOdds = FetchApi.getJsonOdds(id);
//				double odds = jsonOdds.getBody().getObject().getJSONObject("odds").getJSONObject("1").getJSONObject("1").getJSONObject("1").getDouble("2");
//				System.out.println(odds); 
		
		
		
		
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
//			System.out.println(jsonOdds.getBody());
			for(int i = 0; i<20; i++) {
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
		try {
			new DataHandler();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
