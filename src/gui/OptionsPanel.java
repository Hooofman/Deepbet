package gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel {

	protected JButton btnSaveAll;
	protected JButton btnLoadAll;
	protected JComboBox comboBoxOpenAll;
	
	public OptionsPanel(ActionListener listener) {
		init();
	}
	
	public void init() {
		btnSaveAll = new JButton("Save All settings");
		btnLoadAll = new JButton("Load All settings");
		comboBoxOpenAll = new JComboBox();
		add(btnSaveAll);
		add(btnLoadAll);
		add(comboBoxOpenAll);
	}
}
