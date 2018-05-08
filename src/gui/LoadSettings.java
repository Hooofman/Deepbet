package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadSettings extends JPanel {
	private GUI gui;
	private String indicator;
	private JFrame frame;
	private JButton btnAdd;
	private JButton btnChoosePath;
	private JLabel lblPathName;
	private Controller controller;

	public LoadSettings(Controller cont, String indicator) {
		this.controller = cont;
		this.indicator = indicator;

		btnAdd = new JButton("Välj");
		btnChoosePath = new JButton("...");
		lblPathName = new JLabel("---");
		btnChoosePath.setPreferredSize(new Dimension(40, 20));

		setLayout(new BorderLayout(5, 5));
		setPreferredSize(new Dimension(300, 70));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(lblPathName, BorderLayout.CENTER);
		add(btnChoosePath, BorderLayout.WEST);
		add(btnAdd, BorderLayout.SOUTH);
		btnAdd.setEnabled(false);

		addListeners();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new JFrame("Choose path");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(LoadSettings.this);
				frame.pack();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
			}
		});
	}

	private void addListeners() {
		Listener l = new Listener();

		btnAdd.addActionListener(l);
		btnChoosePath.addActionListener(l);
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAdd && indicator.equals("Database")) { // Database
				String str = ReadFromFile.readFromFile(lblPathName.getText());
				controller.setDBSettings(str);
				controller.addToConsoleText("Database settings is loaded from " + lblPathName.getText());
				frame.dispose();
			} else if (e.getSource() == btnAdd && indicator.equals("ANN")) {
				System.out.println(lblPathName.getText() + "Ska laddas");
				String str = ReadFromFile.readFromFile(lblPathName.getText());
				controller.setANNSettings(str);
				controller.addToConsoleText("Neural network settings is loaded from " + lblPathName.getText());
				frame.dispose();

			} else if (e.getSource() == btnAdd && indicator.equals("LoadLeague")) {
				System.out.println(lblPathName.getText() + "Ska laddas");
				String str = ReadFromFile.readFromFile(lblPathName.getText());
				controller.setLeagueSettings(str);
				controller.addToConsoleText("League settings is loaded from " + lblPathName.getText());
				frame.dispose();

			} else if (e.getSource() == btnAdd && indicator.equals("LoadAll")) {
				System.out.println(lblPathName.getText() + "Ska laddas");
				String str = ReadFromFile.readFromFile(lblPathName.getText());
				controller.setAllSettings(str);
				controller.addToConsoleText("All settings is loaded from " + lblPathName.getText());
				frame.dispose();

			} else if (e.getSource() == btnChoosePath) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"/SavedFiles"));
				if (indicator.equals("Network")) {
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Only NN-files", "nnet");
					fileChooser.addChoosableFileFilter(filter);
					// fileChooser.setFileFilter(filter);
				}
				// fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(LoadSettings.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					lblPathName.setText(file.getAbsolutePath());
					btnAdd.setEnabled(true);
				}
			} else if (e.getSource() == btnAdd && indicator.equals("Network")) {
				controller.setNeuralNetworkPath(lblPathName.getText());
				controller.addToConsoleText("Neural network template is loaded from " + lblPathName.getText());
				frame.dispose();
			}

		}

	}
}
