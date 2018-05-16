package gui;

import javax.swing.SwingUtilities;

public class StartGUI {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
				Controller controller = new Controller(gui);
				
				Runtime.getRuntime().addShutdownHook(
					new Thread() { 
						public void run() {  
							WriteToFile.write(gui.getStringToSaveAll(), System.getProperty("user.dir")+"/SavedFiles/autosave/auto.txt");
						}        
					}); 
				controller.loadAutoSaved("/SavedFiles/Autosave/auto.txt");
			}
		});

	}
}
