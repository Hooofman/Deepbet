package gui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
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

import org.neuroph.nnet.MultiLayerPerceptron;




public class PaintNetwork  extends JFrame implements Runnable{
	private ArrayList<ArrayList<NeuroPaint>> layer = new ArrayList<ArrayList<NeuroPaint>>();
	private ArrayList<NeuroPaint> inputLayer = new ArrayList<NeuroPaint>();
	private ArrayList<NeuroPaint> outputLayer = new ArrayList<NeuroPaint>();
	BufferStrategy bs;
	int height = 710;
	int width = 1500;


	public PaintNetwork(MultiLayerPerceptron MLP) throws HeadlessException {
		setTitle("Network");
		setSize(width, height);
		setVisible(true);
		for(int i = 0; i< MLP.getLayersCount(); i++) {
			layer.add(new ArrayList<NeuroPaint>());
		}
		createBufferStrategy(2);
		bs = this.getBufferStrategy();
	}

	public void initiate(MultiLayerPerceptron MLP) {
		for(int i = 0; i < MLP.getLayersCount(); i++) {
			layer.get(i).clear();
			int neuronsAtThisLayer = MLP.getLayerAt(i).getNeuronsCount();
			int xplus = width/(neuronsAtThisLayer+1);
			for(int j = 0; j < neuronsAtThisLayer; j++) {

				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				int r = (width/neuronsAtThisLayer)/2;
				if(r > 100) {
					r = 100;
				}
				layer.get(i).add(new NeuroPaint((j+1)*xplus, i*200+50,r));
				layer.get(i).get(j).setStrength(netInput);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());
			}
		}
	}

	public void update(MultiLayerPerceptron MLP) {
		for(int i = 0; i < layer.size(); i++) {
			for(int j = 0; j < layer.get(i).size(); j++) {
				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				layer.get(i).get(j).setStrength(netInput*2);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());
			}
		}
		paint();
	}


	public void paint() {
		Graphics2D g = null;
		do {
			try{
				g = (Graphics2D) bs.getDrawGraphics();
				drawAll(g);
			} finally {
				g.dispose();
			}
			bs.show();
		} while (bs.contentsLost());
	}


	public void drawAll(Graphics2D g) {
		g.clearRect(0, 0, width, height);

		ArrayList<Integer> temp;
		ArrayList<Double> weights;
		int highest = 5;
		int lowest = 0;
		for (int i = 0; i < layer.size(); ++i) {
			if (layer.get(i) != null) {
				for (int j = 0; j < layer.get(i).size(); j++) {
					if (layer.get(i).get(j) != null) {
						//Draws outline for each neuron
						g.setStroke(new BasicStroke(0));
						g.setColor(Color.BLACK);
						g.draw(layer.get(i).get(j).getShape());
						//Fills the neuron with color
						g.setStroke(new BasicStroke(0));
						g.setColor(layer.get(i).get(j).getColor());
						g.fill(layer.get(i).get(j).getShape());

						temp = layer.get(i).get(j).getInputConnections();
						weights = layer.get(i).get(j).getWeights();

						for(int input = 0; input < 10; input++) {
							if(weights.size() > 0) {
								int indexToRemove = getIndexOfHighest(weights);
								int weight = 4*Math.abs(weights.get(indexToRemove).intValue());
								if(weight > 255) {
									weight = 255;
								}
								g.setColor(new Color(0,0,0,weight));
//								g.setStroke(new BasicStroke(weight/4));

								g.drawLine(layer.get(i).get(j).getX(), layer.get(i).get(j).getY() - layer.get(i).get(j).getR(), layer.get(i-1).get(temp.get(indexToRemove)).getX(), layer.get(i-1).get(temp.get(indexToRemove)).getY() + layer.get(i-1).get(temp.get(indexToRemove)).getR());
								weights.remove(indexToRemove);
								temp.remove(indexToRemove);
							}
						}
					}

				}
			}
		}
		g.setStroke(new BasicStroke(1));
		
	}

	@Override
	public void run() {

	}

	private int getIndexOfHighest(ArrayList<Double> list) {
		int index = 0;
		int currentIndex = 0;
		double max = -1000;
		for(double number : list) {
			if(number>max) {
				max = number;
				index = currentIndex;
				currentIndex++;
			}
		}

		return index;
	}


}