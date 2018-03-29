package control;

import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.TransferFunctionType;
import entity.*;

public class AIHandler {

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

	public static void trainNetwork(Season season) {
		
		int currentRound = 30;
		
		DataSet trainingSet = new DataSet(11, 1);

		for (int i=0; i<season.getAllTeams().size(); i++) {
			Team team = season.getTeamByNumber(i);
			System.out.println(team.getName() + " : ");
			for (int j=1; j<30; j++) {
				currentRound = j;
				double[] arr = team.createInputArray(currentRound);
				System.out.println();
				for (int k=0; k<arr.length; k++) {
					System.out.print(arr[k] + " , ");
				}
				System.out.println();
				double outcome = team.getOutcomeForASpecificRound(currentRound);
				trainingSet.addRow(new DataSetRow(arr, new double[] {outcome}));
			}
		}
		trainingSet.saveAsTxt("testmedtabellposition.txt", ",");
//		MultiLayerPerceptron MLP = new  MultiLayerPerceptron(TransferFunctionType.TANH, 11, 10, 1);
//		System.out.println("Nätverk skapat");
//		MLP.learn(trainingSet);
//		System.out.println("Inlärning klar");
//		testNeuralNetwork(MLP, trainingSet);
//		System.out.println("Testning klar");
//		MLP.save("test.nnet");
//		System.out.println("Nätverk sparat");
	}
}