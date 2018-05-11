package gui;


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



public class NeuroPaint implements java.awt.Shape{
	private int x;


	private int y;
	private int r;
	private double strength;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<Integer> connections = new ArrayList<Integer>();
	ArrayList<Double> weights = new ArrayList<Double>();
	
	public double getStrength() {
		return Math.round(10*strength*strength);
	}
	
	public Color getColor() {
		int test = Math.min(255,  (int)(10*strength*strength));
		return new Color(255, 0, 0, test);
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public NeuroPaint(int x, int y, int r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		shapes.add(new Ellipse2D.Double(x-r/2, y-r/2, r, r));
	}
	
	public Shape getShape() {
		return shapes.get(0);
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

	public void setInputConnections(List<Connection> inputConnections) {
		weights.clear();
		connections.clear();
		Iterator it = inputConnections.iterator();
		while(it.hasNext()) {
			Connection conn = (Connection) it.next();
			double weight = conn.getWeight().value;
			Neuron neuron = conn.getFromNeuron();
			connections.add(neuron.getParentLayer().indexOf(neuron));
			weights.add(weight);
		}
		
	}
	
	public ArrayList<Integer> getInputConnections() {
		return connections;
	}

	public ArrayList<Double> getWeights() {
		return weights;
	}

	
}
