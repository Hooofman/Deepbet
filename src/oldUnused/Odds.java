package oldUnused;
import java.util.HashMap;

/**
 * A class for creating Odds objects linked to Match objects
 * @author oskarem
 *
 */
public class Odds {
	private HashMap<String, Double> oddsHome;
	private HashMap<String, Double> oddsAway;
	private HashMap<String, Double> oddsDraw;

	/**
	 * Constructs an Odds object and HashMaps for home win, away win and draw
	 */
	public Odds() {
		oddsHome = new HashMap<String, Double>();
		oddsAway = new HashMap<String, Double>();
		oddsDraw = new HashMap<String, Double>();
	}
	
	/**
	 * Gets the odds for a home win and puts it the HashMap
	 * @param bookie bookmaker
	 * @param odds lastest odds
	 */
	public void putOddsHome(String bookie, double odds) {
		oddsHome.put(bookie, odds);
	}
	
	/**
	 * Gets the odds for a away win and puts it the HashMap
	 * @param bookie bookmaker
	 * @param odds lastest odds
	 */
	public void putOddsAway(String bookie, double odds) {
		oddsAway.put(bookie, odds);
	}
	
	/**
	 * Gets the odds for a draw and puts it the HashMap
	 * @param bookie bookmaker
	 * @param odds lastest odds
	 */
	public void putOddsDraw(String bookie, double odds) {
		oddsDraw.put(bookie, odds);
	}
	
	/**
	 * Get the odds for a home win from a specific bookmaker
	 * @param bookie bookmaker
	 * @return odds for home win
	 */
	public double getOddsForBookieHome(String bookie) {
		return oddsHome.get(bookie);
	}
	
	/**
	 * Get the odds for a away win from a specific bookmaker
	 * @param bookie bookmaker
	 * @return odds for away win
	 */
	public double getOddsForBookieAway(String bookie) {
		return oddsAway.get(bookie);
	}
	
	/**
	 * Get the odds for a draw from a specific bookmaker
	 * @param bookie bookmaker
	 * @return odds for draw
	 */
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
	
	/**
	 * Returns the HashMap containing all odds and bookies for a home win
	 * @return HashMap with odds and bookies 
	 */
	public HashMap<String, Double> getOddsHome() {
		return oddsHome;
	}

	/**
	 * Returns the HashMap containing all odds and bookies for a away win
	 * @return HashMap with odds and bookies 
	 */
	public HashMap<String, Double> getOddsAway() {
		return oddsAway;
	}

	/**
	 * Returns the HashMap containing all odds and bookies for a draw
	 * @return HashMap with odds and bookies 
	 */
	public HashMap<String, Double> getOddsDraw() {
		return oddsDraw;
	}

}