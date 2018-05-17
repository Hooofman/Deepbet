package testing;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;

/**
 * Class for painting one Neuron in a neuron network
 * @author johannes.roos
 *
 */


public class NeuroPaint implements java.awt.Shape{
	private int x;
	private int y;
	private int d;


	private ArrayList<Double> strength;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<ArrayList<Integer>> connections = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Double>> weights = new ArrayList<ArrayList<Double>>();
	
	/**
	 * 
	 * @param x x coordination for the neuron in the canvas
	 * @param y y coordination for the neuron in the canvas
	 * @param d diameter for the neuron
	 */
	public NeuroPaint(int x, int y, int d) {
		super();
		this.x = x;
		this.y = y;
		this.d = d;
		this.strength = new ArrayList<Double>();
		shapes.add(new Ellipse2D.Double(x-d/2, y-d/2, d, d));
	}
	
	/**
	 * returns the radius for the neuron
	 * @return the diameter
	 */
	public int getR() {
		return d/2;
	}
	
	/**
	 * Sets the diameter for the neuron
	 * @param r the diameter
	 */
	public void setR(int r) {
		this.d = r;
	}
	
	/**
	 * returns the latest strength of the neuron
	 * @return the strength 
	 */
	public double getStrength() {
		double str = strength.get(strength.size()-1);
		return Math.round(10*str*str);
	}
	
	/**
	 * returns the color of the neuron. Based on the latest strength, a color is generated 
	 * @return Color
	 */
	public Color getColor() {
		double str = strength.get(strength.size()-1);
		int test = Math.min(255,  (int)(10*str*str));
		return new Color(255, 0, 0, test);
	}
	
	/**
	 * returns the X position
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate
	 * @param x position 
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * returns the Y position
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate 
	 * @param y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Adds a strength to the end of the strength array
	 * @param strength strength
	 */
	public void setStrength(double strength) {
		this.strength.add(strength);
	}
	
	/**
	 * Returns the last shape in the array
	 * @return Shape
	 */
	public Shape getShape() {
		return shapes.get(shapes.size()-1);
	}

	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the input connections to this neuron. 
	 * Since the connection updates every iteration the connections are cleared every iteration
	 * 
	 * @param inputConnections
	 */
	public void setInputConnections(List<Connection> inputConnections) {
		//connections.clear();
		Iterator it = inputConnections.iterator();
		ArrayList<Double> temp = new ArrayList<Double>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		while(it.hasNext()) {
			Connection conn = (Connection) it.next();
			double weight = conn.getWeight().value;
			Neuron neuron = conn.getFromNeuron();
			temp2.add(neuron.getParentLayer().indexOf(neuron));
			temp.add(weight);
		}
		weights.add(temp);
		connections.add(temp2);
	}
	/**
	 * returns the input connections to this neuron
	 * @return
	 */
	public ArrayList<Integer> getInputConnections() {
		return connections.get(connections.size()-1);
	}
	/**
	 * returns the latest weigth
	 * @return List of Weights
	 */
	public ArrayList<Double> getWeights() {
		return weights.get(weights.size()-1);
	}

	
}
