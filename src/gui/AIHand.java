package gui;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import control.Norm;
import entity.Match;

public class AIHand {
	private DataSet trainingSet;
	private PrintListener listener;

	public AIHand(PrintListener listener) {
		this.listener = listener;
	}

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
			//System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
			//System.out.println(" Output: " + Arrays.toString(networkOutput));
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
		double[] inArr = match.getMatchArray();
		double[] outArr = match.get1X2Outcome();
		dataset.addRow(new DataSetRow(inArr, outArr));
	}

	/**
	 * Creates or loads an existing neural network and sets the rules for it. Then
	 * it's trained on the dataset.
	 * 
	 * @param data
	 *            the dataset the network is to train on.
	 */
	public void trainNetwork(DataSet data, Norm norm, int iterations, double learningRate, double momentum,
			String searchPath, String finalNNName) {
		// Normalizer norm = new MaxMinNormalizer();
		norm.normalize(data);

		// MultiLayerPerceptron MLP = new
		// MultiLayerPerceptron(TransferFunctionType.SIGMOID, 22, 15, 7, 5, 3);
		// MultiLayerPerceptron MLP = (MultiLayerPerceptron)
		// MultiLayerPerceptron.createFromFile("biasNy.nnet");
		MultiLayerPerceptron MLP = (MultiLayerPerceptron) MultiLayerPerceptron.createFromFile(searchPath);
		listener.updateText("Network created");
		// System.out.println("Nätverk skapat");

		// MLP.randomizeWeights();
		MomentumBackpropagation learningRule = new MomentumBackpropagation();
		// SupervisedLearning learningRule = (SupervisedLearning)MLP.getLearningRule();
		learningRule.setMaxIterations(iterations); // make sure we can end.
		learningRule.setLearningRate(learningRate);
		learningRule.setMomentum(momentum);
		MLP.setLearningRule(learningRule);
		new Thread() {
			public void run() {
				MLP.learn(data);
			}
		}.start();
		int old = 0;
		int current = 0;

		System.out.println("Learning started");
		while(current < iterations) {
			current = learningRule.getCurrentIteration();
			listener.updateProgress(current, iterations);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		listener.updateText("Learning complete");
		// System.out.println("Inlärning klar");
		testNeuralNetwork(MLP, data);
		listener.updateText("Testing complete");
		// System.out.println("Testning klar");
		MLP.save(finalNNName);
		listener.updateText("Network saved");
		// System.out.println("Nätverk sparat");
	}
}
