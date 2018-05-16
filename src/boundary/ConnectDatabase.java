package boundary;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.ResultSet;
import java.util.Properties;

import entity.Match;

/**
 * Class for connection with database.
 * 
 * @author Sven Lindqvist
 */
public class ConnectDatabase {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static String DATABASE_URL = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String MAX_POOL = "";

	private Connection connection;
	private Properties properties;
	private String table;

	public ConnectDatabase() {

	}

	/**
	 * 
	 * @param dataBaseURL url to the database
	 * @param userName Username for database
	 * @param passWord Password for user
	 * @param maxPool Max Pool, usually 250
	 * @param table Default table to insert into, use insertIntoTable() to change this
	 */
	public void setDatabaseSettings(String dataBaseURL, String userName, String passWord, String maxPool,
			String table) {
		ConnectDatabase.DATABASE_URL = dataBaseURL;
		ConnectDatabase.USERNAME = userName;
		ConnectDatabase.PASSWORD = passWord;
		ConnectDatabase.MAX_POOL = maxPool;
		this.table = table;
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
		System.out.println(properties);
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
		} else {
			System.out.println("Error, there is no connection to the database to disconnect from");
		}
	}

	/**
	 * Sets the table for storage in the database.
	 * 
	 * @param table in database to which the data is to be stored in.
	 */
	public void insertIntoTable(String table) {
		this.table = table;
	}

	/**
	 * Creates a statement containing all the data for an match that is to be stored in the database.
	 * 
	 * @param match The match to be stored.
	 * @param season The number of the season.
	 * @param leagueName The name of the league.
	 */

	public void createNewMatch(Match match, int season, String leagueName) {
		System.out.println(table);
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = Date.valueOf(match.getDate());
		Time time = Time.valueOf(match.getTime());
		double[] calculations = match.getCalcOutput();
		double calcHome = calculations[0];
		double calcDraw = calculations[1];
		double calcAway = calculations[2];

		String sql = "INSERT INTO " + table
				+ " (HomeTeam, AwayTeam, DatePlayed, TimePlayed, Season, League, CalcHome, CalcAway, CalcDraw, Recommendation, Status)"
				+ " VALUES ('" + homeTeam + "', '" + awayTeam + "', '" + date + "', '" + time + "', '" + season + "', '"
				+ leagueName + "', '" + calcHome + "', '" + calcAway + "', '" + calcDraw + "', '"
				+ match.getRecommendation() + "', '" + match.getStatus()
				+ "') ON DUPLICATE KEY UPDATE Recommendation = '" + match.getRecommendation() + "' , CalcHome = '"
				+ calcHome + "' , CalcAway = '" + calcAway + "' , CalcDraw = '" + calcDraw + "'";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates a match in the database that wasn't finished to finished and sets the results in the match.
	 * 
	 * @param match The match to be updated.
	 */
	public void updateCalculatedMatches(Match match) {

		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = Date.valueOf(match.getDate());

		String sql = "UPDATE " + table + " set GoalsHomeTeam = '" + match.getHomeGoals() + "'" + ", GoalsAwayTeam = '"
				+ match.getAwayGoals() + "'" + ", Outcome = '" + match.getOutcomeChar() + "'" + ", Status = '"
				+ match.getStatus() + "'" + " where HomeTeam = '" + homeTeam + "' AND AwayTeam = '" + awayTeam
				+ "' AND DatePlayed = '" + date + "'";
		System.out.println(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the data to the database.
	 * 
	 * @param sqlStatement The data to be stored in the database.
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
