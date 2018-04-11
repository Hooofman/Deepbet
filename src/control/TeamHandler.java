package control;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.League;
import entity.Season;
import entity.Team;

/**
 * Creates team-objects
 *
 */
public class TeamHandler {
	private static JSONObject jsonTeams;
	private static JSONArray teams;

	/**
	 * Get the teams playing in a league from JSON and create team-objects.
	 * @param season what season to create teams for
	 * @param league what league the teams are playing in
	 */
	public static void populateTeams(Season season, League league) {
		Team team;
		try {
			// Get the teams playing in a season from JSON
			jsonTeams = FetchApi.getJsonTeams(season.getId()); 
			teams = jsonTeams.getJSONArray("teams"); 

			for (int i = 0; i < teams.length(); i++) {
				String teamName = teams.getJSONObject(i).getString("name");
				
				// If the teams doesnt already exist in the league, create it.
				if (!league.getAllTeams().contains(teamName)) {
					team = new Team(teamName);
					league.addTeam(team);	
				}
			}
		} catch (Exception e) {
		}
	}
}
