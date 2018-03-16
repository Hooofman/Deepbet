package entity;

public class League {
	private String name;
	private int id;
	private int currentYear;
	private Seasons[] seasons;

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

	public Seasons[] getSeasons() {
		return seasons;
	}

	public void setSeasons(Seasons[] seasons) {
		this.seasons = seasons;
	}
}
