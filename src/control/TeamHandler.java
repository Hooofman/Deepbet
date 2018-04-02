package control;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.League;
import entity.Season;
import entity.Team;

public class TeamHandler {
	private static JSONObject jsonTeams;
	private static JSONArray teams;

	public static void populateTeams(Season season, League league) {
		Team team;
		try {
			jsonTeams = FetchApi.getJsonTeams(season.getId());
			teams = jsonTeams.getJSONArray("teams");
			for (int i = 0; i < teams.length(); i++) {
				String teamName = teams.getJSONObject(i).getString("name");
				if (!league.getAllTeams().contains(teamName)) {
					team = new Team(teamName);
					team.setShortName(teams.getJSONObject(i).getString("shortName"));
					league.addTeam(team);	
				}
			}
		} catch (Exception e) {
		}
	}
}
