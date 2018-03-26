package control;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Season;
import entity.Team;

public class TeamHandler {
	private static JSONObject jsonTeams;
	private static JSONArray teams;

	public static void populateTeams(Season season) {
		try {
			jsonTeams = FetchApi.getJsonTeams();
			teams = jsonTeams.getJSONArray("teams");
			for (int i = 0; i < teams.length(); i++) {
				season.addTeam(new Team(teams.getJSONObject(i).getString("name")));
			}

		} catch (Exception e) {
		}
	}
}
