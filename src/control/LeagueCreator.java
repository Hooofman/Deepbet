package control;

import org.json.JSONObject;

import entity.League;
import entity.Season;

public class LeagueCreator implements Runnable {

	
	public void run() {
		while(!Thread.interrupted()) {
			
			League PL = new League("PL");
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
				
				TeamHandler.populateTeams(season, PL);
			}
		}
	}
	
}
