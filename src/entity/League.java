package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class League {
	private String name;
	private int id;
	private int currentYear;
	private ArrayList<Season> seasons;
	private ArrayList<Team> teams;

	public League(String name) {
		this.name = name;
		this.seasons = new ArrayList<Season>();
		this.teams = new ArrayList<Team>();
	}
	
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
	
	public void removeTeam(Team team) {
		if(teams.contains(team)) {
			this.teams.remove(team);
		}
	}
	
	public void addSeason(Season season) {
		seasons.add(season);
	}
	
	public void resetTeamsForNewSeason() {
		for(Team team : teams) {
			team.resetForNewSeason();
		}
	}
	
	public void removeLast3Teams() {
		ArrayList<Team> list = new ArrayList<Team>();
		for (Team team : teams) {
			if (team.getLastTablePosition() > 17) {
				list.add(team);
				System.out.println(team.getName() + " kommer raderas");
			}
		}
		this.teams.removeAll(list);
	}

	public ArrayList<String> getAllTeams(){
		ArrayList<String> temp = new ArrayList<String>();
		for(Team team: teams) {
			temp.add(team.getName());
		}
		return temp;
	}
	
	public ArrayList<Team> getAllTeamObjects(){
		return this.teams;
	}
	
	public Team getTeam(String name) {
		for (Team team : teams) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}
}