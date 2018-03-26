package control;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Season;

public class SeasonCreator {
	private static JSONObject jsonSeason;
	
	/**
	 * Creates a new season
	 * @throws JSONException
	 * @param id to use for what league to create season for
	 */
	
	public static void createSeason(int id) throws JSONException {
		Season season = new Season();
		// Get the season from API
		
		jsonSeason = FetchApi.getJsonSeason(id);
		
		// Get all the data for the season
		int year = jsonSeason.getInt("year");
		int numberOfRounds = jsonSeason.getInt("numberOfMatchdays");
		int numberOfTeams = jsonSeason.getInt("numberOfTeams");
		int numberOfGames = jsonSeason.getInt("numberOfGames");
		int LeagueId = jsonSeason.getInt("id");
		String leagueName = jsonSeason.getString("caption");
		String leagueAbbrevation = jsonSeason.getString("league");
		
		// Deliever the data to the season-object
		season.setId(LeagueId);
		season.setYear(year);
		season.setNumberOfRounds(numberOfRounds);
		
		// Get the teams
		TeamHandler.populateTeams(season);
	}
	
	public static void main(String[] args) {
		try {
			createSeason(466);
		} catch (JSONException e) {
		}
	}
}
