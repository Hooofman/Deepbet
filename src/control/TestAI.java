package control;

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

	public static double[] getOutputForMatch(Match match){
		NeuralNetwork test = NeuralNetwork.load("test.nnet");
		test.setInput(match.getMatchArray(5));
		test.calculate();

		double[] output = test.getOutput();
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(match.getHomeGoals() + " - " + match.getAwayGoals());
		System.out.println("---");
		return output;
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