package control;

import java.util.ArrayList;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

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

	public static void addMatchToDataSet(Match match, DataSet dataset) {
		double[] inArr = match.getMatchArray(5);
		double[] outArr = match.get1X2Outcome();
		System.out.println(inArr.length);
		System.out.println(outArr.length);
		dataset.addRow(new DataSetRow(inArr, outArr));
	}

	public void trainNetwork(DataSet data) {
		Normalizer norm = new MaxMinNormalizer();
		norm.normalize(data);
		MultiLayerPerceptron MLP = new  MultiLayerPerceptron(TransferFunctionType.SIGMOID, 22, 15, 3);
		System.out.println("Nätverk skapat");


		SupervisedLearning learningRule = (SupervisedLearning)MLP.getLearningRule(); 
		learningRule.setMaxIterations(5000); // make sure we can end. 
		MLP.setLearningRule((BackPropagation) learningRule);
		MLP.learn(data);


		System.out.println("Inlärning klar");
		testNeuralNetwork(MLP, data);
		System.out.println("Testning klar");
		MLP.save("test.nnet");
		System.out.println("Nätverk sparat");
	}
}