package boundary;

/**
 * author Sven Lindqvist
 * Class for connection with database.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

import entity.Match;

public class ConnectDatabase {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://deepbet.ddns.net:3306/deepbet";
	private static final String USERNAME = "deepbet";
	private static final String PASSWORD = "Deepbet123";
	private static final String MAX_POOL = "250";
	private Connection connection;
	private Properties properties;
	private String table;

	public ConnectDatabase() {

	}

	/**
	 * Creates properties using the username, password and max pool.
	 * 
	 * @return Properties used to access the database.
	 */
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}

	/**
	 * Establishes a connection with the database.
	 * 
	 * @return Connection The connection object.
	 */
	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	/**
	 * Disconnects from the database.
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the table for storage in the database.
	 * 
	 * @param table
	 *            in database to which the data is to be stored in.
	 */
	public void insertIntoTable(String table) {
		this.table = table;
	}

	/**
	 * Creates a statement containing all the data for a played match that is to be
	 * stored in the database. Calls the executeStatement method.
	 * 
	 * @param match
	 *            The match that is to be stored in the database.
	 * @param season
	 *            The number of the season
	 * @param leagueName
	 *            The name of the league.
	 */
	public void createStatementForPlayedMatches(Match match, int season, String leagueName) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		int homeGoals = match.getHomeGoals();
		int awayGoals = match.getAwayGoals();
		int outcome = match.getOutcome();
		Date date = Date.valueOf(match.getDate()); // Kolla format och om rätt Date (SQL nu)!
		Time time = Time.valueOf(match.getTime());
		String league = leagueName;
		char recommendation = match.getRecommendation();
		int strengthRec = match.getStrengthRec();

		String statement = "INSERT INTO " + table + " VALUES ('" + homeTeam + "', '" + awayTeam + "', '" + homeGoals
				+ "', '" + awayGoals + "', '" + date + "', '" + time + "', '" + season + "', '" + league + "', '"
				+ outcome + "', '" + recommendation + "', '" + strengthRec + "')";

		excecuteStatement(statement);
	}

	/**
	 * Creates a statement containing all the data for an upcoming match that is to
	 * be stored in the database. Calls the executeStatement method.
	 * 
	 * @param match
	 *            The match that is to be stored in the database.
	 * @param season
	 *            The number of the season
	 * @param leagueName
	 *            The name of the league.
	 */
	public void createStatementForUpcomming(Match match, int season, String leagueName) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = Date.valueOf(match.getDate()); // Kolla format och om rätt Date (SQL nu)!
		Time time = Time.valueOf(match.getTime());
		String league = leagueName;
		double[] calculations = match.getCalcOutput();
		double calcHome = calculations[0];
		double calcDraw = calculations[1];
		double calcAway = calculations[2];
		char recommendation = match.getRecommendation();
		int strengthRec = match.getStrengthRec();

		String statement = "INSERT INTO " + table + " VALUES ('" + homeTeam + "', '" + awayTeam + "', '" + date + "', '"
				+ time + "', '" + season + "', '" + league + "', '" + calcHome + "', '" + calcAway + "', '" + calcDraw
				+ "', '" + recommendation + "', '" + strengthRec + "')";
		excecuteStatement(statement);
	}
	
	public void createNewMatch(Match match, int season, String leagueName) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = Date.valueOf(match.getDate());
		Time time = Time.valueOf(match.getTime());
		double[] calculations = match.getCalcOutput();
		double calcHome = calculations[0];
		double calcDraw = calculations[1];
		double calcAway = calculations[2];
		
		String sql = "INSERT INTO games (HomeTeam, AwayTeam, DatePlayed, TimePlayed, Season, League, CalcHome, CalcAway, CalcDraw, Recommendation, Status)"
				+ " VALUES ('" + homeTeam + "', '" 
				+ awayTeam + "', '" 
				+ date + "', '" 
				+ time + "', '" 
				+ season + "', '" 
				+ leagueName + "', '" 
				+ calcHome + "', '" 
				+ calcAway + "', '"
				+ calcDraw + "', '"
				+ match.getRecommendation() + "', '"
				+ match.getStatus() + "')";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	public void updateCalculatedMatches(Match match) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = Date.valueOf(match.getDate());
		String sql = "UPDATE games "
				+ "set GoalsHomeTeam = '" + match.getHomeGoals() + "'"
						+ ", GoalsAwayTeam = '" + match.getAwayGoals() + "'"
								+ ", Outcome = '" + match.getOutcomeChar() + "'"
										+ ", Status = '" + match.getStatus() + "'"
												+ "where HomeTeam = '" + homeTeam + "' AND AwayTeam = '" + awayTeam + "' AND DatePlayed = '" + date + "'";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Sends the data to the database.
	 * 
	 * @param sqlStatement
	 *            The data to be stored in the database.
	 */
	public void excecuteStatement(String sqlStatement) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sqlStatement);
			statement.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
