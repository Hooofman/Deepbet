package control;

import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

import boundary.ConnectDatabase;
import boundary.ReadFromFile;
import gui.GUI;
import gui.LoadSettings;
import gui.PaintNetwork;
import gui.SaveSettings;

/**
 * The main-controller class
 * 
 * @author Oscar, Sven, Johannes
 *
 */
public class Controller {
	private GUI gui;
	private CalculationHandler calcHandler;
	private ConnectDatabase connection;

	/**
	 * Constructor. Creates new DB-connection, points console-prints to GUI.
	 * 
	 * @param gui the GUI-instance to use
	 */
	public Controller(GUI gui) {
		this.gui = gui;
		gui.setController(this);
		connection = new ConnectDatabase();
		pointConsoleToDocument();
	}

	/**
	 * Redirects the System.out.print to the documents instead of console A new thread listens to this new outputstream
	 * and prits in to the gui
	 */
	public void pointConsoleToDocument() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		System.setErr(ps);

		new Thread() {
			public void run() {
				while (true) {
					if (baos.toString().length() > 0) {
						System.out.flush();
						gui.addToTextConsole(baos.toString());
						baos.reset();
					}
					try {
						this.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void saveSettings(String indicator, String data) {
		new SaveSettings(indicator, data);
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
		String[] inputGUI = str.split(",", 2);
		gui.setLeagueSettings(inputGUI);
	}

	public void setAllSettings(String str) {
		String[] inputGUI = str.split(",", 12);
		gui.setAllSettings(inputGUI);
	}

	public void setNeuralNetworkPath(String pathName) {
		gui.setNeuralNetworkPathName(pathName);
	}

	public void addToConsoleText(String txt) {
		gui.addToTextConsole(txt);
	}

	/**
	 * Loads autosaved settings
	 * 
	 * @param fileName what file to load settings from
	 */
	public void loadAutoSaved(String fileName) {
		String path = System.getProperty("user.dir") + fileName;
		String str = ReadFromFile.readFromFile(path);
		setAllSettings(str);
	}

	public void disableButtons() {
		gui.disableButtons();
	}

	public void enableButtons() {
		gui.enableButtons();
	}

	/**
	 * Update progress in gui
	 * 
	 * @param currentIteration current iteration
	 * @param maxIteration the amount of iterations that will be done
	 */
	public void updateProgress(int currentIteration, int maxIteration) {
		int progress = 1000 * currentIteration / maxIteration;
		gui.showProgress(progress);
	}

	public void sendNetworkFrame(PaintNetwork frame) {
		gui.addNetworkFrame(frame);
	}

	/**
	 * Loads settings from GUI
	 * 
	 * @param fileName the file to load settings from
	 * @param indicator defines what to load settings for
	 */
	public void loadFromComboBox(String fileName, String indicator) {
		String str = ReadFromFile.readFromFile(fileName);
		if (indicator.equals("nnet")) {
			gui.setNeuralNetworkPathName(System.getProperty("user.dir") + "\\" + fileName);
			addToConsoleText("Network Path is set to " + System.getProperty("user.dir") + "\\" + fileName);
		} else if (indicator.equals("all")) {
			setAllSettings(str);
			addToConsoleText("All settings is loaded from " + System.getProperty("user.dir") + "\\" + fileName);
		} else if (indicator.equals("league")) {
			setLeagueSettings(str);
			addToConsoleText("League settings is loaded from " + System.getProperty("user.dir") + "\\" + fileName);
		}
	}
	
	public Graphics getGraphicsFromGui() {
		return gui.getNetworkPanelGraphics();
	}

	public void setDataBaseSettings(String dataBaseURL, String userName, String passWord, String maxPool,
			String table) {
		connection.setDatabaseSettings(dataBaseURL, userName, passWord, maxPool, table);
	}

	/**
	 * Receives settings for the neural network and sends them forward to the classes that handles the AI.
	 * 
	 * @param it Number of iterations.
	 * @param learnRate The learning rate that is to be used.
	 * @param momentu The Momentum that is to be used.
	 * @param NNPath The search path to the neural network template.
	 */
	public void calculate(String it, String learnRate, String momentu, String NNPath, String datasetName,
			String finalNNName, String leagueName, String leageuAPIId, String table) {

		int iterations = Integer.parseInt(it);
		double learningRate = Double.parseDouble(learnRate);
		double momentum = Double.parseDouble(momentu);
		calcHandler = new CalculationHandler(this, iterations, learningRate, momentum, NNPath, datasetName, finalNNName,
				leagueName, leageuAPIId.split(", "), table, connection);
	}

}
