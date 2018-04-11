package control;

import java.util.ArrayList;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

import entity.*;

/**
 * Class to train and load a neural network.
 *
 */
public class AIHandler {
	private DataSet trainingSet;

	/**
	 * Tests a neural network with a dataset and prints out the input and output.
	 * 
	 * @param nnet
	 *            The neural network
	 * @param testSet
	 *            the dataset to be tested.
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
	 * Adds a match to a dataset.
	 * 
	 * @param match
	 *            the match that is to be added to the dataset.
	 * @param dataset
	 *            to which the match is to added to.
	 */
	public static void addMatchToDataSet(Match match, DataSet dataset) {
		double[] inArr = match.getMatchArray(5);
		double[] outArr = match.get1X2Outcome();
		// System.out.println(inArr.length);
		// System.out.println(outArr.length);
		dataset.addRow(new DataSetRow(inArr, outArr));
	}

	/**
	 * Creates or loads an existing neural network and sets the rules for it. Then
	 * it's trained on the dataset.
	 * 
	 * @param data
	 *            the dataset the network is to train on.
	 */
	public void trainNetwork(DataSet data) {
		Normalizer norm = new MaxMinNormalizer();
		norm.normalize(data);
		// MultiLayerPerceptron MLP = new
		// MultiLayerPerceptron(TransferFunctionType.SIGMOID, 22, 15, 7, 5, 3);
		MultiLayerPerceptron MLP = (MultiLayerPerceptron) MultiLayerPerceptron.load("biasNy.nnet");

		System.out.println("Nätverk skapat");

		// MLP.randomizeWeights();
		MomentumBackpropagation learningRule = new MomentumBackpropagation();
		// SupervisedLearning learningRule = (SupervisedLearning)MLP.getLearningRule();
		learningRule.setMaxIterations(2000); // make sure we can end.
		learningRule.setLearningRate(0.2);
		learningRule.setMomentum(0.7);
		MLP.setLearningRule((BackPropagation) learningRule);
		MLP.learn(data);

		System.out.println("Inlärning klar");
		testNeuralNetwork(MLP, data);
		System.out.println("Testning klar");
		MLP.save("test.nnet");
		System.out.println("Nätverk sparat");
	}
}