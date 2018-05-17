package testing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import boundary.PrintListener;
import entity.Match;
import testing.PaintNetworkLabel;
import control.Norm;

/**
 * Class runs the training and testing of the neural network
 * 
 * @author Oscar, Johannes, Sven
 *
 */
public class AIHand {
	private DataSet trainingSet;
	private Controller controller;
	private int currentIteration;
	private int iterations;
	private PaintNetworkLabel pn;
	/**
	 * Constructor
	 * 
	 * @param listener listener-interface used for updates sent to GUI
	 */
	public AIHand(Controller controller) {
		this.controller = controller;
		currentIteration = 1;
	}

	/**
	 * Tests a neural network with a dataset and prints out the input and output.
	 * 
	 * @param nnet The neural network
	 * @param testSet the dataset to be tested.
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
	 * @param match the match that is to be added to the dataset.
	 * @param dataset to which the match is to added to.
	 */
	public static void addMatchToDataSet(Match match, DataSet dataset) {
		double[] inArr = match.getMatchArray();
		double[] outArr = match.get1X2Outcome();
		dataset.addRow(new DataSetRow(inArr, outArr));
	}

	/**
	 * Loads an existing neural network and sets the rules for it. Then it's trained on the dataset.
	 * 
	 * @param data the dataset to use
	 * @param norm normalization-class to use for data-normalization
	 * @param iterations how many iterations that will be done during training
	 * @param learningRate the learning rate of the network
	 * @param momentum momentum used during training
	 * @param searchPath search path for the nn-network
	 * @param finalNNName what to save the trained network as
	 */
	public void trainNetwork(DataSet data, Norm norm, int iterations, double learningRate, double momentum,
			String searchPath, String finalNNName) {
		norm.normalize(data); // Normalize the data in the dataset
		this.iterations = iterations;
		MultiLayerPerceptron MLP = (MultiLayerPerceptron) MultiLayerPerceptron.createFromFile(searchPath); // Load the
		// network
		// template
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
		//pn = new PaintNetworkLabel(MLP);
		//new Thread(pn).start();
		//controller.sendNetworkFrame(pn);
		//pn.initiate(MLP);
		while (currentIteration < iterations) {
			currentIteration = learningRule.getCurrentIteration();
			synchedPainting(1);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		int oldIteration = 0;
//		Thread t1 = new Thread() {
//			public void run() {
//				
//				this.interrupt();
//			}
//		};
//		t1.start();

		// Update of progressbar in GUI
//		while (currentIteration < iterations) {
//			currentIteration = learningRule.getCurrentIteration();
//			if(oldIteration < currentIteration) {
//				if(controller.shouldIDraw()) {
//					synchedPainting(2);
//				}
//				pn.updateInputConnections(MLP);
//				oldIteration = currentIteration;
//			}
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}

		System.out.println("Learning complete. Starts testing of netork..");

		testNeuralNetwork(MLP, data); // Test the network with input from dataset
		System.out.println("Testing complete");

		MLP.save(finalNNName); // Save the trained network
		System.out.println("Network saved");
	}
	
	/**
	 * Synchronized so only one can draw at the same time.
	 * Otherwise network and progressbar interferes with each other
	 * @param who
	 */
	public synchronized void synchedPainting(int who) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(who == 1) {
			controller.updateProgress(currentIteration, iterations);
		}else if(who == 2) {
			pn.paint(pn.getGraphics());
		}
			
		
	}
}
