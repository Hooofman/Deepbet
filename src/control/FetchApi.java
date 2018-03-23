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

	public static JSONObject getJsonTeams() {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/466/teams");		
		} catch (Exception e) {
		}
		//System.out.println(object);
		return object;
	}


	public static JSONObject getJsonMatches() {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/466/fixtures");		
		} catch (Exception e) {
		}
		//System.out.println(object);
		return object;
	}

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
		//System.out.println(response.toString());
		return response;
	}
	
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
		//System.out.println(response.toString());
		return response;
	}


	public static JSONObject readJSONfromURL(String url) throws MalformedURLException, IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
