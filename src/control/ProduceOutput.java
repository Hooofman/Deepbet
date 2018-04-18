package control;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import boundary.WriteToFile;
import entity.Match;

/**
 * Class that produces the output for a match. Uses a match-object and tests it parameters against the trained the network
 * @author Oscar Malmqvist 
 *
 */
public class ProduceOutput {
	
	/**
	 * Tests a match against the network to get an output of how the match will end
	 * @param match the match to test
	 * @param norm the normalized values 
	 */
	public static void getOutputForMatch(Match match, Norm norm){
		NeuralNetwork test = NeuralNetwork.createFromFile("test.nnet"); // Load the trained network
		double[] inputArray = norm.normalizeInput(match.getMatchArray());
		test.setInput(inputArray); // Get the array from the match
		test.calculate(); // Test the match against the network
		
		// Get the output and save it
		double[] output = test.getOutput(); 
		match.setCalcOutput(output);
		
		// Get the pick in char-form and save it to the match-object
		double pick = Math.max(output[0], Math.max(output[1], output[2]));
		if (pick == output[0]) {
			match.setRecommendation('1');
		} else if (pick == output[1]) {
			match.setRecommendation('X');
		} else if (pick == output[2]) {
			match.setRecommendation('2');
		}
		
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(Arrays.toString(match.getMatchArray()));
		System.out.println(Arrays.toString(inputArray));
		System.out.println("---");

		WriteToFile.appendTxt("---");
		WriteToFile.appendTxt(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		WriteToFile.appendTxt("---");
	}
}
