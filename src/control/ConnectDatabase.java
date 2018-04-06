package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

import entity.Match;

public class ConnectDatabase {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://192.168.1.2:3306/deepbet";
	private static final String USERNAME = "deepbet";
	private static final String PASSWORD = "Deepbet123";
	private static final String MAX_POOL = "250"; // vad är detta?
	private Connection connection;
	private Properties properties;
	private String table;
	private Match match;

	public ConnectDatabase() {

	}

	public void addMatchToSave(Match match) {
		this.match = match;
	}

	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}

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

	public void insertIntoTable(String table) {
		this.table = table;
	}

	public void createStatementForPlayedMatches(int season, String leagueName) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		int homeGoals = match.getHomeGoals();
		int awayGoals = match.getAwayGoals();
		int outcome = match.getOutcome();
		Date date = (Date) match.getDate(); // Kolla format och om rätt Date (SQL nu)!
		Time time = new Time(date.getTime());
		String league = leagueName;
		char recommendation = match.getRecommendation();
		int strengthRec = match.getStrengthRec();

		String statement = "INSERT INTO " + table + " VALUES (" + homeTeam + ", " + awayTeam + ", " + homeGoals + ", "
				+ awayGoals + ", " + date + ", " + time + ", " + season + ", " + league + ", " + outcome + ", "
				+ recommendation + ", " + strengthRec + ")";

		excecuteStatement(statement);
	}

	public void createStatementForUpcomming(int season, String leagueName) {
		String homeTeam = match.getHomeTeam().getName();
		String awayTeam = match.getAwayTeam().getName();
		Date date = (Date) match.getDate(); // Kolla format och om rätt Date (SQL nu)!
		Time time = new Time(date.getTime());
		String league = leagueName;
		double[] calculations = match.get1X2Outcome();
		double calcHome = calculations[0];
		double calcDraw = calculations[1];
		double calcAway = calculations[2];
		char recommendation = match.getRecommendation();
		int strengthRec = match.getStrengthRec();

		String statement = "INSERT INTO " + table + "VALUES (" + homeTeam + ", " + awayTeam + ", " + date + ", " + time
				+ ", " + season + ", " + league + ", " + calcHome + ", " + calcAway + ", " + calcDraw + ", "
				+ recommendation + ", " + strengthRec + ")";
		excecuteStatement(statement);
	}

	public void excecuteStatement(String sqlStatement) {
		java.sql.PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sqlStatement);
			statement.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
