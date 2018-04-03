package control;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import entity.Match;
import entity.Team;

public class TestAI {

	public static double[] getOutputForTeam(int round, Team team) {

		NeuralNetwork test = NeuralNetwork.load("test.nnet");
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
		test.calculate();
		double[] output = test.getOutput();

		return output;
	}

	public static int getOutputForMatch(Match match){
		NeuralNetwork test = NeuralNetwork.load("test.nnet");
		test.setInput(match.getMatchArray(5));
		test.calculate();

		double[] output = test.getOutput();
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(match.getHomeGoals() + " - " + match.getAwayGoals());
		System.out.println("---");
		return getPrediction(output, match.get1X2Outcome());
	}
	
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
	
	public static void printGameOutcome(int round, Team homeTeam, Team awayTeam) {
		double[] homeTeamOutput = getOutputForTeam(round, homeTeam);
		double[] awayTeamOutput = getOutputForTeam(round, awayTeam);

		System.out.println("----------------------");
		System.out.println(homeTeam.getName());
		for (int i=0; i<homeTeamOutput.length; i++) {
			System.out.println(homeTeamOutput[i]);
		}
		System.out.println("vs");
		System.out.println(awayTeam.getName());
		for (int i=0; i<awayTeamOutput.length; i++) {
			System.out.println(awayTeamOutput[i]);
		}
		System.out.println("----------------------");
	}

}