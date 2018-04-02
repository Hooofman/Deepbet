package control;

import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import entity.League;
import entity.Season;

public class LeagueCreator extends Thread {

	private League league;
	private DataSet trainingSet;
	
	public void start(String leagueName, int[] apiId) throws JSONException, InterruptedException {
		trainingSet = new DataSet(11, 1);
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

			FixtureHandler.createFixtures(season);
			System.out.println("Säsong skapad " + season.getYear());
			new AIHandler().trainNetwork(season, trainingSet);
			System.out.println("Säsong inlagd i dataset");
			
			
			Thread.sleep(10000);
			
			league.resetTeamsForNewSeason();
			System.out.println("Lag nollställda inför ny säsong");
			Thread.sleep(65000);
		}
		trainingSet.saveAsTxt("testmedtabellposition.txt", ",");
		System.out.println("Dataset sparat");
	}

	public static void main(String[] args) {

		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};

		try {
			new LeagueCreator().start("PL", plApiId);


		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
