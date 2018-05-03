package gui;

import javax.swing.SwingUtilities;

public class Controller {
	private GUI gui;
	private CalculationHandler calcHandler;

	public Controller(GUI gui) {
		this.gui = gui;
		gui.setController(this);
	}

	public void saveSettings(String indicator, String data) {
		new SaveSettings(this, indicator, data);
	}

	public void loadSettings(String indicator) {
		new LoadSettings(this, indicator);
	}

	public void setDBSettings(String str) {
		String[] inputGUI = str.split(",", 0);
		gui.setDBSettings(inputGUI);
	}

	public void setANNSettings(String str) {
		String[] inputGUI = str.split(",", 0);
		gui.setANNSettings(inputGUI);
	}
	
	public void setLeagueSettings(String str) {
		String[] inputGUI = str.split(",", 0);
		gui.setLeagueSettings(inputGUI);
	}
	
	public void setAllSettings(String str) {
		String[] inputGUI = str.split(",", 0);
		gui.setAllSettings(inputGUI);
	}
	public void setNeuralNetworkPath(String pathName) {
		gui.setNeuralNetworkPathName(pathName);
	}

	public void addToConsoleText(String txt) {
		gui.addToTextConsole(txt);
	}

	/**
	 * Receives settings for the neural network and sends them forward to the
	 * classes that handles the AI.
	 * 
	 * @param it
	 *            Number of iterations.
	 * @param learnRate
	 *            The learning rate that is to be used.
	 * @param momentu
	 *            The Momentum that is to be used.
	 * @param NNPath
	 *            The search path to the neural network template.
	 */
	public void calculate(String it, String learnRate, String momentu, String NNPath, String datasetName, String finalNNName, String leagueName, String leageuAPIId, String table) {
		int iterations = Integer.parseInt(it);
		double learningRate = Double.parseDouble(learnRate);
		double momentum = Double.parseDouble(momentu);
		calcHandler = new CalculationHandler(this, iterations, learningRate, momentum, NNPath, datasetName, finalNNName, leagueName, leageuAPIId.split(", "), table);
		calcHandler.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
				new Controller(gui);
			}
		});

	}
}