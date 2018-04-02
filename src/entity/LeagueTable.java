package entity;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTable {
	private ArrayList<Team> teams;

	public LeagueTable() {
		teams = new ArrayList<Team>();

	}

	public void updateTable(ArrayList<Team> teams) {
		this.teams = teams;
		Collections.sort(teams);
		for (int i = teams.size()-1; i >= 0; i--) {
			teams.get(i).setTablePosition(i + 1);

		}
		System.out.println(teams);
	}

	public ArrayList<Team> getLeagueTable() {
		return teams;
	}
}
