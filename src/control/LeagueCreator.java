package control;

import org.json.JSONObject;

import entity.League;
import entity.Season;

public class LeagueCreator extends Thread {
	
	public void start(String leagueName) {
		while(!Thread.interrupted()) {
			League league = new League(leagueName);
			int[] seasonsId = PL.getApiId();
			for (int i= 0; i<seasonsId.length; i++) {
				
				JSONObject jsonSeason = FetchApi.getJsonSeason(seasonsId[i]);
				Season season = new Season();
				
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
				
				TeamHandler.populateTeams(season, league);
				
				FixtureHandler.createFixtures(season);
				Thread.sleep(65000);
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		new LeagueCreator().start("PL");
	}
}
