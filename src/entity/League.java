package entity;

public class League {
	private String name;
	private int id;
	private int currentYear;
	private Season[] seasons;

	public League() {

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

	public Season[] getSeasons() {
		return seasons;
	}

	public void setSeasons(Season[] seasons) {
		this.seasons = seasons;
	}
}
