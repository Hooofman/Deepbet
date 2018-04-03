package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neuroph.core.data.DataSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import android.content.Context;
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
			new AIHandler().createDataSetForSeason(season, trainingSet);
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
		trainingSet.saveAsTxt("testmedtabellposition.txt", ",");
		System.out.println("Dataset sparat");

		new AIHandler().trainNetwork(trainingSet);
		TestAI.printGameOutcome(27, league.getTeam("Crystal Palace FC"), league.getTeam("Manchester United FC"));
		TestAI.printGameOutcome(27, league.getTeam("Manchester City FC"), league.getTeam("Chelsea FC"));
		TestAI.printGameOutcome(27, league.getTeam("Brighton & Hove Albion"), league.getTeam("Arsenal FC"));
		TestAI.printGameOutcome(27, league.getTeam("Liverpool FC"), league.getTeam("Newcastle United FC"));
		TestAI.printGameOutcome(27, league.getTeam("Leicester City FC"), league.getTeam("AFC Bournemouth"));
		TestAI.printGameOutcome(27, league.getTeam("Southampton FC"), league.getTeam("Stoke City FC"));
		TestAI.printGameOutcome(27, league.getTeam("Swansea City FC"), league.getTeam("West Ham United FC"));
		TestAI.printGameOutcome(27, league.getTeam("Tottenham Hotspur FC"), league.getTeam("Huddersfield Town"));
		TestAI.printGameOutcome(27, league.getTeam("Watford FC"), league.getTeam("West Bromwich Albion FC"));
		TestAI.printGameOutcome(27, league.getTeam("Burnley FC"), league.getTeam("Everton FC"));
	}

	public static void main(String[] args) {

		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
//		int[] plApiId = {398, 426, 445};

		try {
			new LeagueCreator().start("PL", plApiId);


		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
