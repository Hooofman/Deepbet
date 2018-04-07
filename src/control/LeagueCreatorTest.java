package control;

import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import entity.League;
import entity.Match;
import entity.Season;

public class LeagueCreatorTest extends Thread {

	private League league;
	private DataSet trainingSet;

	public void start(String leagueName, int[] apiId, int matchesToGetDataFor) throws JSONException, InterruptedException {
		trainingSet = new DataSet(22, 3);
		league = new League(leagueName);
		int[] seasonsId = apiId;
		for (int i= 0; i<seasonsId.length; i++) {

			JSONObject jsonSeason = FetchApi.getJsonSeason(seasonsId[i]);

			Season season = new Season(league);

			int year = jsonSeason.getInt("year");
			int numberOfRounds = jsonSeason.getInt("numberOfMatchdays");
			int numberOfTeams = jsonSeason.getInt("numberOfTeams");
			int numberOfGames = jsonSeason.getInt("numberOfGames");
			int LeagueId = jsonSeason.getInt("id");
			int currentRound = jsonSeason.getInt("currentMatchday");

			season.setId(LeagueId);
			season.setYear(year);
			season.setNumberOfRounds(numberOfRounds);
			season.setCurrentRound(currentRound);

			league.addSeason(season);

			TeamHandler.populateTeams(season, league);

			FixtureHandlerTest.createFixtures(season, trainingSet, matchesToGetDataFor);
			System.out.println("Säsong skapad " + season.getYear());
			//new AIHandler().createDataSetForSeason(season, trainingSet);
			System.out.println("Säsong inlagd i dataset");

			season.updateTable();
			
			if (i != seasonsId.length-1) {
				league.removeLast3Teams();
				System.out.println("Sista 3 lag borttagna");

				league.resetTeamsForNewSeason();
				System.out.println("Lag nollställda inför ny säsong");
			}
			
			Thread.sleep(1000);
		}
		trainingSet.save("test");
		System.out.println("Dataset sparat");
	}
	
	public League getLeague() {
		return league;
	}

	public static void main(String[] args) {

//		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
		int[] plApiId = {398, 426, 445};

		try {
			new LeagueCreator().start("PL", plApiId);


		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}