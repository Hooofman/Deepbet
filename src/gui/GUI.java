package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class GUI extends JFrame implements ActionListener {
	private Controller controller;
	private JPanel pnlMain;
	private JTextPane consolText;
	private JPanel pnlTextArea;
	private JScrollPane scrollBar;

	// Database input
	private JPanel pnlWest; // Holds the entire western content
	private JPanel pnlDB;
	private JTextField password;
	private JTextField userName;
	private JTextField dbAddress;
	private JTextField table;
	private JLabel lblDB;
	private JLabel lblPassword;
	private JLabel lblUserName;
	private JLabel lblDBAddress;
	private JLabel lblTable;
	private JButton btnSaveDBSettings;
	private JButton btnLoadDBSettings;

	// ANN settings
	private JPanel pnlANN;
	private JLabel lblANN;

	private JLabel lblIterations;
	private JLabel lblLearningRate;
	private JLabel lblMomentum;
	private JButton btnLoadNetwork;
	private JButton btnSaveANNsettings;
	private JButton btnLoadANNSettings;

	private JTextField iterations;
	private JTextField learningRate;
	private JTextField momentum;
	private JTextField txtNeuralNetWorkPath;

	private JButton btnCalc;

	private JPanel pnlBottom;
	private JPanel pnlUpper;
	private JPanel pnlButtons;

	private StyledDocument doc;

	public GUI() {

		pnlANN = new JPanel();
		pnlMain = new JPanel();
		pnlTextArea = new JPanel();
		pnlWest = new JPanel();
		pnlDB = new JPanel();
		password = new JTextField("Deepbet123");
		userName = new JTextField("deepbet");
		dbAddress = new JTextField("jdbc:mysql://deepbet.ddns.net:3306/deepbet");
		table = new JTextField("games");
		lblDB = new JLabel("Database settings");
		lblPassword = new JLabel("Password");
		lblUserName = new JLabel("User name");
		lblDBAddress = new JLabel("Database address");
		lblTable = new JLabel("Table to save in");
		btnSaveDBSettings = new JButton("Save database settings");
		btnLoadDBSettings = new JButton("Load database settings");
		lblANN = new JLabel("Network settings");
		consolText = new JTextPane();

		lblIterations = new JLabel("Number of iterations");
		lblLearningRate = new JLabel("Learing rate");
		lblMomentum = new JLabel("Momentum");
		btnLoadNetwork = new JButton("Load network template");
		btnSaveANNsettings = new JButton("Save network settings");
		btnLoadANNSettings = new JButton("Load network settings");

		iterations = new JTextField("20");
		learningRate = new JTextField("0.7");
		momentum = new JTextField("0.2");
		txtNeuralNetWorkPath = new JTextField(
				"C:\\Users\\Sven-\\OneDrive\\Dokument\\GitHub\\DeepBet\\Deepbet\\bias2.nnet");

		btnCalc = new JButton("Start calculation");

		pnlBottom = new JPanel(); // Holds calc Button
		pnlUpper = new JPanel(); // Upper Panel
		pnlButtons = new JPanel(); // Holds buttons

		doc = consolText.getStyledDocument();

		createAnnPanel();
		createPnlDB();
		createBottomPanel();
		createConsolePanel();
		createPnlUpper();
		createMainPnl();
		addActionListeners();
		this.add(pnlMain);
		this.setPreferredSize(new Dimension(1500, 1000));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

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
	}

	public void createPnlUpper() {
		pnlUpper.setPreferredSize(new Dimension(1200, 100));
		pnlUpper.add(new JLabel("DeepBet"));
		pnlUpper.setBackground(Color.RED);
	}

	public void createBottomPanel() {
		pnlBottom.setPreferredSize(new Dimension(400, 100));
		pnlBottom.add(btnCalc);
		pnlBottom.setBackground(Color.PINK);
	}

	public void createConsolePanel() {
		pnlTextArea.setPreferredSize(new Dimension(200, 400));
		pnlTextArea.setLayout(new BorderLayout());
		// pnlTextArea.setBackground(Color.LIGHT_GRAY);

		consolText.setPreferredSize(new Dimension(200, 400));

		scrollBar = new JScrollPane(consolText);
		scrollBar.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollBar.setAutoscrolls(true);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlTextArea.add(scrollBar);

	}

	public void createMainPnl() {
		pnlMain.setLayout(new BorderLayout());

		pnlMain.add(pnlWest, BorderLayout.WEST);

		pnlMain.add(pnlTextArea, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlMain.add(pnlUpper, BorderLayout.NORTH);
	}

	public void createPnlDB() {

		pnlDB.setLayout(new GridLayout(5, 2));
		pnlDB.setBackground(Color.GRAY);
		pnlDB.setPreferredSize(new Dimension(100, 0));

		// lblPassword.setPreferredSize(new Dimension(100, 20));
		// password.setPreferredSize(new Dimension(100, 20));
		// lblUserName.setPreferredSize(new Dimension(100, 20));
		// userName.setPreferredSize(new Dimension(100, 20));
		// lblDBAddress.setPreferredSize(new Dimension(100, 20));
		// dbAddress.setPreferredSize(new Dimension(100, 20));
		// lblTable.setPreferredSize(new Dimension(100, 20));
		// table.setPreferredSize(new Dimension(100, 20));

		pnlDB.add(lblDB);
		pnlDB.add(new JLabel());
		pnlDB.add(lblPassword);
		pnlDB.add(password);

		pnlDB.add(lblUserName);
		pnlDB.add(userName);

		pnlDB.add(lblDBAddress);
		pnlDB.add(dbAddress);

		pnlDB.add(lblTable);
		pnlDB.add(table);

		pnlWest.setLayout(new GridLayout(4, 1));

		pnlButtons.setLayout(new GridLayout(5, 1));
		pnlButtons.add(btnLoadNetwork);
		pnlButtons.add(txtNeuralNetWorkPath);

		pnlButtons.add(btnLoadDBSettings);
		pnlButtons.add(btnSaveDBSettings);
		pnlButtons.add(btnSaveANNsettings);
		pnlButtons.add(btnLoadANNSettings);

		pnlWest.add(pnlDB);
		pnlWest.add(pnlANN);
		pnlWest.add(pnlButtons);
	}

	public void createAnnPanel() {
		pnlANN.setLayout(new GridLayout(4, 2));
		pnlANN.setBackground(Color.GRAY);
		pnlANN.setPreferredSize(new Dimension(300, 50));

		lblIterations.setPreferredSize(new Dimension(100, 20));
		iterations.setPreferredSize(new Dimension(100, 20));
		lblLearningRate.setPreferredSize(new Dimension(100, 20));
		learningRate.setPreferredSize(new Dimension(100, 20));
		lblMomentum.setPreferredSize(new Dimension(100, 20));
		momentum.setPreferredSize(new Dimension(100, 20));

		pnlANN.add(lblANN);
		pnlANN.add(new JLabel()); // FULLÖSNING
		pnlANN.add(lblIterations);
		pnlANN.add(iterations);

		pnlANN.add(lblLearningRate);
		pnlANN.add(learningRate);

		pnlANN.add(lblMomentum);
		pnlANN.add(momentum);
	}

	public String getStringToSaveDB() {
		String str = password.getText() + "," + userName.getText() + "," + dbAddress.getText() + "," + table.getText();
		return str;
	}

	public String getStringToSaveANN() {
		String str = iterations.getText() + "," + learningRate.getText() + "," + momentum.getText();
		return str;
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
	}

	public void setNeuralNetworkPathName(String pathName) {
		txtNeuralNetWorkPath.setText(pathName);
	}

	public void addToTextConsole(String str) {
		try {
			doc.insertString(doc.getLength(), "\n" + str, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

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
		} else if (e.getSource() == btnCalc) {
			controller.calculate(iterations.getText(), learningRate.getText(), momentum.getText(),
					txtNeuralNetWorkPath.getText());
		}
	}
}
