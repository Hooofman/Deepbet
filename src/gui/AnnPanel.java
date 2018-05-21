package gui;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AnnPanel extends JPanel {
	
	protected JLabel lblIterations;
	protected JLabel lblLearningRate;
	protected JLabel lblMomentum;
	protected JLabel lblNeuralNetworkPath;
	protected JLabel lblDatasetName;
	protected JLabel lblFinalNNName;
	
	protected JButton btnLoadNetwork;
	protected JButton btnSaveANNsettings;
	protected JButton btnLoadANNSettings; 
	
	protected JTextField iterations;
	protected JTextField learningRate;
	protected JTextField momentum;
	protected JTextField txtNeuralNetWorkPath;
	protected JTextField txtDatasetName;
	protected JTextField txtFinalNNName;
	
	protected JComboBox comboBoxOpenNetwork;
	
	public AnnPanel(ActionListener listener) {
		init();
		comboBoxOpenNetwork.addActionListener(listener);
		btnLoadNetwork.addActionListener(listener);
		btnSaveANNsettings.addActionListener(listener);
		btnLoadANNSettings.addActionListener(listener);
	}
	
	public void init() {
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
		txtDatasetName = new JTextField("");
		txtFinalNNName = new JTextField("");
		
		comboBoxOpenNetwork = new JComboBox();
		
		add(lblIterations);
		add(lblLearningRate);
		add(lblMomentum);
		add(lblNeuralNetworkPath);
		add(lblFinalNNName);
		add(txtDatasetName);
		add(btnLoadNetwork);
		add(btnLoadANNSettings);
		add(btnSaveANNsettings);
		add(iterations);
		add(learningRate);
		add(momentum);
		add(lblNeuralNetworkPath);
		add(txtDatasetName);
		add(txtFinalNNName);
		add(comboBoxOpenNetwork);
		add(txtNeuralNetWorkPath);
		
		
		File folder = new File("SavedFiles/nnet/template");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenNetwork.addItem(listOfFiles[i].getName());
			}
		}
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
	
	public void setANNSettings(String[] array) {
		iterations.setText(array[0]);
		learningRate.setText(array[1]);
		momentum.setText(array[2]);
		txtDatasetName.setText(array[3]);
		txtNeuralNetWorkPath.setText(array[4]);
		txtFinalNNName.setText(array[5]);
	}
	
	
	
}
