package control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.*;

public class DataHandler {

	private JSONObject jsonTeams;
	private JSONObject jsonMatches;
	
	public DataHandler() {
		Season season = new Season();
		jsonTeams = FetchApi.getJsonTeams();
		jsonMatches = FetchApi.getJsonMatches();

		JSONArray teams = null;
		JSONArray matches = null;
		try {

			teams = jsonTeams.getJSONArray("teams");
			for(int i = 0; i<teams.length();i++) {
				season.addTeam(new Team(teams.getJSONObject(i).getString("name")));
			}
			
			matches = jsonMatches.getJSONArray("fixtures");
			System.out.println(matches);
			System.out.println(jsonMatches.getInt("count"));
			for(int i = 0; i< 20; i++) {
				season.addMatch(new Match(season.getTeam(matches.getJSONObject(i).getString("homeTeamName")), season.getTeam(matches.getJSONObject(i).getString("awayTeamName"))));
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
