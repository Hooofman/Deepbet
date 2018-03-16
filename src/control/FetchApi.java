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

public class FetchApi {
	static JSONObject object = null;
	JSONArray array = null;
	
	public FetchApi() {
		
		
		
		
	}
	
	public static JSONObject getJsonTeams() {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/445/teams");		
		} catch (Exception e) {
		}
		System.out.println(object);
		return object;
	}

	
	public static JSONObject getJsonMatches() {
		try {
			object = readJSONfromURL("http://api.football-data.org/v1/competitions/445/fixtures");		
		} catch (Exception e) {
		}
		System.out.println(object);
		return object;
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
	 
	 public static void main(String[] args) {
		new FetchApi();
	}
}
