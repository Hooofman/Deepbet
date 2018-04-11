package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import be.abeel.io.Base64.OutputStream;

/**
 * Class to fetch data from an API.
 * 
 * @author
 *
 */
public class FetchApi {
	private static String token = "WrBZ2f7IrkoF5ZZXd3ILImXnyGMEhdTYfblOtuaOrwM5He6BUPsUSCzTJDjx";
	static JSONObject object = null;
	JSONArray array = null;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public FetchApi() {
	}

	/**
	 * Fetches matches stored in the server.
	 * 
	 * @param year
	 *            The year the matches to be fetched was played.
	 * @param id
	 *            defines what file on server the data to be fetched is located.
	 * @return JSONObject A JSONObject containing all the matches from a specific
	 *         year stored in our server.
	 */
	public static JSONObject getJsonMatchesFromHome(int year, String id) {
		object = readJSONfromURL("http://deepbet.ddns.net/API/" + year + "_" + id + ".json");

		return object;
	}

	/**
	 * Gets all teams for a specific league
	 * 
	 * @param id
	 *            what league to look for. Id = 445 is Premier league. Look at
	 *            http://api.football-data.org/v1/competitions/
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
	 * Gets all information for a league. This includes number of rounds, league
	 * name, current year and so on.
	 * 
	 * @param id
	 *            what league to look for. Id = 445 is Premier league. Look at
	 *            http://api.football-data.org/v1/competitions/
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
	 * 
	 * @param id
	 *            what league to look for. Id = 445 is Premier league. Look at
	 *            http://api.football-data.org/v1/competitions/
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
	 * 
	 * @return all matches from Odds-api
	 */

	public static HttpResponse<JsonNode> getJsonOddsLeague() {
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get("https://bettingodds-bettingoddsapi-v1.p.mashape.com/events/league/22635")
					.header("X-Mashape-Key", "dWsfQ2SNqVmsheRkFQDwyFagyUj9p1eNhJmjsn1RhNyD1WgNJW")
					.header("Accept", "application/json").asJson();
		} catch (Exception e) {
		}
		return response;
	}

	/**
	 * Gets the odds for a specific event
	 * 
	 * @param id
	 *            what event to look at
	 * @return the odds for the event
	 */

	public static HttpResponse<JsonNode> getJsonOdds(long id) {
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get("https://bettingodds-bettingoddsapi-v1.p.mashape.com/event/" + id)
					.header("X-Mashape-Key", "CphqzYqDghmshKO8Whkk9rNOFNaAp1w4uKTjsn8aqUbPrTAmVz")
					.header("Accept", "application/json").asJson();
		} catch (Exception e) {
		}
		return response;
	}

	/**
	 * Fetches the table positions for a specific matchday.
	 * 
	 * @param id
	 *            the identifier for which league and season to be fetched.
	 * @param matchDay
	 *            the round for the table positions that is to be fetched.
	 * @return JSONObject containing the table positions for the specified matchday.
	 */
	public static JSONObject getJsonTablePosition(int id, int matchDay) {
		try {
			object = readJSONfromURL(
					"http://api.football-data.org/v1/competitions/" + id + "/leagueTable/?matchday=" + matchDay);
		} catch (Exception e) {
			System.out.println("Error when fetching table position from API");
		}
		return object;
	}

	/**
	 * Creates a connection to the the API and fetches the data.
	 * 
	 * @param url
	 *            the address to the API.
	 * @return JSONObject containing all the data in that API.
	 */
	public static JSONObject readJSONfromURL(String url) {
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get(url).header("X-Auth-Token", "d8294109f1a44ee6ab3f9c170932f193")
					.header("Accept", "application/json").asJson();
		} catch (Exception e) {
		}
		return response.getBody().getObject();
	}

	/**
	 * Method to put together the string that goes into the JsonObject
	 * 
	 * @param rd
	 *            the reader to use
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
}
