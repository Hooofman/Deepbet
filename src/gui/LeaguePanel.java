package gui;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LeaguePanel extends JPanel {
	protected JLabel lblLeagueName;
	protected JLabel lblLeagueAPIid;
	protected JTextField txtLeagueName;
	protected JTextField txtLeagueAPIid;
	protected JButton btnSaveLeagueSettings;
	protected JButton btnLoadLeagueSettings;
	protected JComboBox comboBoxOpenLeague;
	
	public LeaguePanel(ActionListener listener) {
		init();
		comboBoxOpenLeague.addActionListener(listener);
		btnSaveLeagueSettings.addActionListener(listener);
		btnLoadLeagueSettings.addActionListener(listener);
	}
	
	public void init() {
		lblLeagueName = new JLabel("League name");
		lblLeagueAPIid = new JLabel("Season-IDs from API, separate with ,");
		txtLeagueName = new JTextField();
		txtLeagueAPIid = new JTextField();
		btnSaveLeagueSettings = new JButton("Save League settings");
		btnLoadLeagueSettings = new JButton("Load League settings");
		comboBoxOpenLeague = new JComboBox();
		
		add(lblLeagueName);
		add(lblLeagueAPIid);
		add(txtLeagueName);
		add(txtLeagueAPIid);
		add(btnSaveLeagueSettings);
		add(btnLoadLeagueSettings);
		add(comboBoxOpenLeague);
		
		File folder = new File("SavedFiles/league");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenLeague.addItem(listOfFiles[i].getName());
			}
		}
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
	
	public void setLeagueSettings(String[] array) {
		txtLeagueName.setText(array[0]);
		txtLeagueAPIid.setText(array[1]);
	}
}
