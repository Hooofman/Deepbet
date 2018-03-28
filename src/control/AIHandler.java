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

	public static void trainNetwork(Season inputSeason) {
		Season season = inputSeason;
		int currentRound = season.getCurrentRound();
		
		DataSet trainingSet = new DataSet(11, 1);

		for (int i=0; i<season.getAllTeams().size(); i++) {
			Team team = season.getTeamByNumber(i);
			for (int j=0; j<currentRound; j++) {
				double outcome = team.getOutcomeForASpecificRound(currentRound);
				trainingSet.addRow(new DataSetRow(team.createInputArray(currentRound), new double[] {outcome}));
			}
		}	
		MultiLayerPerceptron MLP = new  MultiLayerPerceptron(TransferFunctionType.TANH, 11, 10, 1);
		MLP.learn(trainingSet);
		testNeuralNetwork(MLP, trainingSet);
		MLP.save("test.nnet");
	}
}