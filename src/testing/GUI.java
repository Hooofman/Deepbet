package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyledDocument;

import testing.PaintNetworkLabel;
import testing.Controller;

/**
 * Graphical user interface for an administer to set settings for the AI, and
 * search paths for documents generated during the training of the network. It
 * also displays the results of the calculations, the progress of the training
 * and error messages.
 * 
 * @author Sven Lidqvist, Johannes Roos, Oscar Malmqvist.
 *
 */
public class GUI extends JFrame implements ActionListener {
	private Controller controller;
	private JPanel pnlMain;
	private JTextPane consolText;
	private JPanel pnlTextArea;
	private JScrollPane scrollBar;

	// Upper panel
	private JLabel lblTitle;
	private String logoPath = "images/deepbet_vit.png";
	private JProgressBar progressBar;

	private JTabbedPane pnlWest; // Holds the entire western content
	private JTabbedPane pnlCenter;
	private PaintNetworkLabel paintNetwork;
	private JPanel networkPanel;
	
	// Database input
	private JPanel pnlDB;
	private JTextField password;
	private JTextField userName;
	private JTextField dbAddress;
	private JTextField table;
	private JLabel lblPassword;
	private JLabel lblUserName;
	private JLabel lblDBAddress;
	private JLabel lblTable;
	private JButton btnSaveDBSettings;
	private JButton btnLoadDBSettings;

	// ANN settings
	private JPanel pnlANN;

	private JLabel lblIterations;
	private JLabel lblLearningRate;
	private JLabel lblMomentum;
	private JLabel lblNeuralNetworkPath;
	private JLabel lblDatasetName;
	private JLabel lblFinalNNName;
	private JButton btnLoadNetwork;
	private JButton btnSaveANNsettings;
	private JButton btnLoadANNSettings;

	private JTextField iterations;
	private JTextField learningRate;
	private JTextField momentum;
	private JTextField txtNeuralNetWorkPath;
	private JTextField txtDatasetName;
	private JTextField txtFinalNNName;

	// League panel
	private JPanel pnlLeague;
	private JLabel lblLeagueName;
	private JLabel lblLeagueAPIid;
	private JTextField txtLeagueName;
	private JTextField txtLeagueAPIid;
	private JButton btnSaveLeaguesettings;
	private JButton btnLoadLeagueSettings;

	private JButton btnSaveAll;
	private JButton btnLoadAll;

	private JButton btnCalc;

	private JComboBox comboBoxOpenAll;
	private JComboBox comboBoxOpenNetwork;
	private JComboBox comboBoxOpenLeague;

	private JPanel pnlBottom;
	private JPanel pnlUpper;
	private JPanel pnlButtons;

	private StyledDocument doc;
	/**
	 * Constructs the GUI.
	 */
	public GUI() {

		UIManager.put("ProgressBar.selectionForeground", Color.black);
		UIManager.put("ProgressBar.selectionBackground", Color.black);
		setTitle("DeepBet");

		pnlANN = new JPanel();
		pnlMain = new JPanel();
		pnlTextArea = new JPanel();
		pnlWest = new JTabbedPane();
		pnlCenter = new JTabbedPane();
		pnlDB = new JPanel();
		pnlLeague = new JPanel();
		password = new JTextField("Deepbet123");
		userName = new JTextField("deepbet");
		dbAddress = new JTextField("");
		table = new JTextField("games");

		progressBar = new JProgressBar(0, 1000);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		lblPassword = new JLabel("Password");
		lblUserName = new JLabel("User name");
		lblDBAddress = new JLabel("Database address");
		lblTable = new JLabel("Table to save in");
		btnSaveDBSettings = new JButton("Save database settings");
		btnLoadDBSettings = new JButton("Load database settings");

		consolText = new JTextPane();

		lblIterations = new JLabel("Number of iterations");
		lblLearningRate = new JLabel("Learing rate");
		lblMomentum = new JLabel("Momentum");
		lblNeuralNetworkPath = new JLabel("NN-template");
		lblFinalNNName = new JLabel("Trained NN name");
		lblDatasetName = new JLabel("Dataset name");
		btnLoadNetwork = new JButton("Load network template");
		btnSaveANNsettings = new JButton("Save network settings");
		btnLoadANNSettings = new JButton("Load network settings");

		iterations = new JTextField("20");
		learningRate = new JTextField("0.7");
		momentum = new JTextField("0.2");
		txtNeuralNetWorkPath = new JTextField("");
		txtDatasetName = new JTextField("Fixed");
		txtFinalNNName = new JTextField("");

		lblLeagueName = new JLabel("League name");
		lblLeagueAPIid = new JLabel("Season-IDs from API, separate with ,");
		txtLeagueName = new JTextField();
		txtLeagueAPIid = new JTextField();
		btnSaveLeaguesettings = new JButton("Save League settings");
		btnLoadLeagueSettings = new JButton("Load League settings");

		btnSaveAll = new JButton("Save All settings");
		btnLoadAll = new JButton("Load All settings");

		btnCalc = new JButton("Start calculation");

		comboBoxOpenAll = new JComboBox();
		comboBoxOpenNetwork = new JComboBox();
		comboBoxOpenLeague = new JComboBox();

		pnlBottom = new JPanel(); // Holds calc Button
		pnlUpper = new JPanel(); // Upper Panel
		pnlButtons = new JPanel(); // Holds buttons

		doc = consolText.getStyledDocument();
		fixComboBox();
		createWestPanel();
		createAnnPanel();
		createPnlDB();
		createLeaguePanel();
		createBottomPanel();
		createConsolePanel();
		createButtonPanel();
		createPnlUpper();
		createCenterPanel();
		createMainPnl();
		addActionListeners();

		this.add(pnlMain);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void addActionListeners() {
		btnLoadDBSettings.addActionListener(this);
		btnSaveDBSettings.addActionListener(this);
		btnLoadANNSettings.addActionListener(this);
		btnSaveANNsettings.addActionListener(this);
		btnLoadNetwork.addActionListener(this);
		btnCalc.addActionListener(this);
		btnLoadLeagueSettings.addActionListener(this);
		btnSaveLeaguesettings.addActionListener(this);
		btnLoadAll.addActionListener(this);
		btnSaveAll.addActionListener(this);
		comboBoxOpenAll.addActionListener(this);
		comboBoxOpenNetwork.addActionListener(this);
		comboBoxOpenLeague.addActionListener(this);
		pnlCenter.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				if(pnlCenter.getSelectedIndex() == 0) {
					controller.decideIfToDraw(false);
				}else {
					controller.decideIfToDraw(true);
				}
				
			}
		});
		

	}

	public void createPnlUpper() {
		lblTitle = new JLabel();
		lblTitle.setIcon(new ImageIcon(logoPath));
		pnlUpper.add(lblTitle);

		pnlUpper.setBackground(Color.DARK_GRAY);
	}

	public void createBottomPanel() {
		pnlBottom.setLayout(new GridLayout(1, 2));
		btnCalc.setFont(btnCalc.getFont().deriveFont(26.0f));
		btnCalc.setBackground(Color.GREEN);
		progressBar.setForeground(Color.GREEN);
		progressBar.setFont(progressBar.getFont().deriveFont(26.0f));
		pnlBottom.add(btnCalc);
		pnlBottom.setBackground(Color.BLACK);
		pnlBottom.add(progressBar);
	}

	public void createWestPanel() {
		pnlWest.setPreferredSize(new Dimension(400, 400));
		pnlWest.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlWest.addTab("Database", pnlDB);
		pnlWest.addTab("Network", pnlANN);
		pnlWest.addTab("League", pnlLeague);
		pnlWest.addTab("Options", pnlButtons);
		pnlWest.setVisible(true);
	}

	public void createCenterPanel() {
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlCenter.addTab("Console", pnlTextArea);
		pnlCenter.setVisible(true);
		
	}
	
	public Graphics getNetworkPanelGraphics() {
		return networkPanel.getGraphics();
	}

	public void addNetworkFrame(PaintNetworkLabel paintNetwork) {
		
		pnlCenter.setDoubleBuffered(true);
		paintNetwork.setSizeForLabel(pnlCenter.getHeight(), pnlTextArea.getWidth());
		paintNetwork.setOpaque(false);
		paintNetwork.setVisible(false);
		pnlCenter.add("Network", paintNetwork);
		
	}

	public void createConsolePanel() {
		pnlTextArea.setPreferredSize(new Dimension(800, 400));
		pnlTextArea.setLayout(new BorderLayout());
		pnlTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		consolText.setEditable(false);
		scrollBar = new JScrollPane(consolText);
		scrollBar.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollBar.setAutoscrolls(true);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlTextArea.add(scrollBar);

		// Scrolls the text-panel automatically when filled
		DefaultCaret caret = (DefaultCaret) consolText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void createMainPnl() {
		pnlMain.setLayout(new BorderLayout());

		pnlMain.add(pnlWest, BorderLayout.WEST);

		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlMain.add(pnlUpper, BorderLayout.NORTH);
	}

	/**
	 * Creates the panel holding the information concerning the database.
	 */
	public void createPnlDB() {
		pnlDB.setLayout(new GridLayout(5, 2, 5, 5));
		pnlDB.setBorder(new TitledBorder("Database"));

		Border border = pnlDB.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		pnlDB.setBorder(new CompoundBorder(border, margin));

		pnlDB.add(lblPassword);
		pnlDB.add(password);

		pnlDB.add(lblUserName);
		pnlDB.add(userName);

		pnlDB.add(lblDBAddress);
		pnlDB.add(dbAddress);

		pnlDB.add(lblTable);
		pnlDB.add(table);
		pnlDB.add(btnLoadDBSettings);
		pnlDB.add(btnSaveDBSettings);
	}

	/**
	 * Creates the panel holding information concerning the artificial network.
	 */
	public void createAnnPanel() {
		pnlANN.setLayout(new GridLayout(7, 2, 5, 5));
		pnlANN.setBorder(new TitledBorder("Neural network"));

		Border border = pnlANN.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		pnlANN.setBorder(new CompoundBorder(border, margin));

		pnlANN.add(lblIterations);
		pnlANN.add(iterations);

		pnlANN.add(lblLearningRate);
		pnlANN.add(learningRate);

		pnlANN.add(lblMomentum);
		pnlANN.add(momentum);

		pnlANN.add(lblDatasetName);
		pnlANN.add(txtDatasetName);

		pnlANN.add(comboBoxOpenNetwork);
		pnlANN.add(txtNeuralNetWorkPath);

		pnlANN.add(lblFinalNNName);
		pnlANN.add(txtFinalNNName);
		pnlANN.add(btnSaveANNsettings);
		pnlANN.add(btnLoadANNSettings);
	}

	public void createLeaguePanel() {
		pnlLeague.setLayout(new GridLayout(3, 2, 5, 5));
		pnlLeague.setBorder(new TitledBorder("League settings"));

		Border border = pnlLeague.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		pnlLeague.setBorder(new CompoundBorder(border, margin));

		pnlLeague.add(lblLeagueName);
		pnlLeague.add(txtLeagueName);

		pnlLeague.add(lblLeagueAPIid);
		pnlLeague.add(txtLeagueAPIid);
		pnlLeague.add(comboBoxOpenLeague);
		pnlLeague.add(btnSaveLeaguesettings);
	}

	public void createButtonPanel() {
		pnlButtons.setLayout(new GridLayout(4, 2, 5, 5));
		pnlButtons.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlButtons.add(btnSaveAll);
		pnlButtons.add(btnLoadAll);
		pnlButtons.add(comboBoxOpenAll);
	}

	/**
	 * Lists the files in a folder and displays them in combo boxes.
	 */
	public void fixComboBox() {
		File folder = new File("SavedFiles/All");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenAll.addItem(listOfFiles[i].getName());
			}
		}

		folder = new File("SavedFiles/nnet");
		listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenNetwork.addItem(listOfFiles[i].getName());
			}
		}

		folder = new File("SavedFiles/league");
		listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenLeague.addItem(listOfFiles[i].getName());
			}
		}
	}

	/**
	 * Creates a string with the information that is to be saved concerning the
	 * database.
	 * 
	 * @return String The string containing the information that is to be saved.
	 */
	public String getStringToSaveDB() {
		String str = password.getText() + "," + userName.getText() + "," + dbAddress.getText() + "," + table.getText();
		return str;
	}

	/**
	 * Creates a string with the information that is to be saved concerning the
	 * artificial network.
	 * 
	 * @return String The string containing the information that is to be saved.
	 */
	public String getStringToSaveANN() {
		String str = iterations.getText() + "," + learningRate.getText() + "," + momentum.getText() + ","
				+ txtDatasetName.getText() + "," + txtNeuralNetWorkPath.getText() + "," + txtFinalNNName.getText();
		return str;
	}

	/**
	 * Creates a string with the information that is to be saved concerning the
	 * league.
	 * 
	 * @return String The string containing the information that is to be saved.
	 * 
	 */
	public String getStringToSaveLeague() {
		String str = txtLeagueName.getText() + "," + txtLeagueAPIid.getText();
		return str;
	}

	/**
	 * Creates a string with the information that is to be saved concerning all the
	 * information in the GUI.
	 * 
	 * @return String The string containing the information that is to be saved.
	 * 
	 */
	public String getStringToSaveAll() {
		return getStringToSaveDB() + "," + getStringToSaveANN() + "," + getStringToSaveLeague();
	}

	public void setDBSettings(String[] array) {
		password.setText(array[0]);
		userName.setText(array[1]);
		dbAddress.setText(array[2]);
		table.setText(array[3]);
	}

	public void setANNSettings(String[] array) {
		iterations.setText(array[0]);
		learningRate.setText(array[1]);
		momentum.setText(array[2]);
		txtDatasetName.setText(array[3]);
		txtNeuralNetWorkPath.setText(array[4]);
		txtFinalNNName.setText(array[5]);
	}

	public void setLeagueSettings(String[] array) {
		txtLeagueName.setText(array[0]);
		txtLeagueAPIid.setText(array[1]);

	}

	public void setNeuralNetworkPathName(String pathName) {
		txtNeuralNetWorkPath.setText(pathName);
	}

	public void setAllSettings(String[] array) {
		setDBSettings(Arrays.copyOfRange(array, 0, 4));
		setANNSettings(Arrays.copyOfRange(array, 4, 10));
		setLeagueSettings(Arrays.copyOfRange(array, 10, 13));
	}

	/**
	 * Adds text in the console in the GUI.
	 * 
	 * @param str
	 *            The text that is to be added to the console in the GUI.
	 */
	public void addToTextConsole(String str) {
		try {
			doc.insertString(doc.getLength(), "\n" + str, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void disableButtons() {
		btnCalc.setBackground(Color.RED);
		btnCalc.setEnabled(false);
	}

	public void enableButtons() {
		btnCalc.setBackground(Color.GREEN);
		btnCalc.setEnabled(true);
	}

	public void showProgress(int progress) {
		progressBar.setValue(progress);
		progressBar.setString((double) progress / 10 + " %");
	}

	/**
	 * Handles the input from the user when a button is pushed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSaveDBSettings) {
			controller.saveSettings("SaveDB", getStringToSaveDB());

		} else if (e.getSource() == btnLoadDBSettings) { // Load Database settings
			controller.loadSettings("Database");
		} else if (e.getSource() == btnSaveANNsettings) {
			controller.saveSettings("SaveANN", getStringToSaveANN());
		} else if (e.getSource() == btnLoadANNSettings) {
			controller.loadSettings("ANN");
		} else if (e.getSource() == btnLoadNetwork) {
			controller.loadSettings("Network");
		} else if (e.getSource() == btnLoadLeagueSettings) {
			controller.loadSettings("LoadLeague");
		} else if (e.getSource() == btnSaveLeaguesettings) {
			controller.saveSettings("SaveLeague", getStringToSaveLeague());
		} else if (e.getSource() == btnSaveAll) {
			controller.saveSettings("SaveAll",
					getStringToSaveDB() + "," + getStringToSaveANN() + "," + getStringToSaveLeague());
		} else if (e.getSource() == btnLoadAll) {
			controller.loadSettings("LoadAll");
		} else if (e.getSource() == comboBoxOpenAll) {
			controller.loadFromComboBox("SavedFiles/All/" + (String) comboBoxOpenAll.getSelectedItem(), "all");
		} else if (e.getSource() == comboBoxOpenNetwork) {
			controller.loadFromComboBox("SavedFiles/nnet/" + (String) comboBoxOpenNetwork.getSelectedItem(), "nnet");
		} else if (e.getSource() == comboBoxOpenLeague) {
			controller.loadFromComboBox("SavedFiles/league/" + (String) comboBoxOpenLeague.getSelectedItem(), "league");
		} 

		else if (e.getSource() == btnCalc) {
			controller.disableButtons();
			controller.setDataBaseSettings(dbAddress.getText(), userName.getText(), password.getText(), "250",
					table.getText());
			controller.calculate(iterations.getText(), learningRate.getText(), momentum.getText(),
					txtNeuralNetWorkPath.getText(), txtDatasetName.getText(), txtFinalNNName.getText(),
					txtLeagueName.getText(), txtLeagueAPIid.getText(), table.getText());
		}
	}

}
