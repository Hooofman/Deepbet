package neurophtrainer;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.neuroph.core.*;
import org.neuroph.core.data.DataSet;

import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;

import control.LeagueCreator;
import entity.League;

/**
 *
 * @author zoran
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
		int[] plApiId = {398, 426, 445};
		LeagueCreator ligaSkapare = null;
		int matchesToGetDataFor = 27;
		int matchToTestOn = 28;

		try {
			ligaSkapare = new LeagueCreator();
			ligaSkapare.start("PL", plApiId, matchesToGetDataFor);
		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}

		League league = ligaSkapare.getLeague();
		
		DataSet trainingSet = DataSet.load("test");
		

        MLPTrainer trainer = new MLPTrainer(trainingSet);
        
        // set hidden layer range
        trainer.setMinHiddenLayers(1);
        trainer.setMaxHiddenLayers(1);
        // set hidden neurons per layer range
        trainer.setMinHiddenNeurons(7);
        trainer.setMaxHiddenNeurons(15);
        // set learning rate range
        trainer.setMinLearningRate(0.1);
        trainer.setMaxLearningRate(0.1);
        // set momentm range
        trainer.setMinMomentum(0.7);
        trainer.setMaxMomentum(0.7);
        
        
        
        // run trainer
        trainer.run(); 

    }

}
