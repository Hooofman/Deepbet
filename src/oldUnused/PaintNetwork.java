package oldUnused;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.neuroph.nnet.MultiLayerPerceptron;

/**
 * Class that paints the entire network Color on the neuron are based on the netInput (all inputs - all outputs) Size of
 * the lines are based on the weight of the connection between the neurons
 * 
 * @author johannes.roos
 *
 */
public class PaintNetwork extends JPanel implements Runnable {
	private ArrayList<ArrayList<NeuroPaint>> layer = new ArrayList<ArrayList<NeuroPaint>>();
	private ArrayList<NeuroPaint> inputLayer = new ArrayList<NeuroPaint>();
	private ArrayList<NeuroPaint> outputLayer = new ArrayList<NeuroPaint>();
	BufferStrategy bs;
	int height = 700;
	int width = 1500;

	/**
	 * Sets up the JFrame and creates the array containing all the layers Creates a BufferedStrategy for faster drawing
	 * in the JFrame
	 * 
	 * @param MLP The MultiLayered Network
	 * @throws HeadlessException exception
	 */
	public PaintNetwork(MultiLayerPerceptron MLP) throws HeadlessException {
//		setTitle("Network");
		setSize(width, height);
//		createBufferStrategy(2);
//		bs = this.getBufferStrategy();
		
		setVisible(true);
		for (int i = 0; i < MLP.getLayersCount(); i++) {
			layer.add(new ArrayList<NeuroPaint>());
		}
		
	}
	
	public void setSizeForLabel(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Fills the Lists with new neurons, input- and outputlayers x Position are calulated using the Frame-width and
	 * number of neurons at the layer y Position for each layer are fixed (Will create problem with more than 4 layers)
	 * 
	 * @param MLP The MultiLayered Network
	 */
	public void initiate(MultiLayerPerceptron MLP) {
		int xplus = 0;

		for (int i = 0; i < MLP.getLayersCount(); i++) {
			layer.get(i).clear();
			int neuronsAtThisLayer = MLP.getLayerAt(i).getNeuronsCount();
			xplus = width / (neuronsAtThisLayer + 1);
			for (int j = 0; j < neuronsAtThisLayer; j++) {

				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				int r = (width / neuronsAtThisLayer) / 2;
				if (r > 100) {
					r = 100;
				}
				layer.get(i).add(new NeuroPaint((j + 1) * xplus, i * 195 + 50, r));
				layer.get(i).get(j).setStrength(netInput);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());

			}
		}
	}

	/**
	 * Updates the Strengths, input connections and Weigths for each Neuron Calls paint() after the update
	 * 
	 * @param MLP The MultiLayered Network
	 */
	public void update2(MultiLayerPerceptron MLP) {
		for (int i = 0; i < layer.size(); i++) {
			for (int j = 0; j < layer.get(i).size(); j++) {
				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				layer.get(i).get(j).setStrength(netInput * 2);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());
			}
		}
		
		paintComponent();
	}

	/**
	 * Paints the updated network
	 */
	public void paintComponent() {
		Graphics2D g = null;
//		do {
			try {
				g = (Graphics2D) this.getGraphics();
				drawAll(g);
			} finally {
				g.dispose();
			}
//			bs.show();
//		} while (bs.contentsLost());
	}

	/**
	 * Draws the entire network i the Graphics g Starts from the left in the first layer, drawing first the neuron then
	 * the connections. Only draws the 3 highest connections from each neuron
	 * 
	 * @param g the graphics in which to draw the network
	 */
	public void drawAll(Graphics2D g) {
		//g.clearRect(0, 0, width, height);

		ArrayList<Integer> temp;
		ArrayList<Double> weights;
		for (int i = 0; i < layer.size(); ++i) {
			if (layer.get(i) != null) {
				for (int j = 0; j < layer.get(i).size(); j++) {
					if (layer.get(i).get(j) != null) {
						g.setStroke(new BasicStroke(0));
						g.setColor(layer.get(i).get(j).getColor());
						g.fill(layer.get(i).get(j).getShape());

						g.setColor(Color.BLACK);
						g.draw(layer.get(i).get(j).getShape());

						temp = layer.get(i).get(j).getInputConnections();
						weights = layer.get(i).get(j).getWeights();

						for (int input = 0; input < 100; input++) {
							// int weight = (int)Math.abs(weights.get(input).doubleValue());

							if (weights.size() > 0) {
								int indexToRemove = removeMax(weights);
								int weight = Math.abs(weights.get(indexToRemove).intValue());
								g.setColor(new Color(0, 0, 0, 125));
								g.setStroke(new BasicStroke(weight / 4));

								g.drawLine(layer.get(i).get(j).getX(),
										layer.get(i).get(j).getY() - layer.get(i).get(j).getR(),
										layer.get(i - 1).get(temp.get(indexToRemove)).getX(),
										layer.get(i - 1).get(temp.get(indexToRemove)).getY()
												+ layer.get(i - 1).get(temp.get(indexToRemove)).getR());
								weights.remove(indexToRemove);
								temp.remove(indexToRemove);
							}
						}
					}

				}
			}
		}
	

	}

	@Override
	public void run() {

	}

	/**
	 * returns the index of the highest number in a list, used to remove the highest weight after painting it
	 * 
	 * @param list List of doubles
	 * @return the index of the highest number
	 */
	private int removeMax(ArrayList<Double> list) {
		int index = 0;
		int currentIndex = 0;
		double max = -1000;
		for (double number : list) {
			if (number > max) {
				max = number;
				index = currentIndex;
				currentIndex++;
			}
		}

		return index;
	}

}
