package control;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.DecimalScaleNormalizer;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.RangeNormalizer;

import entity.*;

public class AIHandler {

	private DataSet trainingSet;
	/**
	 * * This sample shows how to create, train, save and load simple Multi Layer
	 * Perceptron
	 */

	public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {
		for (DataSetRow dataRow : testSet.getRows()) {
			nnet.setInput(dataRow.getInput());
			nnet.calculate();
			double[] networkOutput = nnet.getOutput();
			System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
			System.out.println(" Output: " + Arrays.toString(networkOutput));
		}
	}

	public void trainNetworkForLeague(League league) {
		// Create dataset
		trainingSet = new DataSet(11, 1);
		ArrayList<Season> seasons = league.getSeasons();
		for (int i=0; i<seasons.size(); i++) {
			trainNetwork(seasons.get(i));
		}
		trainingSet.saveAsTxt("testmedtabellposition.txt", ",");
		System.out.println("Dataset sparat");
	}

	/**
	 * Trains the network
	 * @param season
	 */
	public void createDataSetForSeason(Season season, DataSet dataset) {
		int currentRound = 28;

		// loop through the teams of the season
		for (int i=0; i<season.getAllTeams().size(); i++) {
			Team team = season.getTeamByNumber(i);
			System.out.println(team.getName() + " : ");
			int matchdays = team.getMatchesPlayed();
			
			// TODO: Remove Later! For testing!!!
			if (season.getYear() == 2017) {
				matchdays -= 2;
			}
			
			// loop through all the rounds
			for (int j=0; j<matchdays; j++) {
				if (team.getOutcomeForASpecificRound(j+1) != null) {
					// Create the array to put in each row of the dataset
					double[] arr = team.createInputArray(j, 5);

					// Just for testing-purpose, print the array in console
					System.out.println();
					for (int k=0; k<arr.length; k++) {
						System.out.print(arr[k] + " , ");
					}
					System.out.println();

					// Get the outcome for the round, the desired output
					double outcome = team.getOutcomeForASpecificRound(j+1);

					dataset.addRow(new DataSetRow(arr, new double[] {outcome}));
				}
			}
		}
	}

	public void trainNetwork(DataSet data) {
		MaxMinNormalizer norm = new MaxMinNormalizer();

		norm.normalize(data);
		
		MultiLayerPerceptron MLP = new  MultiLayerPerceptron(TransferFunctionType.TANH, 11, 8, 1);
		System.out.println("Nätverk skapat");

		MLP.randomizeWeights();
		SupervisedLearning learningRule = (SupervisedLearning)MLP.getLearningRule(); 
		learningRule.setMaxIterations(2000); // make sure we can end. 
		MLP.setLearningRule((BackPropagation) learningRule);
		MLP.learn(data);

		System.out.println("Inlärning klar");
		testNeuralNetwork(MLP, data);
		System.out.println("Testning klar");
		MLP.save("test.nnet");
		System.out.println("Nätverk sparat");
	}
}