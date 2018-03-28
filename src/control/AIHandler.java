package control;

import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.TransferFunctionType;
import entity.*;

public class AIHandler {

	private static Season season;
	/**
	 * * This sample shows how to create, train, save and load simple Multi Layer
	 * Perceptron
	 */
	


	public static void main(String[] args) {
		// create training set (logical XOR function)
		DataSet trainingSet = new DataSet(11, 1);
		
		for (int i=0; i<season.teams; i++) {
			for (int j=0; j<season.currentRound; j++) {
				
				
				trainingSet.addRow(new DataSetRow(createInputArrayForTeam(team), createInputOutcome(team));
			}
		}
				
				
		
		trainingSet.addRow(new DataSetRow(new double[] { 0, 1 }, new double[] { 1 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 0 }, new double[] { 1 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 1 }, new double[] { 0 }));
		// create multi layer perceptron 
		MultiLayerPerceptron myMlPerceptron = new  MultiLayerPerceptron(TransferFunctionType.TANH, 2, 3, 1); 
		// learn the training set 
		myMlPerceptron.learn(trainingSet);
		// test perceptron 
		System.out.println("Testing trained neural network");
		testNeuralNetwork(myMlPerceptron, trainingSet);
		// save trained neural network 
		myMlPerceptron.save("myMlPerceptron.nnet");
		// load saved neural network 
		NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");
		// test loaded neural network 
		System.out.println("Testing loaded neural network"); 
		testNeuralNetwork(loadedMlPerceptron, trainingSet);
	}

	public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {
		for (DataSetRow dataRow : testSet.getRows()) {
			nnet.setInput(dataRow.getInput());
			nnet.calculate();
			double[] networkOutput = nnet.getOutput();
			System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
			System.out.println(" Output: " + Arrays.toString(networkOutput));
		}
	}
}