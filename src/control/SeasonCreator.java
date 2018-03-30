package control;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Season;

public class SeasonCreator {
	private static JSONObject jsonSeason;

	/**
	 * Creates a new season
	 * 
	 * @throws JSONException
	 * @param id
	 *            to use for what league to create season for
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
		int currentRound = jsonSeason.getInt("currentMatchday");
		String leagueName = jsonSeason.getString("caption");
		String leagueAbbrevation = jsonSeason.getString("league");

		// Deliever the data to the season-object
		season.setId(LeagueId);
		season.setYear(year);
		season.setNumberOfRounds(numberOfRounds);
		season.setCurrentRound(currentRound);
		// Get the teams
		TeamHandler.populateTeams(season);

		// Get the fixtures
		FixtureHandler.createFixtures(season);
		TablePositionHandler.populateTablePosition(season);
		AIHandler.trainNetwork(season);
		
		TestAI.printGameOutcome(29, season.getTeam("Crystal Palace FC"), season.getTeam("Manchester United FC"));
		TestAI.printGameOutcome(29, season.getTeam("Manchester City FC"), season.getTeam("Chelsea FC"));
		TestAI.printGameOutcome(29, season.getTeam("Brighton & Hove Albion"), season.getTeam("Arsenal FC"));
		TestAI.printGameOutcome(29, season.getTeam("Liverpool FC"), season.getTeam("Newcastle United FC"));
		TestAI.printGameOutcome(29, season.getTeam("Leicester City FC"), season.getTeam("AFC Bournemouth"));
		TestAI.printGameOutcome(29, season.getTeam("Southampton FC"), season.getTeam("Stoke City FC"));
		TestAI.printGameOutcome(29, season.getTeam("Swansea City FC"), season.getTeam("West Ham United FC"));
		TestAI.printGameOutcome(29, season.getTeam("Tottenham Hotspur FC"), season.getTeam("Huddersfield Town"));
		TestAI.printGameOutcome(29, season.getTeam("Watford FC"), season.getTeam("West Bromwich Albion FC"));
		TestAI.printGameOutcome(29, season.getTeam("Burnley FC"), season.getTeam("Everton FC"));
		
//		TestAI.printGameOutcome(30, season.getTeam("Manchester United FC"), season.getTeam("Liverpool FC"));
		
	}

	public static void main(String[] args) {
		try {
			createSeason(445);
		} catch (JSONException e) {
		}
	}
}
