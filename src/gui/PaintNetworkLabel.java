package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.neuroph.nnet.MultiLayerPerceptron;

public class PaintNetworkLabel extends JPanel implements Runnable{
	private ArrayList<ArrayList<NeuroPaint>> layer = new ArrayList<ArrayList<NeuroPaint>>();
	private ArrayList<NeuroPaint> inputLayer = new ArrayList<NeuroPaint>();
	private ArrayList<NeuroPaint> outputLayer = new ArrayList<NeuroPaint>();
	int height = 0;
	int width = 0;


	public PaintNetworkLabel(MultiLayerPerceptron MLP) {
		for (int i = 0; i < MLP.getLayersCount(); i++) {
			layer.add(new ArrayList<NeuroPaint>());
		}
		this.setDoubleBuffered(true);

	}

	public void setSizeForLabel(int height, int width) {
		this.height = height;
		this.width = width;
		setSize(width, height);
	}

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

	public void updateInputConnections(MultiLayerPerceptron MLP) {
		for (int i = 0; i < layer.size(); i++) {
			for (int j = 0; j < layer.get(i).size(); j++) {
				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				layer.get(i).get(j).setStrength(netInput * 2);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());
			}
		}
	}



	public void drawAll(Graphics2D g) {
		g.clearRect(0, 0, width, height);

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
							if (weights.size() > 0) {
								int indexToRemove = getIndexOfHighest(weights);
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

	private int getIndexOfHighest(ArrayList<Double> list) {
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
	/**
	 * Comment just to be able to push?
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		drawAll(g2d);
		super.paintComponent(g2d);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
