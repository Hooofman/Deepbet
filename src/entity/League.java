package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class for creating a League object. 
 * @author oskarem
 *
 */
public class League {
	private String name;
	private int id;
	private int currentYear;
	private ArrayList<Season> seasons;
	private ArrayList<Team> teams;
	
	/**
	 * Constructs a League object and creates arrays for seasons and teams. 
	 * @param name Name of league 
	 */
	public League(String name) {
		this.name = name;
		this.seasons = new ArrayList<Season>();
		this.teams = new ArrayList<Team>();
	}
	
	/**
	 * Returns the array of Season objects
	 * @return array of Season objects
	 */
	public ArrayList<Season> getSeasons() {
		return seasons;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	
	public void addTeam(Team team) {
			this.teams.add(team);
	}
	
	/**
	 * Remove a team from the league
	 * @param team a team-object
	 */
	public void removeTeam(Team team) {
		if(teams.contains(team)) {
			this.teams.remove(team);
		}
	}
	
	/**
	 * Adds a season to the league
	 * @param season a season object 
	 */
	public void addSeason(Season season) {
		seasons.add(season);
	}
	
	/**
	 * Clears the statistics for all the teams at the start of a new season
	 */
	public void resetTeamsForNewSeason() {
		for(Team team : teams) {
			team.resetForNewSeason();
		}
	}
	
	/**
	 * Removes the relegated teams from the league at the end of a season
	 */
	public void removeLast3Teams() {
		ArrayList<Team> list = new ArrayList<Team>();
		for (Team team : teams) {
			// Puts the 3 last placed teams in a list 
			if (team.getLastTablePosition() > 17) {
				list.add(team);
				System.out.println(team.getName() + " kommer raderas");
			}
		}
		// Removes the list with the relegated teams
		this.teams.removeAll(list);
	}
	
	/**
	 * Returns an array containing all team names in the league
	 * @return array of team names 
	 */
	public ArrayList<String> getAllTeams(){
		ArrayList<String> temp = new ArrayList<String>();
		for(Team team: teams) {
			temp.add(team.getName());
		}
		return temp;
	}
	
	/**
	 * Returns an array of all Team object in the league
	 * @return array of Team objects
	 */
	public ArrayList<Team> getAllTeamObjects(){
		return this.teams;
	}
	
	/**
	 * Checks if a team exists in the league 
	 * @param name team name
	 * @return the Team object if it exists. Otherwise null
	 */
	public Team getTeam(String name) {
		for (Team team : teams) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}
}