package control;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Season;
import entity.Team;

public class TeamHandler {
	private static JSONObject jsonTeams;
	private static JSONArray teams;

	public static void populateTeams(Season season) {
		Team team;
		try {
			jsonTeams = FetchApi.getJsonTeams(season.getId());
			teams = jsonTeams.getJSONArray("teams");
			for (int i = 0; i < teams.length(); i++) {
				team = new Team(teams.getJSONObject(i).getString("name"));
				team.setShortName(teams.getJSONObject(i).getString("shortName"));
				season.addTeam(team);	
			}
		} catch (Exception e) {
		}
	}
}
