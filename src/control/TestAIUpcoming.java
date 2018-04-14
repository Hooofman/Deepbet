package control;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;

import entity.League;
import entity.Match;
import entity.Season;

public class TestAIUpcoming {
	
	
	public static void main(String[] args) {

		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
//		int[] plApiId = {398, 426, 445};
		LeagueCreator ligaSkapare = null;
	
		// Create db-connection
		ConnectDatabase connection = new ConnectDatabase();
		connection.connect();
		
		try {
			ligaSkapare = new LeagueCreator();
			ligaSkapare.start("PL", plApiId);
		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}

		League league = ligaSkapare.getLeague();
		DataSet trainingSet = DataSet.load("test");
		new AIHandler().trainNetwork(trainingSet);

		ArrayList<Season> seasons = league.getSeasons();
		ArrayList<Match> matchesToTest = new ArrayList<Match>();
		Season seasonToTest = seasons.get(seasons.size()-1);
		ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches();

		for (Match match : matchesFromSeason) {
			if (match.getStatus().equals("TIMED")) {
				matchesToTest.add(match);
			}
		}

		for (Match match : matchesToTest) {
			ProduceOutput.getOutputForMatch(match);
//			connection.insertIntoTable("upcominggames");
//			connection.createStatementForUpcomming(match, seasonToTest.getYear(), league.getName());
		}
		
		connection.disconnect();
	}
}
