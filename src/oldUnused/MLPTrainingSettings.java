/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oldUnused;

import java.util.Vector;

import org.neuroph.core.data.DataSet;


/**
 *
 * @author zoran
 */
public class MLPTrainingSettings {

    private double learningRate = 0.5;
    private double maxError = 0.01;
    private double minErrorChange = 0.0001;
    private double minErrorChangeIterations = 1000;
    private double momentum = 0.0;
    private Vector<Integer> hiddenLayers;
    private DataSet trainingSet;
    // add test and validation set

    public MLPTrainingSettings(double learningRate, double momentum, double maxError, Vector<Integer> hiddenLayers, DataSet trainingSet ) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.maxError = maxError;
        this.hiddenLayers = hiddenLayers;
        this.trainingSet = trainingSet;
    }

    public Vector<Integer> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(Vector<Integer> hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getMaxError() {
        return maxError;
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public DataSet getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public double getMinErrorChange() {
        return minErrorChange;
    }

    public void setMinErrorChange(double minErrorChange) {
        this.minErrorChange = minErrorChange;
    }

    public double getMinErrorChangeIterations() {
        return minErrorChangeIterations;
    }

    public void setMinErrorChangeIterations(double minErrorChangeIterations) {
        this.minErrorChangeIterations = minErrorChangeIterations;
    }

    public String toString() {
        return "Learning rate: " + learningRate + " Momentum: " + momentum + "Max error: " + maxError + "Hidden layers: " + hiddenLayers.toString();
    }




}
