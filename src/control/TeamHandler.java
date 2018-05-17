package control;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import boundary.FetchApi;
import entity.League;
import entity.Season;
import entity.Team;

/**
 * Creates team-objects
 * 
 * @author Sven Lindqvist, Johannes Roos, Oscar Malmqvist
 *
 */
public class TeamHandler {
	private static JSONObject jsonTeams;
	private static JSONArray teams;

	/**
	 * Get the teams playing in a league from JSON and create team-objects.
	 * 
	 * @param season what season to create teams for
	 * @param league what league the teams are playing in
	 */
	public static void populateTeams(Season season, League league) {
		Team team;
		ArrayList<Team> teamsArray = league.getAllTeamObjects();
		double goalsFor = 0;
		double goalsAgainst = 0;

		for (int i = 0; i < teamsArray.size(); i++) {
			goalsFor += teamsArray.get(i).getAverageForNGames(teamsArray.get(i).getGoalsFor(), 5);
			goalsAgainst += teamsArray.get(i).getAverageForNGames(teamsArray.get(i).getGoalsAgainst(), 5);
		}
		goalsFor /= 17;
		goalsAgainst /= 17;

		try {
			// Get the teams playing in a season from JSON
			jsonTeams = FetchApi.getJsonTeamsFromHome(season.getId());
			teams = jsonTeams.getJSONArray("teams");

			for (int i = 0; i < teams.length(); i++) {
				String teamName = teams.getJSONObject(i).getString("name");

				// If the teams doesnt already exist in the league, create it.
				if (!league.getAllTeams().contains(teamName)) {
					team = new Team(teamName);
					team.setGoalsFor(goalsFor);
					team.setGoalsAgainst(goalsAgainst);
					league.addTeam(team);
				}
			}

		} catch (Exception e) {
		}
	}
}
