package neurophtrainer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import org.neuroph.*;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

/**
 *
 * @author zoran
 */
public class MLPTrainer {

    Vector<MLPTrainingSettings> trainingSettings;
    double minLearningRate = 0.1, maxLearningRate = 0.99, learningRateStep = 0.1;
    double minMomentum = 0, maxMomentum = 0.9, momentumStep = 0.1;
    int minHiddenNeurons, maxHiddenNeurons;
    int minHiddenLayers = 1, maxHiddenLayers = 1;
    double maxError, maxIterations, minErrorChange, minErrorChangeIterations;
    
    DataSet trainingSet;
    Vector<Vector> allLayerVariations = new Vector<Vector>();

    PrintWriter logWritter;
	private int firstMin;
	private int secondMax;
	private int secondMin;
	private int firstMax;

    public MLPTrainer(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public void run() {
        this.generateTrainingSettings();

        try {
            logWritter = new PrintWriter(new BufferedWriter(new FileWriter("training.log")));
        }  catch(IOException ioe) {
            ioe.printStackTrace();
        }

        for (int j = 0; j < trainingSettings.size(); j++) {
            Vector hiddenLayersNeuronCount = new Vector();
            logWritter.print( trainingSettings.get(j).getTrainingSet().getInputSize());
            hiddenLayersNeuronCount.add(22); // inputs
            hiddenLayersNeuronCount.addAll(trainingSettings.get(j).getHiddenLayers()); // hidden
            hiddenLayersNeuronCount.add(3); // outputs
            logWritter.flush();

            MultiLayerPerceptron nnet = new MultiLayerPerceptron(hiddenLayersNeuronCount);
            MomentumBackpropagation learningRule = ((MomentumBackpropagation) nnet.getLearningRule());
            learningRule.setLearningRate(trainingSettings.get(j).getLearningRate());
            learningRule.setMaxError(trainingSettings.get(j).getMaxError());
            learningRule.setMomentum(trainingSettings.get(j).getMomentum());
            learningRule.setMinErrorChange(0.001);
            learningRule.setMinErrorChangeIterationsLimit(100);
            learningRule.setMaxIterations(1000);
           
            
            System.out.println(j + ". Training network with following settings: " + trainingSettings.get(j).toString());
            logWritter.print(trainingSettings.get(j).toString());      
            //nnet.learn(trainingSettings.get(j).getTrainingSet());
            new TreadTraining(nnet, trainingSettings.get(j), j+"").start();
           
        }
        logWritter.flush();
        logWritter.close();

    }


    private void generateTrainingSettings() {
        trainingSettings = new Vector<MLPTrainingSettings>();
        int i = 0;

        int[] hiddenNeurons = new int[maxHiddenNeurons - minHiddenNeurons + 1];
        for (int hiddenNeuronsCount = minHiddenNeurons; hiddenNeuronsCount <= maxHiddenNeurons; hiddenNeuronsCount++) {
            hiddenNeurons[i] = hiddenNeuronsCount;
            i++;
        }

        // try all hidden layer combinations
//        for (int hiddenLayersCount = minHiddenLayers; hiddenLayersCount <= maxHiddenLayers; hiddenLayersCount++) {
//            generateAllVariations(hiddenNeurons, hiddenLayersCount, new Vector());
//        }
        
       for(int first = this.firstMin; first <=this.firstMax; first++) {
    	   for(int second = this.secondMin; second <= this.secondMax; second++ ) {
    		   Vector vector = new Vector();
    		   vector.add(first);
    		   if(second != 0) {
    			   vector.add(second);
    		   }
    		   allLayerVariations.add((Vector)vector.clone());
    	   }
       }
       
       System.out.println(allLayerVariations);

        // try all learningRate and momentum combinations
        for (double learningRate = minLearningRate; learningRate <= maxLearningRate; learningRate += learningRateStep) {
            for (double momentum = minMomentum; momentum <= maxMomentum; momentum += momentumStep) {
                for (Vector hiddenLayersSettings : allLayerVariations ) {
                   trainingSettings.add(new MLPTrainingSettings(learningRate, momentum, 0.1, hiddenLayersSettings, trainingSet));
                }
            }
        }
    }
    
    private void generateTest() {
    	
    }

    private void generateAllVariations(int[] elements, int depth, Vector variation) {
        if (depth == 0) {
           // System.out.println(variation);
            allLayerVariations.add((Vector)variation.clone());
        } else {
            for (int i = 0; i < elements.length; i++) {
                variation.add(elements[i]);
                generateAllVariations(elements, depth - 1, variation);
                variation.remove(variation.size() - 1);
            }
        }
    }

    public double getLearningRateStep() {
        return learningRateStep;
    }

    public void setLearningRateStep(double learningRateStep) {
        this.learningRateStep = learningRateStep;
    }

    public int getMaxHiddenLayers() {
        return maxHiddenLayers;
    }

    public void setMaxHiddenLayers(int maxHiddenLayers) {
        this.maxHiddenLayers = maxHiddenLayers;
    }

    public int getMaxHiddenNeurons() {
        return maxHiddenNeurons;
    }

    public void setMaxHiddenNeurons(int maxHiddenNeurons) {
        this.maxHiddenNeurons = maxHiddenNeurons;
    }

    public double getMaxLearningRate() {
        return maxLearningRate;
    }

    public void setMaxLearningRate(double maxLearningRate) {
        this.maxLearningRate = maxLearningRate;
    }

    public double getMaxMomentum() {
        return maxMomentum;
    }

    public void setMaxMomentum(double maxMomentum) {
        this.maxMomentum = maxMomentum;
    }

    public int getMinHiddenLayers() {
        return minHiddenLayers;
    }

    public void setMinHiddenLayers(int minHiddenLayers) {
        this.minHiddenLayers = minHiddenLayers;
    }

    public int getMinHiddenNeurons() {
        return minHiddenNeurons;
    }

    public void setMinHiddenNeurons(int minHiddenNeurons) {
        this.minHiddenNeurons = minHiddenNeurons;
    }

    public double getMinLearningRate() {
        return minLearningRate;
    }

    public void setMinLearningRate(double minLearningRate) {
        this.minLearningRate = minLearningRate;
    }

    public double getMinMomentum() {
        return minMomentum;
    }

    public void setMinMomentum(double minMomentum) {
        this.minMomentum = minMomentum;
    }

    public double getMomentumStep() {
        return momentumStep;
    }

    public void setMomentumStep(double momentumStep) {
        this.momentumStep = momentumStep;
    }

    public DataSet getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public Vector<MLPTrainingSettings> getTrainingSettings() {
        return trainingSettings;
    }

    public void setTrainingSettings(Vector<MLPTrainingSettings> trainingSettings) {
        this.trainingSettings = trainingSettings;
    }

    private void writeLog(String logEntry) {

    }

	public void setFirstHiddenLayerMin(int i) {
		this.firstMin = i;	
	}

	public void setFirstHiddenLayerMax(int i) {
		this.firstMax = i;
		
	}

	public void setSecondHiddenLayerMin(int i) {
		this.secondMin = i;
		
	}

	public void setSecondHiddenLayerMax(int i) {
		this.secondMax = i;
		
	}
}
