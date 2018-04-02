package entity;

import java.util.ArrayList;

public class League {
	private String name;
	private int id;
	private int currentYear;
	private ArrayList<Season> seasons;
	private ArrayList<Team> teams;

	public League(String name) {
		this.name = name;
		seasons = new ArrayList<Season>();
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
		if(!teams.contains(team)) {
			teams.add(team);
		}
	}
	
	public void removeTeam(Team team) {
		if(teams.contains(team)) {
			teams.remove(team);
		}
	}
	
	public void addSeason(Season season) {
		seasons.add(season);
	}
	
	public void removeLast3Teams() {
		
	}

	public ArrayList<String> getAllTeams(){
		ArrayList<String> temp = new ArrayList<String>();
		for(Team team: teams) {
			temp.add(team.getName());
		}
		return temp;
	}
}