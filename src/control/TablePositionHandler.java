package control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Season;
import entity.Team;

public class TablePositionHandler {
	private static JSONObject jsonTablePosition;
	private static int tablePosition;

	public static void populateTablePosition(Season season) {
		int round = season.getCurrentRound();

		for (int i = round; i > 0; i--) {
			jsonTablePosition = FetchApi.getJsonTablePosition(season.getId(), round);
			try {
				JSONArray arrayTable = jsonTablePosition.getJSONArray("standing");
				for (int j = 0; j < season.getAllTeams().size(); j++) {
					String name = arrayTable.getJSONObject(j).getString("teamName");
					season.getTeam(name).setTablePosition(i, j);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("Runda: " + i);
		}
	}
}
