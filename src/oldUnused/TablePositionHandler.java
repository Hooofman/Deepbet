package oldUnused;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import boundary.FetchApi;
import entity.Season;
import entity.Team;

/**
 * Get the tableposition from JSON and set the current table position for each team
 *
 */

public class TablePositionHandler {
	private static JSONObject jsonTablePosition;
	private static int tablePosition;

	/**
	 * Gets the table position for each matchday in a season and save it in each team
	 * @param season what season to get table positions for
	 */
	public static void populateTablePosition(Season season) {
		int round = season.getCurrentRound();

		for (int i = 1; i < round; i++) {
			jsonTablePosition = FetchApi.getJsonTablePosition(season.getId(), i);
			try {
				JSONArray arrayTable = jsonTablePosition.getJSONArray("standing");
				for (int j = 0; j < season.getAllTeams().size(); j++) {
					String name = arrayTable.getJSONObject(j).getString("teamName");
					season.getTeam(name).setTablePosition(j+1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
