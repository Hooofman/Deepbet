package neurophtrainer;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

public class TreadTraining extends Thread{
	private  MultiLayerPerceptron nnet;
	MLPTrainingSettings data;
	String info;
	String round;
	public TreadTraining( MultiLayerPerceptron nnet, MLPTrainingSettings data, String round, String info) {
		this.nnet = nnet;
		this.data = data;
		this.info = info;
		this.round = round;
	}
	
	public void run() {
		nnet.learn(data.getTrainingSet());
         System.out.println("Completed training with settings:" + data.toString());
         nnet.save("test_"+round+"_"+info);
         
	}

}
