package entity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for creating the league table 
 * @author 
 *
 */
public class LeagueTable {
	private ArrayList<Team> teams;
	
	/**
	 * Constructs the league table containing of Team objects in an ArrayList
	 */
	public LeagueTable() {
		teams = new ArrayList<Team>();
	}
	
	/**
	 * Updates the table after each fixture
	 * @param teams array of all teams
	 */
	public void updateTable(ArrayList<Team> teams) {
		this.teams = teams;
		// Sorts the teams based on number of points
		Collections.sort(teams);
		
		for (int i = 0; i < teams.size(); i++) {
			// Sets the new table position based on how they are sorted
			teams.get(i).setTablePosition(i + 1);
		}
	}
	
	/**
	 * Returns the league table
	 * @return array of Team objects
	 */
	public ArrayList<Team> getLeagueTable() {
		return teams;
	}
}
