package oldUnused;

import java.util.ArrayList;

import org.json.JSONException;
import org.neuroph.core.data.DataSet;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

import entity.League;
import entity.Match;
import entity.Season;
import control.*;
import boundary.*;

public class TestAI3 {
	public static void main(String[] args) {

		//		int[] plApiId = {113, 114, 4, 301, 341, 354, 398, 426, 445};
		int[] plApiId = {354,398, 426, 445};
		LeagueCreatorTest ligaSkapare = null;
		int matchesToGetDataFor = 28;
		int matchToTestOn = 29;
		int totalMatches = 0;

		ArrayList<ArrayList<Integer>> correctPredictions = new ArrayList<ArrayList<Integer>>();
		ArrayList<MLPTrainer> trainers = new ArrayList<MLPTrainer>(100);
		
		ArrayList<Integer> inner = null;

		

		int itteration = 0;
		int numberOfCombinations = 0;

		for (int i = matchesToGetDataFor; i > 20 ; i--) {
			inner = new ArrayList<Integer>();  
			
			int predictionCount = 0;

			try {
				ligaSkapare = new LeagueCreatorTest();
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
			trainers.get(itteration).setFirstHiddenLayerMin(12);
			trainers.get(itteration).setFirstHiddenLayerMax(40);

			trainers.get(itteration).setSecondHiddenLayerMin(0);
			trainers.get(itteration).setSecondHiddenLayerMax(1);
			// set learning rate range
			trainers.get(itteration).setMinLearningRate(0.2);
			trainers.get(itteration).setMaxLearningRate(0.4);
			// set momentm range
			trainers.get(itteration).setMinMomentum(0.5);
			trainers.get(itteration).setMaxMomentum(0.8);

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
				inner.add(predictionCount);


				

				totalMatches = 0;
				inner.clear();
				correctPredictions.add(inner);
			}
			itteration++;
			
		}
		
		
		for(int i = 0; i < correctPredictions.size(); i++) {
			for (int k = correctPredictions.get(i).size()-1; k>=0; k--) {
				System.out.println("Omgång " + k + " : " + correctPredictions.get(i).get(k) + " rätt.");
			}
		}

	}
}
