package test;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import entity.Match;
import entity.Team;
import control.*;
import boundary.*;

/**
 * Class used for testing the AI
 *
 */
public class TestAI {

	/**
	 * Tests a team against the network to produce an output that represents the teams current strength
	 * @param round what round to test for
	 * @param team what team to test for
	 * @return the current strength of the team
	 */
	public static double[] getOutputForTeam(int round, Team team) {
		NeuralNetwork test = NeuralNetwork.load("test.nnet"); // Load the trained network
		test.setInput(team.getSumOfGoals(round), 
				team.getSumOfGoalsAgainst(round),
				team.getTotalPoints(round),
				team.getTotalPointsHome(round),
				team.getTotalPointsAway(round),
				team.getLocationAndPointsPoints(round),
				team.pointsLastNGames(round, 5),
				team.getTablePosition(round),
				team.goalsForLastNGames(round, 5),
				team.goalsAgainstLastNGames(round, 5),
				team.getLocationAndPointsLocation(round));
		test.calculate(); // Calculate the teams strength
		double[] output = test.getOutput();
		return output;
	}

	/**
	 * Tests a match-object against the network to create an output
	 * @param match what match to test
	 * @return an int that either is 0 or 1, depending on if the AI did pick the correct outcome
	 */
	public static int getOutputForMatch(Match match){
		NeuralNetwork test = NeuralNetwork.load("test.nnet"); // Load the trained network
		test.setInput(match.getMatchArray(5)); // Get the input-array from the match
		test.calculate(); // Calculate the match
		double[] output = test.getOutput(); // Get the AIs predicition
		
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(match.getHomeGoals() + " - " + match.getAwayGoals());
		System.out.println(Arrays.toString(match.getMatchArray(5)));
		System.out.println("---");
		
		WriteToFile.appendTxt("---");
		WriteToFile.appendTxt(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		WriteToFile.appendTxt(match.getHomeGoals() + " - " + match.getAwayGoals());
		WriteToFile.appendTxt("---");
		
		return getPrediction(output, match.get1X2Outcome()); // Returns 1 for correct pick, 0 for incorrect pick
	}
	
	/**
	 * Method used for training and testing the network with a lot of different combinations of AI-settings
	 * @param match the match to test
	 * @param round the round to test for
	 * @param combinations used to find the correct network
	 * @return returns 1 for correct pick, 0 for incorrect pick
	 */
		public static int getOutputForMatch(Match match, int round, int combinations){
		NeuralNetwork test = NeuralNetwork.load("test_"+round+"_"+combinations+".nnet");
		test.setInput(match.getMatchArray(5));
		test.calculate();

		double[] output = test.getOutput();
		return getPrediction(output, match.get1X2Outcome());
	}
	
	/**
	 * Used to calculate if the AI picked the correct outcome for a match
	 * @param output the prediction made by the AI
	 * @param outcome the actual outcome of the match
	 * @return 1 for correct pick, 0 for incorrect pick
	 */
	public static int getPrediction(double[] output, double[] outcome) {
		double highestPrediciton = Math.max(output[0], Math.max(output[1], output[2]));
		double[] prediction = {0.0, 0.0, 0.0};
		if (highestPrediciton == output[0]) {
			prediction[0] = 1.0;
		} else if (highestPrediciton == output[1]) {
			prediction[1] = 1.0;
		} else if (highestPrediciton == output[2]) {
			prediction[2] = 1.0;
		}
		
		if (Arrays.equals(prediction, outcome)) {
			return 1;
		}
		return 0;
	}
}