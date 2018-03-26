package control;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OddsHandler2 {
	private static JSONObject jsonOdds;
	private static JSONArray fixtures;

	public static void fetchOddsFromApi(String sport) {
		jsonOdds = FetchApi.getJsonOdds(sport);

	}

	public static HashMap<String, Double> getHomeOddsMap(String home, String away) throws JSONException {
		fetchOddsFromApi("EPL");

		HashMap<String, Double> homeOdds = new HashMap<String, Double>();

		Iterator itr = jsonOdds.getJSONObject("data").getJSONObject("events").keys();

		while(itr.hasNext()) {
			String fixtureName = (String) itr.next();
			JSONObject fixture = (JSONObject) jsonOdds.getJSONObject("data").getJSONObject("events").get(fixtureName);
			String homeTeam = fixture.getJSONArray("participants").getString(0);
			String awayTeam = fixture.getJSONArray("participants").getString(1);

			if(homeTeam.contains(home) && awayTeam.contains(away)) {
				JSONObject bookies = getBookies(fixture);
				Iterator itrBookies = bookies.keys();
				while(itrBookies.hasNext()) {
					String bookie = (String) itrBookies.next();
					double odds = bookies.getJSONObject(bookie).getJSONObject("odds").getJSONArray("h2h").getDouble(0);
					homeOdds.put(bookie, odds);
				}
				return homeOdds;
			}
		}
		return null;
	}

	public static void getOdds(String sport) throws JSONException {
		jsonOdds = FetchApi.getJsonOdds("EPL");
		Iterator itr = jsonOdds.getJSONObject("data").getJSONObject("events").keys();

		while (itr.hasNext()) {
			HashMap<String, Double> homeOdds;
			String fixtureName = (String) itr.next();
			JSONObject fixture = (JSONObject) jsonOdds.getJSONObject("data").getJSONObject("events").get(fixtureName);
			String homeTeam = fixture.getJSONArray("participants").getString(0);
			String awayTeam = fixture.getJSONArray("participants").getString(1);

			JSONObject bookies = getBookies(fixture);
			homeOdds = getHomeOdds(bookies);
		}
	}

	public static JSONObject getBookies(JSONObject fixture) throws JSONException {
		JSONObject bookies = fixture.getJSONObject("sites");
		return bookies;
	}

	public static HashMap<String, Double> getHomeOdds(JSONObject bookies) throws JSONException {
		HashMap<String, Double> homeOdds = new HashMap<String, Double>();
		Iterator itrBookies = bookies.keys();

		while(itrBookies.hasNext()) {
			String bookie = (String) itrBookies.next();
			double odds = bookies.getJSONObject(bookie).getJSONObject("odds").getJSONArray("h2h").getDouble(0);
			homeOdds.put(bookie, odds);
		}
		return homeOdds;
	}

	public static void main(String[] args) {
		try {
			getOdds("EPL");
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
}
