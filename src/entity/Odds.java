package entity;
import java.util.HashMap;

public class Odds {
	private HashMap<String, Double> oddsHome = new HashMap<String, Double>();
	private HashMap<String, Double> oddsAway = new HashMap<String, Double>();
	private HashMap<String, Double> oddsDraw = new HashMap<String, Double>();
	private String bookie;
	private double odds;
	
	public Odds(String bookie, double odds) {
		this.bookie = bookie;
		this.odds = odds;
	}
	
	public void setOddsHome(HashMap<String, Double> oddsHome) {
		oddsHome.put(bookie, odds);
	}

	public void setOddsAway(HashMap<String, Double> oddsAway) {
		oddsAway.put(bookie, odds);
	}

	public void setOddsDraw(HashMap<String, Double> oddsDraw) {
		oddsDraw.put(bookie, odds);
	}

	public HashMap<String, Double> getOddsHome() {
		return oddsHome;
	}

	public HashMap<String, Double> getOddsAway() {
		return oddsAway;
	}

	public HashMap<String, Double> getOddsDraw() {
		return oddsDraw;
	}

}