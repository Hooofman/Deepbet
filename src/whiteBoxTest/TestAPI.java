package whiteBoxTest;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boundary.FetchApi;

class TestAPI {
	JSONObject jsonTeams;
	JSONObject jsonMatches;
	JSONArray teams;
	JSONArray fixtures;
	@BeforeEach
	void setUp() throws Exception {
		jsonTeams = FetchApi.getJsonTeams(445);
		teams = jsonTeams.getJSONArray("teams"); 
	}

	@Test
	void test() throws JSONException {
		for (int i = 0; i < teams.length(); i++) {
			try {
				String teamName = teams.getJSONObject(i).getString("name");
				System.out.println(teamName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		jsonMatches = FetchApi.getJsonMatches(445);
		fixtures = jsonMatches.getJSONArray("fixtures");
		for (int i = 0; i < fixtures.length(); i++) {
			int homeGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsHomeTeam");
			int awayGoals = fixtures.getJSONObject(i).getJSONObject("result").optInt("goalsAwayTeam");
			System.out.println(fixtures.getJSONObject(i).getString("homeTeamName") + " - "+ fixtures.getJSONObject(i).getString("awayTeamName") + "  "+ homeGoals + " - "+ awayGoals);
		}
	}

}
