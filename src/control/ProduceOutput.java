package control;

import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import entity.Match;

public class ProduceOutput {

	public static void getOutputForMatch(Match match){
		NeuralNetwork test = NeuralNetwork.load("test.nnet");
		test.setInput(match.getMatchArray(5));
		test.calculate();

		double[] output = test.getOutput();
		match.setCalcOutput(output);
		
		System.out.println("---");
		System.out.println(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		System.out.println(Arrays.toString(match.getMatchArray(5)));
		System.out.println("---");

		WriteToFile.appendTxt("---");
		WriteToFile.appendTxt(match.getHomeTeam() +" vs " + match.getAwayTeam() +": " + output[0] + "\t"+ output[1] + "\t" + output[2]);
		WriteToFile.appendTxt("---");
	}
}
