package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame implements ActionListener {
	private Controller controller;
	private JPanel pnlMain;
	private JTextArea consolText = new JTextArea();
	private JPanel pnlTextArea = new JPanel();

	// Database input
	private JPanel pnlWest = new JPanel(); // Holds the entire western content
	private JPanel pnlDB = new JPanel();
	private JTextField password = new JTextField("Deepbet123");
	private JTextField userName = new JTextField("deepbet");
	private JTextField dbAddress = new JTextField("jdbc:mysql://deepbet.ddns.net:3306/deepbet");
	private JTextField table = new JTextField("games");
	private JLabel lblDB = new JLabel("Database settings");
	private JLabel lblPassword = new JLabel("Password");
	private JLabel lblUserName = new JLabel("User name");
	private JLabel lblDBAddress = new JLabel("Database address");
	private JLabel lblTable = new JLabel("Table to save in");
	private JButton btnSaveDBSettings = new JButton("Save database settings");
	private JButton btnLoadDBSettings = new JButton("Load database settings");

	// ANN settings
	private JPanel pnlANN;
	private JLabel lblANN = new JLabel("Network settings");

	private JLabel lblIterations = new JLabel("Number of iterations");
	private JLabel lblLearningRate = new JLabel("Learing rate");
	private JLabel lblMomentum = new JLabel("Momentum");
	private JButton btnLoadNetwork = new JButton("Load network template");
	private JButton btnSaveANNsettings = new JButton("Save network settings");
	private JButton btnLoadANNSettings = new JButton("Load network settings");

	private JTextField iterations = new JTextField();
	private JTextField learningRate = new JTextField();
	private JTextField momentum = new JTextField();
	private JTextField txtNeuralNetWorkPath = new JTextField("search path for neural network");

	private JButton btnCalc = new JButton("Start calculation");

	private JPanel pnlBottom = new JPanel(); // Holds calc Button
	private JPanel pnlUpper = new JPanel(); // Upper Panel
	private JPanel pnlButtons = new JPanel(); // Holds buttons

	public GUI() {
		this.setPreferredSize(new Dimension(1500, 1000));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		createAnnPanel();
		createPnlDB();

		createBottomPanel();
		createConsolePanel();
		createPnlUpper();

		createMainPnl();
		addActionListeners();
		this.add(pnlMain);

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
		pnlTextArea.add(consolText, BorderLayout.CENTER);
	}

	public void createMainPnl() {
		pnlMain = new JPanel();
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
		pnlANN = new JPanel();
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
		consolText.append(str + "\n");
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
