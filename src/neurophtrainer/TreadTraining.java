package neurophtrainer;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

public class TreadTraining extends Thread{
	private  MultiLayerPerceptron nnet;
	MLPTrainingSettings data;
	String info;
	public TreadTraining( MultiLayerPerceptron nnet, MLPTrainingSettings data, String info) {
		this.nnet = nnet;
		this.data = data;
		this.info = info;
	}
	
	public void run() {
		nnet.learn(data.getTrainingSet());
         System.out.println("Completed training with settings:" + data.toString());
         nnet.save("test_"+info);
         
	}

}
