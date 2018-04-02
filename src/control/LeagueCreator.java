package control;

import org.json.JSONException;
import org.json.JSONObject;

import entity.League;
import entity.Season;

public class LeagueCreator extends Thread {
	
	public void start(String leagueName, int[] apiId) throws JSONException, InterruptedException {
		while(!Thread.interrupted()) {
			League league = new League(leagueName);
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
				Thread.sleep(65000);
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		
		int[] plApiId = {113, 114, 4, 301 ,341, 354, 398, 426, 445};
		
		try {
			new LeagueCreator().start("PL", plApiId);
		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
