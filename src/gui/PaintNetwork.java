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
	int height = 700;
	int width = 1500;


	public PaintNetwork(MultiLayerPerceptron MLP) throws HeadlessException {
		setTitle("Network");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		for(int i = 0; i< MLP.getLayersCount(); i++) {
			layer.add(new ArrayList<NeuroPaint>());
		}
		createBufferStrategy(2);
		bs = this.getBufferStrategy();
	}

	public void initiate(MultiLayerPerceptron MLP) {
		int inputNeurons = MLP.getInputNeurons().size();
		int xplus = width/(inputNeurons+1);
		for(int j = 0; j< inputNeurons; j++) {
			inputLayer.add(new NeuroPaint((j+1)*xplus, 50, 25));
		}

		int outputNeurons = MLP.getOutputNeurons().size();

		xplus = width/(outputNeurons+1);
		for(int j = 0; j< outputNeurons; j++) {
			outputLayer.add(new NeuroPaint((j+1)*xplus, height-50, 25));
		}

		for(int i = 0; i < MLP.getLayersCount(); i++) {
			layer.get(i).clear();
			int neuronsAtThisLayer = MLP.getLayerAt(i).getNeuronsCount();
			xplus = width/(neuronsAtThisLayer+1);
			for(int j = 0; j < neuronsAtThisLayer; j++) {

				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				int r = (width/neuronsAtThisLayer)/2;
				if(r > 100) {
					r = 100;
				}
				layer.get(i).add(new NeuroPaint((j+1)*xplus, i*150+100,r));
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

		int outputNeurons = MLP.getOutputNeurons().size();
		double[] strength = MLP.getOutput(); 
		for(int j = 0; j< outputNeurons; j++) {

			outputLayer.get(j).setStrength(strength[j]);
			outputLayer.get(j).setInputConnections(MLP.getOutputNeurons().get(j).getInputConnections());
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
						g.setStroke(new BasicStroke(0));
						g.setColor(layer.get(i).get(j).getColor());
						g.fill(layer.get(i).get(j).getShape());
						
						g.setColor(Color.BLACK);
						g.draw(layer.get(i).get(j).getShape());

						temp = layer.get(i).get(j).getInputConnections();
						weights = layer.get(i).get(j).getWeights();

						for(int input = 0; input < 10; input++) {
							//int weight = (int)Math.abs(weights.get(input).doubleValue());

							if(weights.size() > 0) {
								int indexToRemove = removeMax(weights);
								int weight = Math.abs(weights.get(indexToRemove).intValue());
								g.setColor(new Color(0,0,0,125));
								g.setStroke(new BasicStroke(weight/4));

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
		for (int i = 0; i < outputLayer.size(); ++i) {

			temp = outputLayer.get(i).getInputConnections();
			if (outputLayer.get(i) != null) {
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(1));
				g.draw(outputLayer.get(i).getShape());
				g.setColor(outputLayer.get(i).getColor());
				g.fill(outputLayer.get(i).getShape());
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(5));
				g.drawLine(outputLayer.get(i).getX(), outputLayer.get(i).getY() - outputLayer.get(i).getR(), layer.get(layer.size()-1).get(temp.get(i)).getX(), layer.get(layer.size()-1).get(temp.get(i)).getY() + layer.get(layer.size()-1).get(temp.get(i)).getR());
			}
		}
		g.setStroke(new BasicStroke(1));
		for (int i = 0; i < inputLayer.size(); ++i) {
			if (inputLayer.get(i) != null) {
				g.draw(inputLayer.get(i).getShape());
			}
		}

	}

	@Override
	public void run() {

	}

	private int removeMax(ArrayList<Double> list) {
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
