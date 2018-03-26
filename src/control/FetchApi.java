package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class FetchApi {
	static JSONObject object = null;
	JSONArray array = null;


	public FetchApi() {
	}
	
	
	public static JSONObject getJsonOdds(String sport) {
		try {
			object = readJSONfromURL("https://api.the-odds-api.com/v2/odds/?sport=" + sport + "&region=uk&apiKey=56117f942fa819cf3bb034243fbc201c");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.print(object);
		return object;
	}
	
	
	/**
	 * Gets all teams for a specific league
	 * @param id what league to look for. Id = 445 is Premier league. Look at http://api.football-data.org/v1/competitions/
	 * @return JsonObject with all teams
	 */

	public static JSONObject getJsonTeams(int id) {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/" + id + "/teams");		
		} catch (Exception e) {
		}
		System.out.println(object);
		return object;
	}
	
	/**
	 * Gets all information for a league. This includes number of rounds, league name, current year and so on.
	 * @param id what league to look for. Id = 445 is Premier league. Look at http://api.football-data.org/v1/competitions/
	 * @return JsonObject with season-information
	 */
	
	public static JSONObject getJsonSeason(int id) {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/" + id);		
		} catch (Exception e) {
		}
		System.out.println(object);
		return object;
	}

	/**
	 * Gets all matches for a specific league.
	 * @param id what league to look for. Id = 445 is Premier league. Look at http://api.football-data.org/v1/competitions/
	 * @return JsonObject with all matches in a specific league
	 */
	public static JSONObject getJsonMatches(int id) {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/" + id + "/fixtures");		
		} catch (Exception e) {
		}
		System.out.println(object);
		return object;
	}
	
	/**
	 * Gets all matches from Odds-api
	 * @return all matches from Odds-api
	 */

	public static HttpResponse<JsonNode> getJsonOddsLeague() {
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get("https://bettingodds-bettingoddsapi-v1.p.mashape.com/events/league/22635")
					.header("X-Mashape-Key", "dWsfQ2SNqVmsheRkFQDwyFagyUj9p1eNhJmjsn1RhNyD1WgNJW")
					.header("Accept", "application/json")
					.asJson();
		} catch (Exception e) {
		}
		return response;
	}
	
	/**
	 * Gets the odds for a specific event
	 * @param id what event to look at
	 * @return the odds for the event
	 */
	
	public static HttpResponse<JsonNode> getJsonOdds(long id) {
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get("https://bettingodds-bettingoddsapi-v1.p.mashape.com/event/" + id)
			.header("X-Mashape-Key", "CphqzYqDghmshKO8Whkk9rNOFNaAp1w4uKTjsn8aqUbPrTAmVz")
			.header("Accept", "application/json")
			.asJson();
		} catch (Exception e) {
		}
		return response;
	}

	/**
	 * Reads JSON from an URL
	 * @param url what URL to use
	 * @return JSONObject
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JSONException
	 */

	public static JSONObject readJSONfromURL(String url) throws MalformedURLException, IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
	}
	
	/**
	 * Method to put together the string that goes into the JsonObject
	 * @param rd the reader to use
	 * @return String with the data
	 * @throws IOException
	 */

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		getJsonOdds("EPL");
//		getJsonTeams(466);
//		getJsonSeason(466);
//		getJsonMatches(466);
	}
}
