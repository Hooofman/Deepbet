package neurophtrainer;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

public class TreadTraining extends Thread{
	private  MultiLayerPerceptron nnet;
	MLPTrainingSettings data;
	public TreadTraining( MultiLayerPerceptron nnet, MLPTrainingSettings data) {
		this.nnet = nnet;
		this.data = data;
	}
	
	public void run() {
		nnet.learn(data.getTrainingSet());
         System.out.println("Completed training with settings:" + data.toString());
         nnet.save("TrainingData\\" + data.toString());
         
	}

}
