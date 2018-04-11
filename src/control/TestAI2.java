package control;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;

import entity.League;
import entity.Match;
import entity.Season;

/**
 * A class used to test the neural network. It creates a league and season with
 * the teams in that specific season. The fixtures is fetched and a training
 * dataset is created and the network is trained with that dataset. It then
 * prints out its results.
 * 
 *
 */
public class TestAI2 {

	public static void main(String[] args) {

		int[] plApiId = { 113, 114, 4, 301, 341, 354, 398, 426, 445 }; // ID for specific seasons in premier league in
																		// the API.
		// int[] plApiId = {398, 426, 445};
		LeagueCreatorTest ligaSkapare = null;
		int matchesToGetDataFor = 30;
		double totalMatches = 0;
		double correctCount = 0;

		ArrayList<Integer> correctPredictions = new ArrayList<Integer>(matchesToGetDataFor);

		for (int i = matchesToGetDataFor; i > 28; i--) {
			int predictionCount = 0;

			try {
				ligaSkapare = new LeagueCreatorTest();
				ligaSkapare.start("PL", plApiId, i);
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}

			League league = ligaSkapare.getLeague();

			DataSet trainingSet = DataSet.load("test");
			new AIHandler().trainNetwork(trainingSet);

			ArrayList<Season> seasons = league.getSeasons();
			ArrayList<Match> matchesToTest = new ArrayList<Match>();
			Season seasonToTest = seasons.get(seasons.size() - 1);
			ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches();

			for (Match match : matchesFromSeason) {
				if (match.getRound() == i + 1) {
					matchesToTest.add(match);
				}
			}

			for (Match match : matchesToTest) {
				predictionCount += TestAI.getOutputForMatch(match);

				totalMatches++;
			}
			correctCount += predictionCount;
			correctPredictions.add(predictionCount);
		}

		System.out.println("Jag har testat " + totalMatches + " matcher. Rätt totalt: " + correctCount + " / "
				+ (correctCount / totalMatches) * 100 + "%. Här är mina rätt för varje omgång: ");
		WriteToFile.appendTxt("Jag har testat " + totalMatches + " matcher. Rätt totalt: " + correctCount + " / "
				+ (correctCount / totalMatches) * 100 + "%. Här är mina rätt för varje omgång: ");
		for (int i = correctPredictions.size() - 1; i >= 0; i--) {
			System.out.println("Omgång " + i + " : " + correctPredictions.get(i) + " rätt.");
			WriteToFile.appendTxt("Omgång " + i + " : " + correctPredictions.get(i) + " rätt.");
		}

	}

}
