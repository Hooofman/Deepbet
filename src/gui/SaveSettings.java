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

import boundary.WriteToFile;
import control.Controller;

/**
 * Graphical user interface that handles the event of a user who wants to save
 * specific settings from the main GUI.
 * 
 * @author Sven Lindqvist
 *
 */
public class SaveSettings extends JPanel {
	private GUI gui;
	private String indicator;
	private JFrame frame;
	private JButton btnAdd;
	private JButton btnChoosePath;
	private JLabel lblPathName;
	private String dataToSave;
	private Controller controller;

	/**
	 * Creates the user interface.
	 * 
	 * @param controller
	 *            The controller
	 * @param indicator
	 *            Indicates if it's database, AI or league information that is to be
	 *            saved.
	 * @param dataToSave
	 *            The information that is to be saved.
	 */
	public SaveSettings(Controller controller, String indicator, String dataToSave) {
		this.controller = controller;
		this.indicator = indicator;
		this.dataToSave = dataToSave;

		btnAdd = new JButton("Välj");
		btnChoosePath = new JButton("...");
		lblPathName = new JLabel("---");
		btnChoosePath.setPreferredSize(new Dimension(20, 20));

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
				frame.add(SaveSettings.this);
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

	/**
	 * Inner class that handles the input from the user during a saving session.
	 * 
	 * @author Sven Lindqvist
	 *
	 */
	private class Listener implements ActionListener {
		/**
		 * Depending on the indicator the information is saved with an indicator that
		 * insures that when the information is read, the information is displayed in
		 * the correct location.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAdd && indicator.equals("SaveDB")) {
				WriteToFile.write(dataToSave, lblPathName.getText());
				controller.addToConsoleText("Database settings is saved to " + lblPathName.getText());
				frame.dispose();

			} else if (e.getSource() == btnAdd && indicator.equals("SaveANN")) {
				WriteToFile.write(dataToSave, lblPathName.getText());
				controller.addToConsoleText("Neural network settings is saved to " + lblPathName.getText());
				frame.dispose();
			} else if (e.getSource() == btnAdd && indicator.equals("SaveLeague")) {
				WriteToFile.write(dataToSave, lblPathName.getText());
				controller.addToConsoleText("League settings is saved to " + lblPathName.getText());
				frame.dispose();
			} else if (e.getSource() == btnAdd && indicator.equals("SaveAll")) {
				WriteToFile.write(dataToSave, lblPathName.getText());
				controller.addToConsoleText("All settings is saved to " + lblPathName.getText());
				frame.dispose();
			} else if (e.getSource() == btnChoosePath) {

				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(SaveSettings.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					lblPathName.setText(file.getAbsolutePath());
					btnAdd.setEnabled(true);
				}
			}

		}

	}
}
