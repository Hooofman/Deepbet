package control;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import boundary.WriteToFile;
import entity.Match;

/**
 * Class that produces the output for a match. Uses a match-object and tests it parameters against the trained the network
 * @author Oscar
 *
 */

public class ProduceOutput {
	
	/**
	 * Tests a match against the network to get an output of how the match will end
	 * @param match the match to test
	 */

	public static void getOutputForMatch(Match match){
		NeuralNetwork test = NeuralNetwork.load("test.nnet"); // Load the trained network
		test.setInput(match.getMatchArray()); // Get the array from the match
		test.calculate(); // Test the match against the network
		
		// Get the output and save it
		double[] output = test.getOutput(); 
		match.setCalcOutput(output);
		
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(Arrays.toString(match.getMatchArray()));
		System.out.println("---");

		WriteToFile.appendTxt("---");
		WriteToFile.appendTxt(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		WriteToFile.appendTxt("---");
	}
}
