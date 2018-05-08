package oldUnused;

import boundary.ConnectDatabase;
import control.LeagueCreator;
import gui.PrintListener;

public class TestAll extends Thread implements PrintListener{

	
	public void run() {
		int[] plApiId = { 113, 114, 4, 301, 341, 354, 398, 426, 445 };
		LeagueCreator ligaSkapare = null;
		ConnectDatabase connection = new ConnectDatabase();
		connection.connect();
		
		ligaSkapare = new LeagueCreator("PL", plApiId, this, "test");
		
		
	}
	
	
	
	@Override
	public void updateText(String text) {
		// TODO Auto-generated method stub
		
	}

}
