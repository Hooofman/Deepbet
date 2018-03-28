package control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import entity.Match;
import entity.Odds;
import entity.Team;

public class OddsHandler {
	private static HttpResponse<JsonNode> jsonOdds;
	private static HttpResponse<JsonNode> jsonOddsLeague;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void populateOdds(Match match) {
		jsonOddsLeague = FetchApi.getJsonOddsLeague();

		long id = 0;
		Odds odds;
		try {
			for (int i = 0; i < jsonOddsLeague.getBody().getObject().length(); i++) {
				id = jsonOddsLeague.getBody().getObject().names().getLong(i);
				HttpResponse<JsonNode> jsonOdds = FetchApi.getJsonOdds(id);

				Date matchDate = new Date();
				Date currentDate = new Date();
				currentDate.setDate(currentDate.getDate() + 1);
				try {
					matchDate = sdf.parse(jsonOdds.getBody().getObject().getJSONObject("" + id)
							.getJSONObject("datetime").getString("value"));
					System.out.println(sdf.format(matchDate));
					// if (matchDate.before(currentDate)) {
					// odds = new Odds();

					if (jsonOdds.getBody().getObject().getJSONObject("" + id).getJSONObject("odds")
							.getJSONObject("1") instanceof JSONObject) {
						JSONObject oddsArrayHome = jsonOdds.getBody().getObject().getJSONObject("" + id)
								.getJSONObject("odds").getJSONObject("1");
						System.out.println(oddsArrayHome);

					}

					// odds.putOddsHome(jsonOdds.getBody().getObject().getJSONObject("" +
					// id).getJSONObject("odds").getJSONObject("1").getJSONArray("1")))), odds);
					System.out.println(jsonOdds.getBody().getObject().getJSONObject("" + id).getJSONObject("odds"));
					// }
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Match ma = new Match(new Team("OScar"), new Team("Sven"));
		populateOdds(ma);
	}
}
