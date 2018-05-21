package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyledDocument;

import control.Controller;

public class GUI extends JFrame implements ActionListener {

	private Controller controller;

	// Main-panel
	private JPanel pnlMain;

	// Center-panel
	private JPanel pnlCenter;
	private JTextPane consolText;
	private JScrollPane scrollBar;
	private StyledDocument doc;

	// Upper Panel
	private JPanel pnlUpper;
	private JLabel lblTitle;
	private static final String LOGOPATH = "images/deepbet_vit.png";

	// West Panel
	private JTabbedPane pnlWest;
	private DbPanel pnlDb;
	private AnnPanel pnlAnn;
	private LeaguePanel pnlLeague;
	private OptionsPanel pnlOptions;

	// Bottom Panel
	private JPanel pnlBottom;
	private JButton btnCalc;
	private JProgressBar progressBar;

	public GUI() {
		init();
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void init() {

		initCenterPanel();
		initWestPanel();
		initUpperPanel();
		initBottomPanel();
		initMainPanel();
		add(pnlMain);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public void initMainPanel() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlMain.add(pnlUpper, BorderLayout.NORTH);
		pnlMain.setVisible(true);
	}

	public void initCenterPanel() {
		pnlCenter = new JPanel();
		consolText = new JTextPane();
		scrollBar = new JScrollPane(consolText);

		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));

		consolText.setEditable(false);

		scrollBar.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollBar.setAutoscrolls(true);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		pnlCenter.add(scrollBar);
		pnlCenter.setVisible(true);

		doc = consolText.getStyledDocument();

		// Scrolls the text-panel automatically when filled
		DefaultCaret caret = (DefaultCaret) consolText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void initUpperPanel() {
		pnlUpper = new JPanel();
		lblTitle = new JLabel();
		lblTitle.setIcon(new ImageIcon(LOGOPATH));
		pnlUpper.add(lblTitle);
		pnlUpper.setBackground(Color.DARK_GRAY);
	}

	public void initWestPanel() {
		pnlDb = new DbPanel(this);
		pnlAnn = new AnnPanel(this);
		pnlLeague = new LeaguePanel(this);
		pnlOptions = new OptionsPanel(this);
		pnlWest = new JTabbedPane();
		pnlWest.setPreferredSize(new Dimension(400, 400));
		pnlWest.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlWest.addTab("Database", pnlDb);
		pnlWest.addTab("Network", pnlAnn);
		pnlWest.addTab("League", pnlLeague);
		pnlWest.addTab("Options", pnlOptions);
		pnlWest.setVisible(true);
	}

	public void initBottomPanel() {
		pnlBottom = new JPanel();
		progressBar = new JProgressBar(0, 1000);
		pnlBottom.setLayout(new GridLayout(1, 2));
		btnCalc = new JButton("Start calculation");
		btnCalc.setFont(btnCalc.getFont().deriveFont(26.0f));
		btnCalc.setBackground(Color.GREEN);
		btnCalc.addActionListener(this);
		progressBar.setForeground(Color.GREEN);
		progressBar.setFont(progressBar.getFont().deriveFont(26.0f));
		pnlBottom.add(btnCalc);
		pnlBottom.setBackground(Color.BLACK);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		pnlBottom.add(progressBar);
	}



	// GUI-LOGIC //

	public void setDbSettings(String[] arr) {
		pnlDb.setDBSettings(arr);
	}

	public void setAnnSettings(String[] arr) {
		pnlAnn.setANNSettings(arr);
	}

	public void setLeagueSettings(String[] arr) {
		pnlLeague.setLeagueSettings(arr);
	}

	public void setAllSettings(String[] arr) {
		setDbSettings(Arrays.copyOfRange(arr, 0, 4));
		setAnnSettings(Arrays.copyOfRange(arr, 4, 10));
		setLeagueSettings(Arrays.copyOfRange(arr, 10, 13));
	}

	public void setNeuralNetworkPath(String pathName) {
		pnlAnn.setNeuralNetworkPathName(pathName);
	}

	/**
	 * Creates a string with the information that is to be saved concerning all the
	 * information in the GUI.
	 * 
	 * @return String The string containing the information that is to be saved.
	 * 
	 */
	public String getStringToSaveAll() {
		return pnlDb.getStringToSaveDB() + "," + pnlAnn.getStringToSaveANN() + "," + pnlLeague.getStringToSaveLeague();
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
		if (e.getSource() == pnlDb.btnSaveDBSettings) {
			controller.saveSettings("SaveDB", pnlDb.getStringToSaveDB());
		} else if (e.getSource() == pnlDb.btnLoadDBSettings) { // Load Database settings
			controller.loadSettings("Database");
		} else if (e.getSource() == pnlAnn.btnSaveANNsettings) {
			controller.saveSettings("SaveANN", pnlAnn.getStringToSaveANN());
		} else if (e.getSource() == pnlAnn.btnLoadANNSettings) {
			controller.loadSettings("ANN");
		} else if (e.getSource() == pnlAnn.btnLoadNetwork) {
			controller.loadSettings("Network");
		} else if (e.getSource() == pnlLeague.btnLoadLeagueSettings) {
			controller.loadSettings("LoadLeague");
		} else if (e.getSource() == pnlLeague.btnSaveLeagueSettings) {
			controller.saveSettings("SaveLeague", pnlLeague.getStringToSaveLeague());
		} else if (e.getSource() == pnlOptions.btnSaveAll) {
			controller.saveSettings("SaveAll",
					pnlDb.getStringToSaveDB() + "," + pnlAnn.getStringToSaveANN() + "," + pnlLeague.getStringToSaveLeague());
		} else if (e.getSource() == pnlOptions.btnLoadAll) {
			controller.loadSettings("LoadAll");
		} else if (e.getSource() == pnlOptions.comboBoxOpenAll) {
			controller.loadFromComboBox("SavedFiles/All/" + (String) pnlOptions.comboBoxOpenAll.getSelectedItem(), "all");
		} else if (e.getSource() == pnlAnn.comboBoxOpenNetwork) {
			controller.loadFromComboBox("SavedFiles/nnet/template/" + (String) pnlAnn.comboBoxOpenNetwork.getSelectedItem(), "nnet");
		} else if (e.getSource() == pnlLeague.comboBoxOpenLeague) {
			controller.loadFromComboBox("SavedFiles/league/" + (String) pnlLeague.comboBoxOpenLeague.getSelectedItem(), "league");
		} 

		else if (e.getSource() == btnCalc) {
			controller.disableButtons();
			controller.setDataBaseSettings(pnlDb.dbAddress.getText(), pnlDb.userName.getText(), pnlDb.password.getText(), "250",
					pnlDb.table.getText());
			controller.calculate(pnlAnn.iterations.getText(), pnlAnn.learningRate.getText(), pnlAnn.momentum.getText(),
					pnlAnn.txtNeuralNetWorkPath.getText(), pnlAnn.txtDatasetName.getText(), pnlAnn.txtFinalNNName.getText(),
					pnlLeague.txtLeagueName.getText(), pnlLeague.txtLeagueAPIid.getText(), pnlDb.table.getText());
		}
	}


}
