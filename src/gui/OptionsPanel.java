package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OptionsPanel extends JPanel {

	protected JButton btnSaveAll;
	protected JButton btnLoadAll;
	protected JLabel lblLoadFromCombo;
	protected JComboBox comboBoxOpenAll;
	
	public OptionsPanel(ActionListener listener) {
		init();
		comboBoxOpenAll.addActionListener(listener);
		btnSaveAll.addActionListener(listener);
		btnLoadAll.addActionListener(listener);
	}
	
	public void init() {
		setLayout(new GridLayout(15,0,5,5));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		btnSaveAll = new JButton("Save All settings");
		btnLoadAll = new JButton("Load All settings");
		comboBoxOpenAll = new JComboBox();
		lblLoadFromCombo = new JLabel("Choose saved presets");
		add(lblLoadFromCombo);
		add(comboBoxOpenAll);
		add(btnSaveAll);
		add(btnLoadAll);
		File folder = new File("SavedFiles/All");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comboBoxOpenAll.addItem(listOfFiles[i].getName());
			}
		}
	}
}
