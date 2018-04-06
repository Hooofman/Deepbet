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
import entity.Match;
import entity.Season;

public class LeagueCreator extends Thread {

	private League league;
	private DataSet trainingSet;

	public void start(String leagueName, int[] apiId) throws JSONException, InterruptedException {
		
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

			FixtureHandler.createFixtures(season, trainingSet);
			System.out.println("Säsong skapad " + season.getYear());

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
}
