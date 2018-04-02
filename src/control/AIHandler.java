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
	public void trainNetwork(Season season, DataSet dataset) {
		int currentRound = 28;
		
		// loop through the teams of the season
		for (int i=0; i<season.getAllTeams().size(); i++) {
			Team team = season.getTeamByNumber(i);
			System.out.println(team.getName() + " : ");
			
			// loop through all the rounds
			for (int j=0; j<team.getMatchesPlayed()-1; j++) {

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
//		MultiLayerPerceptron MLP = new  MultiLayerPerceptron(TransferFunctionType.SIGMOID, 11, 10, 1);
//		System.out.println("Nätverk skapat");
//		
//		
//		SupervisedLearning learningRule = (SupervisedLearning)MLP.getLearningRule(); 
//		learningRule.setMaxIterations(10000); // make sure we can end. 
//		MLP.setLearningRule((BackPropagation) learningRule);
//		MLP.learn(trainingSet);
//		
//		
//		System.out.println("Inlärning klar");
//		testNeuralNetwork(MLP, trainingSet);
//		System.out.println("Testning klar");
//		MLP.save("test.nnet");
//		System.out.println("Nätverk sparat");
	}
}