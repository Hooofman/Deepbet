package control;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import boundary.PrintListener;
import entity.Match;
import gui.PaintNetwork;

public class AIHand{
	private DataSet trainingSet;
	private PrintListener listener;
	private int currentIteration;
	
	public AIHand(PrintListener listener) {
		this.listener = listener;
		currentIteration = 0;
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
	 * Loads an existing neural network and sets the rules for it. Then it's trained on the dataset.
	 * @param data the dataset to use
	 * @param norm normalization-class to use for data-normalization
	 * @param iterations how many iterations that will be done during training
	 * @param learningRate the learning rate of the network
	 * @param momentum momentum used during training
	 * @param searchPath search path for the nn-network
	 * @param finalNNName what to save the trained network as
	 */
	public void trainNetwork(DataSet data, Norm norm, int iterations, double learningRate, double momentum, String searchPath, String finalNNName) {
		norm.normalize(data); // Normalize the data in the dataset

		MultiLayerPerceptron MLP = (MultiLayerPerceptron) MultiLayerPerceptron.createFromFile(searchPath); // Load the network template
		System.out.println("Network loaded");

		// Set the rules for training of the network
		MomentumBackpropagation learningRule = new MomentumBackpropagation();
		learningRule.setMaxIterations(iterations);
		learningRule.setLearningRate(learningRate);
		learningRule.setMomentum(momentum);
		MLP.setLearningRule(learningRule);

		// Start the training of the network
		new Thread() {
			public void run() {
				MLP.learn(data);
			}
		}.start();
		System.out.println("Learning started");
		
		// Create instance that will visualize network and start the thread
		PaintNetwork pn = new PaintNetwork(MLP);
		new Thread(pn).start();
		pn.initiate(MLP);
		
		// Update of progressbar in GUI
		while(currentIteration < iterations) {
			currentIteration = learningRule.getCurrentIteration();
			listener.updateProgress(currentIteration, iterations);
			pn.update(MLP);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Learning complete. Starts testing of netork..");
		
		testNeuralNetwork(MLP, data); // Test the network with input from dataset
		System.out.println("Testing complete");
		
		MLP.save(finalNNName); // Save the trained network
		System.out.println("Network saved");
	}
}
