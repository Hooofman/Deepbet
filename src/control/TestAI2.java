package control;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;

import entity.League;
import entity.Match;
import entity.Season;

public class TestAI2 {





	public static void main(String[] args) {

		//		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
		int[] plApiId = {398, 426, 445};
		LeagueCreator ligaSkapare = null;
		int matchesToGetDataFor = 25;
		int matchToTestOn = 26;

		try {
			ligaSkapare = new LeagueCreator();
			ligaSkapare.start("PL", plApiId, matchesToGetDataFor);
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
		
//		for (int i=0; i<seasonToTest.getAllMatches().size(); i++) {
//			if (seasonToTest.getAllMatches().get(i).getRound() == matchToTestOn) {
//				matchesToTest.add(seasonToTest.getAllMatches().get(i));
//			}
//		}
		
		for (Match match : matchesFromSeason) {
			if (match.getRound() == matchToTestOn) {
				matchesToTest.add(match);
			}
		}
		
		for (Match match : matchesToTest) {
			TestAI.getOutputForMatch(match);
		}
	}

}
