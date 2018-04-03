package control;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

import entity.League;
import entity.Match;
import entity.Season;
import neurophtrainer.MLPTrainer;

public class TestAI3 {
	public static void main(String[] args) {

		//		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
		int[] plApiId = {398, 426, 445};
		LeagueCreator ligaSkapare = null;
		int matchesToGetDataFor = 29;
		int matchToTestOn = 30;
		int totalMatches = 0;

		ArrayList<Integer> correctPredictions = new ArrayList<Integer>(30);

		int test = 0;
		int antal = 1;

		for (int i = matchesToGetDataFor; i > 20 ; i--) {
			int predictionCount = 0;

			try {
				ligaSkapare = new LeagueCreator();
				ligaSkapare.start("PL", plApiId, i);
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
			League league = ligaSkapare.getLeague();

			DataSet trainingSet = DataSet.load("test");
			Normalizer norm = new MaxMinNormalizer();
			norm.normalize(trainingSet);
			MLPTrainer trainer = new MLPTrainer(i, trainingSet);

			// set hidden layer range
			trainer.setFirstHiddenLayerMin(3);
			trainer.setFirstHiddenLayerMax(2);

			trainer.setSecondHiddenLayerMin(1);
			trainer.setSecondHiddenLayerMax(1);
			// set learning rate range
			trainer.setMinLearningRate(0.1);
			trainer.setMaxLearningRate(0.1);
			// set momentm range
			trainer.setMinMomentum(0.7);
			trainer.setMaxMomentum(0.7);

			// run trainer
			trainer.run(); 
			//new AIHandler().trainNetwork(trainingSet);


			ArrayList<Season> seasons = league.getSeasons();
			ArrayList<Match> matchesToTest = new ArrayList<Match>();
			Season seasonToTest = seasons.get(seasons.size()-1);
			ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches();


			for (Match match : matchesFromSeason) {
				if (match.getRound() == i+1) {
					matchesToTest.add(match);
				}
			}

			for(int j = 0; j < antal; j++) {
				for (Match match : matchesToTest) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					predictionCount += TestAI.getOutputForMatch(match, i, j);
					totalMatches++;
				}
				correctPredictions.add(predictionCount);
				antal = trainer.getNumberOfCombinations();
				test++;
				System.out.println("Jag har testat " + totalMatches + " matcher. Här är mina rätt för varje omgång: ");
				for (int k = correctPredictions.size()-1; k>=0; k--) {
					System.out.println("Omgång " + k + " : " + correctPredictions.get(k) + " rätt.");
				}

				totalMatches = 0;
				correctPredictions.clear();
			}
		}
	}
}
