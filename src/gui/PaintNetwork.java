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
import java.util.ArrayList;

import javax.swing.JFrame;

import org.neuroph.nnet.MultiLayerPerceptron;




public class PaintNetwork  extends JFrame implements Runnable{
	private ArrayList<ArrayList<NeuroPaint>> layer = new ArrayList<ArrayList<NeuroPaint>>();
	private ArrayList<NeuroPaint> inputLayer = new ArrayList<NeuroPaint>();
	private ArrayList<NeuroPaint> outputLayer = new ArrayList<NeuroPaint>();
	MyCanvas canvas;
	int height = 1000;
	int width = 1920;
	double zoom = 1;

	public PaintNetwork(MultiLayerPerceptron MLP) throws HeadlessException {
		canvas = new MyCanvas();
		add("Center", canvas);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		for(int i = 0; i< MLP.getLayersCount(); i++) {
			layer.add(new ArrayList<NeuroPaint>());
		}

	}

	public void update(MultiLayerPerceptron MLP) {
		int inputNeurons = MLP.getInputNeurons().size();
		int xplus = width/(inputNeurons+1);
		for(int j = 0; j< inputNeurons; j++) {
			inputLayer.add(new NeuroPaint((j+1)*xplus, 100, 25));
		}
		
		int outputNeurons = MLP.getOutputNeurons().size();
		
		xplus = width/(outputNeurons+1);
		for(int j = 0; j< outputNeurons; j++) {
			outputLayer.add(new NeuroPaint((j+1)*xplus, height-100, 25));
		}
		for(int i = 0; i < MLP.getLayersCount(); i++) {
			int neuronsAtThisLayer = MLP.getLayerAt(i).getNeuronsCount();
			xplus = width/(neuronsAtThisLayer+1);
			for(int j = 0; j < neuronsAtThisLayer; j++) {

				double netInput = MLP.getLayerAt(i).getNeuronAt(j).getNetInput();
				int r = (width/neuronsAtThisLayer)/2;
				if(r > 100) {
					r = 100;
				}
				layer.get(i).add(new NeuroPaint((j+1)*xplus, i*200+200,r));
				layer.get(i).get(j).setStrength(netInput);
				layer.get(i).get(j).setInputConnections(MLP.getLayerAt(i).getNeuronAt(j).getInputConnections());


			}
		}
		canvas.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	class MyCanvas extends Canvas {
		public void paint(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;
			for (int i = 0; i < outputLayer.size(); ++i) {
				if (outputLayer.get(i) != null) {
					g.draw(outputLayer.get(i).getShape());
				}
			}
			
			for (int i = 0; i < inputLayer.size(); ++i) {
				if (inputLayer.get(i) != null) {
					g.draw(inputLayer.get(i).getShape());
				}
			}

			for (int i = 0; i < layer.size(); ++i) {
				int highest = 0;
				int lowest = 0;
				if (layer.get(i) != null) {
					for (int j = 0; j < layer.get(i).size(); j++) {
						if (layer.get(i).get(j) != null) {
							g.setStroke(new BasicStroke(0));
							g.setColor(layer.get(i).get(j).getColor());
							g.fill(layer.get(i).get(j).getShape());
							g.draw(layer.get(i).get(j).getShape());
							//g.setColor(Color.BLACK);
							//	        			 g.setFont(getFont().deriveFont(20.0f));
							//	        			 g.drawString(layer.get(i).get(j).getStrength()+"", layer.get(i).get(j).getX()+15, layer.get(i).get(j).getY()-10);
							ArrayList<Integer> temp = layer.get(i).get(j).getInputConnections();
							ArrayList<Double> weights = layer.get(i).get(j).getWeights();
							for(int input = 0; input < temp.size(); input++) {
								int weight = (int)Math.abs(weights.get(input).doubleValue());
								highest = Math.max(highest, weight);
								lowest = Math.min(lowest, weight);
								if(weight > (highest+lowest)*7/8) {
									g.setColor(new Color(0,0,0,125));
									g.setStroke(new BasicStroke(weight/10));
									g.drawLine(layer.get(i).get(j).getX(), layer.get(i).get(j).getY(), layer.get(i-1).get(temp.get(input)).getX(), layer.get(i-1).get(temp.get(input)).getY());
								}
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


}
