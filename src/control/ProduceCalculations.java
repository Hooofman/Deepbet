package control;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;

import entity.*;

public class ProduceCalculations {
	Producer producer;

	class Producer extends Thread {
		LeagueCreator creator;
		String leagueName;
		int[] apiId;
		League league;
		Season season;
		DataSet trainingSet;
		AIHandler aiHandler;
		ArrayList<Match> matchesToCalc;
		ArrayList<Season> seasonsFromLeague;
		
		public Producer() {
			creator = new LeagueCreator();
			aiHandler = new AIHandler();
			matchesToCalc = new ArrayList<Match>();
			seasonsFromLeague = new ArrayList<Season>();
		}
		
		public void run() {
			// Create the league
			try {
				creator.start(leagueName, apiId);
				league = creator.getLeague();
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
			
			// Load the dataset
			trainingSet = DataSet.load("dataSet");
			
			// Train the network
			aiHandler.trainNetwork(trainingSet);
			
			// Get current season
			season = league.getSeasons().get(league.getSeasons().size()-1);
			
			// Get the upcoming matches
			matchesToCalc = season.getMatchesByStatus("TIMED");
			
			// Calculate each match
			for (Match match : matchesToCalc) {
				ProduceOutput.getOutputForMatch(match);
			}
		}
	}
}