package testing;

import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import boundary.ConnectDatabase;
import boundary.ReadFromFile;
import testing.GUI;
import testing.LoadSettings;
import testing.PaintNetworkLabel;
import testing.SaveSettings;

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
	private boolean shouldIDraw = false;
	private int startIt;
	private int endIt;
	private int incIt;
	private double startLR;
	private double endLR;
	private double incLR;
	private double startMomentum;
	private double endMomentum;
	private double incMomentum;
	private int nbrOfThreads;

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
		gui.setDbSettings(inputGUI);
	}

	public void setANNSettings(String str) {
		String[] inputGUI = str.split(",", 0);
		gui.setAnnSettings(inputGUI);
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
		gui.setNeuralNetworkPath(pathName);
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

	/**
	 * Loads settings from GUI
	 * 
	 * @param fileName the file to load settings from
	 * @param indicator defines what to load settings for
	 */
	public void loadFromComboBox(String fileName, String indicator) {
		String str = ReadFromFile.readFromFile(fileName);
		if (indicator.equals("nnet")) {
			gui.setNeuralNetworkPath(System.getProperty("user.dir") + "\\" + fileName);
			addToConsoleText("Network Path is set to " + System.getProperty("user.dir") + "\\" + fileName);
		} else if (indicator.equals("all")) {
			setAllSettings(str);
			addToConsoleText("All settings is loaded from " + System.getProperty("user.dir") + "\\" + fileName);
		} else if (indicator.equals("league")) {
			setLeagueSettings(str);
			addToConsoleText("League settings is loaded from " + System.getProperty("user.dir") + "\\" + fileName);
		}
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
		
		
		int number = 1;
		ExecutorService pool  = Executors.newFixedThreadPool(nbrOfThreads);
		for(int currentIt = this.startIt; currentIt < this.endIt; currentIt+=this.incIt) {
			for(double currentLR = this.startLR; currentLR < this.endLR; currentLR += this.incLR) {
				for(double currentMomentum = this.startMomentum; currentMomentum < this.endMomentum; currentMomentum += this.incMomentum) {
					String settings = "_"+currentIt+"_"+currentLR+"_"+currentMomentum;
					connection = new ConnectDatabase();
					pool.execute(new CalculationHandler(this, currentIt, currentLR, currentMomentum, NNPath, datasetName+"_"+number, finalNNName+"_"+number,
							leagueName, leageuAPIId.split(", "), table, settings, connection));
					number++;
				}
				
			}
			
			
		}
		pool.shutdown();    
	}
	
	public boolean shouldIDraw() {
		return shouldIDraw;
	}
	
	public void decideIfToDraw(boolean bool) {
		this.shouldIDraw = bool;
	}

	public void setMultiSettings(String startIt, String endIt, String incIt, String startLR, String endLR, String incLR,
			String startMomentum, String endMomentum, String incMomentum, String threads) {
		this.startIt = Integer.parseInt(startIt);
		this.endIt = Integer.parseInt(endIt);
		this.incIt = Integer.parseInt(incIt);
		
		this.startLR = Double.parseDouble(startLR);
		this.endLR = Double.parseDouble(endLR);
		this.incLR = Double.parseDouble(incLR);
		
		this.startMomentum = Double.parseDouble(startMomentum);
		this.endMomentum = Double.parseDouble(endMomentum);
		this.incMomentum = Double.parseDouble(incMomentum);
		this.nbrOfThreads = Integer.parseInt(threads);
		
	}



}
