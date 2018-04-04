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
		ArrayList<MLPTrainer> trainers = new ArrayList<MLPTrainer>(100);


		int itteration = 0;
		int numberOfCombinations = 0;

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
			trainers.add(new MLPTrainer(i, trainingSet));

			// set hidden layer range
			trainers.get(itteration).setFirstHiddenLayerMin(39);
			trainers.get(itteration).setFirstHiddenLayerMax(41);

			trainers.get(itteration).setSecondHiddenLayerMin(0);
			trainers.get(itteration).setSecondHiddenLayerMax(1);
			// set learning rate range
			trainers.get(itteration).setMinLearningRate(0.1);
			trainers.get(itteration).setMaxLearningRate(0.1);
			// set momentm range
			trainers.get(itteration).setMinMomentum(0.7);
			trainers.get(itteration).setMaxMomentum(0.7);

			// run trainer
			trainers.get(itteration).run(); 

			//new AIHandler().trainNetwork(trainingSet);
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<Season> seasons = league.getSeasons();
			ArrayList<Match> matchesToTest = new ArrayList<Match>();
			Season seasonToTest = seasons.get(seasons.size()-1);
			ArrayList<Match> matchesFromSeason = seasonToTest.getAllMatches();


			for (Match match : matchesFromSeason) {
				if (match.getRound() == i+1) {
					matchesToTest.add(match);
				}
			}
			numberOfCombinations = trainers.get(itteration).getNumberOfCombinations();
			System.out.println("nbrOfComb: "+numberOfCombinations);
			for(int j = 0; j < numberOfCombinations; j++) {
				for (Match match : matchesToTest) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					predictionCount += TestAI.getOutputForMatch(match, i, j);
					totalMatches++;
				}
				correctPredictions.add(predictionCount);


				System.out.println("Jag har testat " + totalMatches + " matcher. Här är mina rätt för varje omgång: ");
				for (int k = correctPredictions.size()-1; k>=0; k--) {
					System.out.println("Omgång " + k + " : " + correctPredictions.get(k) + " rätt.");
				}

				totalMatches = 0;
				correctPredictions.clear();
				
			}
			itteration++;
			
		}
	}
}
