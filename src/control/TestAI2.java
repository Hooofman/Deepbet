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
//		int[] plApiId = {398, 426, 445};
		int[] plApiId = {398, 426, 445};
		LeagueCreator ligaSkapare = null;
		int matchesToGetDataFor = 27;
		int matchToTestOn = 30;
		double totalMatches = 0;
		double sumCorrectPredictions = 0;
		
		ArrayList<Integer> correctPredictions = new ArrayList<Integer>(38);
		
		for (int i = matchesToGetDataFor; i > 26 ; i--) {
			int predictionCount = 0;

			try {
				ligaSkapare = new LeagueCreator();
				ligaSkapare.start("PL", plApiId, i);
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
			League league = ligaSkapare.getLeague();
			
			DataSet trainingSet = DataSet.load("test");
			trainingSet.saveAsTxt("bias.txt", ",");
			new AIHandler().trainNetwork(trainingSet);
			
			ArrayList<Season> seasons = league.getSeasons();
			ArrayList<Match> matchesToTest = new ArrayList<Match>();
			Season seasonToTest = seasons.get(seasons.size()-1);
			ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches();

			
			for (Match match : matchesFromSeason) {
				if (match.getRound() == i+1) {
					matchesToTest.add(match);
				}
			}
			
			for (Match match : matchesToTest) {
				predictionCount += TestAI.getOutputForMatch(match, "test3Sig.nnet");
				totalMatches++;
			}
			correctPredictions.add(predictionCount);
			sumCorrectPredictions += predictionCount;
			
			if (i % 4 == 0) {
				try {
					Thread.sleep(25000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Jag har testat " + totalMatches + " matcher. Här är mina rätt för varje omgång: ");
		for (int i = correctPredictions.size()-1; i>=0; i--) {
			System.out.println("Omgång " + i + " : " + correctPredictions.get(i) + " rätt.");
		}
		System.out.println("Totalt hade jag " + sumCorrectPredictions + " rätt. Det motsvarar: " + ((sumCorrectPredictions / totalMatches)*100) + "%" );
	}

}
