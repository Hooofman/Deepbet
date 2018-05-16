package control;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.data.norm.Normalizer;

/**
 * MaxMin normalization method, which normalize data in regard to min and max elements in training set (by columns)
 * Normalization is done according to formula:
 * normalizedVector[i] = (vector[i] - min[i]) / (max[i] - min[i])
 * 
 * This class works fine if  max and min are both positive and we want to normalize to  [0,1]
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class Norm implements Normalizer {
    double[] maxIn, maxOut; // contains max values for in and out columns
    double[] minIn, minOut; // contains min values for in and out columns     


    @Override
    public void normalize(DataSet dataSet) {
        // find min i max vectors
        findMaxAndMinVectors(dataSet);
       
        for (DataSetRow row : dataSet.getRows()) {
           double[] normalizedInput = normalizeMaxMin(row.getInput(), minIn, maxIn);
           row.setInput(normalizedInput);
            
           if (dataSet.isSupervised()) {
                double[] normalizedOutput = normalizeMaxMin(row.getDesiredOutput(), minOut, maxOut);
                row.setDesiredOutput(normalizedOutput);
           }
        }

    }
    
  private void findMaxAndMinVectors(DataSet dataSet) {
        int inputSize = dataSet.getInputSize();
        int outputSize = dataSet.getOutputSize();
        
        maxIn = new double[inputSize];
        minIn = new double[inputSize];
        
        for(int i=0; i<inputSize; i++) {
            maxIn[i] = Double.MIN_VALUE;
            minIn[i] = Double.MAX_VALUE;
        }
        
        maxOut = new double[outputSize];
        minOut = new double[outputSize];   
        
        for(int i=0; i<outputSize; i++) {
            maxOut[i] = Double.MIN_VALUE;
            minOut[i] = Double.MAX_VALUE;
        }        

        for (DataSetRow dataSetRow : dataSet.getRows()) {
            double[] input = dataSetRow.getInput();
            for (int i = 0; i < inputSize; i++) {
                if (input[i] > maxIn[i]) {
                    maxIn[i] = input[i];
                }
                if (input[i] < minIn[i]) {
                    minIn[i] = input[i];
                }
            }
            
            double[] output = dataSetRow.getDesiredOutput();
            for (int i = 0; i < outputSize; i++) {
                if (output[i] > maxOut[i]) {
                    maxOut[i] = output[i];
                }
                if (output[i] < minOut[i]) {
                    minOut[i] = output[i];
                }
            }            
                                    
        }        
    }     
  
    
    public double[] normalizeMaxMin(double[] vector, double[] min, double[] max) {
        double[] normalizedVector = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (vector[i] - min[i]) / (max[i] - min[i]);
        }

        return normalizedVector;             
    }    
    
    /**
     * Used for normalizing the inputarray. Uses min and max values gathered from when the dataset was normalized
     * @author Oscar
     * 
     * @param array the matcharray
     * @return the normalized array
     */
    
    public double[] normalizeInput(double[] array) {
    	double[] normalizedArray = new double[array.length];
    	
    	for (int i = 0; i < array.length; i++) {
            normalizedArray[i] = (array[i] - minIn[i]) / (maxIn[i] - minIn[i]);
        }

        return normalizedArray;    
    }
}