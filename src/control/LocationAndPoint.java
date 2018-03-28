package control;

/**
 * Class for Location and points for a single match
 * @author Vixen666
 *
 */
public class LocationAndPoint {
	private int location;
	private int points;
	
	/**
	 * Creates a new instance and sets location and points
	 * @param location 1 for home, 0 for away
	 * @param points points taken that match
	 */
	public LocationAndPoint(int location, int points) {
		this.location = location;
		this.points = points;
	}
	
	/**
	 * Creates a new instance and only sets location
	 * @param location 1 for home, 0 for away
	 */
	public LocationAndPoint(int location) {
		this.location = location;
	}

	/**
	 * returns the location
	 * @return 1 for home, 0 for away
	 */
	public int getLocation() {
		return location;
	}
	/**
	 * sets the location and returns this object
	 * @param location 1 for home, 0 for away
	 * @return this object
	 */
	public LocationAndPoint setLocation(int location) {
		this.location = location;
		return this;
	}
	/**
	 * Returns points for this object
	 * @return
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * Sets points for this match
	 * @param points Points taken in this match
	 * @return this object
	 */
	public LocationAndPoint setPoints(int points) {
		this.points = points;
		return this;
	}
}
