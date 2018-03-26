package entity;
import java.util.HashMap;

public class Odds {
	private HashMap<String, Double> oddsHome;
	private HashMap<String, Double> oddsAway;
	private HashMap<String, Double> oddsDraw;

	
	public Odds() {
		oddsHome = new HashMap<String, Double>();
		oddsAway = new HashMap<String, Double>();
		oddsDraw = new HashMap<String, Double>();
	}
	
	public void putOddsHome(String bookie, double odds) {
		oddsHome.put(bookie, odds);
	}
	
	public void putOddsAway(String bookie, double odds) {
		oddsAway.put(bookie, odds);
	}
	
	public void putOddsDraw(String bookie, double odds) {
		oddsDraw.put(bookie, odds);
	}
	
	public double getOddsForBookieHome(String bookie) {
		return oddsHome.get(bookie);
	}
	
	public double getOddsForBookieAway(String bookie) {
		return oddsAway.get(bookie);
	}
	
	public double getOddsForBookieDraw(String bookie) {
		return oddsDraw.get(bookie);
	}
	
	public void setOddsHome(HashMap<String, Double> oddsHome) {
		this.oddsHome = oddsHome;
	}
	
	public void setOddsAway(HashMap<String, Double> oddsAway) {
		this.oddsAway = oddsAway;
	}
	
	public void setOddsDraw(HashMap<String, Double> oddsDraw) {
		this.oddsDraw = oddsDraw;
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