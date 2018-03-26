package control;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Season;

public class SeasonCreator {
	private static JSONObject jsonSeason;
	
	/**
	 * Creates a new season
	 * @throws JSONException
	 */
	
	public static void createSeason() throws JSONException {
		Season season = new Season();
		// Get the season from API
		
		jsonSeason = FetchApi.getJsonSeason();
		
		// Get all the data for the season
		int year = jsonSeason.getInt("year");
		int numberOfRounds = jsonSeason.getInt("numberOfMatchdays");
		int numberOfTeams = jsonSeason.getInt("numberOfTeams");
		int numberOfGames = jsonSeason.getInt("numberOfGames");
		int id = jsonSeason.getInt("id");
		String leagueName = jsonSeason.getString("caption");
		String leagueAbbrevation = jsonSeason.getString("league");
		
		// Deliever the data to the season-object
		season.setYear(year);
		season.setNumberOfRounds(numberOfRounds);
		
		// Get the teams
		TeamHandler.populateTeams(season);
	}
	
	public static void main(String[] args) {
		try {
			createSeason();
		} catch (JSONException e) {
		}
	}
}
