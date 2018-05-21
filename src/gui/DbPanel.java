package gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JTextField password;
	protected JTextField userName;
	protected JTextField dbAddress;
	protected JTextField table;
	protected JLabel lblPassword;
	protected JLabel lblUserName;
	protected JLabel lblDBAddress;
	protected JLabel lblTable;
	protected JButton btnSaveDBSettings;
	protected JButton btnLoadDBSettings;
	
	public DbPanel(ActionListener listener) {
		init();
		btnSaveDBSettings.addActionListener(listener);
		btnLoadDBSettings.addActionListener(listener);
	}
	
	public void init() {
		password = new JTextField("Deepbet123");
		userName = new JTextField("deepbet");
		dbAddress = new JTextField("");
		table = new JTextField("games");
		
		lblPassword = new JLabel("Password");
		lblUserName = new JLabel("User name");
		lblDBAddress = new JLabel("Database address");
		lblTable = new JLabel("Table to save in");
		
		btnSaveDBSettings = new JButton("Save database settings");
		btnLoadDBSettings = new JButton("Load database settings");
		
		add(password);
		add(userName);
		add(dbAddress);
		add(table);
		add(lblPassword);
		add(lblUserName);
		add(lblDBAddress);
		add(lblTable);
		add(btnSaveDBSettings);
		add(btnLoadDBSettings);
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
	
	public void setDBSettings(String[] array) {
		password.setText(array[0]);
		userName.setText(array[1]);
		dbAddress.setText(array[2]);
		table.setText(array[3]);
	}
}
