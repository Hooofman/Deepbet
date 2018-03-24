package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

/**
 * 
 * Class for connecting to a MySQL-database
 * @author Vixen666
 *
 */

public class DBConnector {
	// Database Constants
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://192.168.1.2:3306/deepbet";
	private static final String USERNAME = "deepbet";
	private static final String PASSWORD = "Deepbet123";
	private static final String MAX_POOL = "250";

	//Strings and maps for SQL-Statements
	private String table;
	private HashMap<String, String> pairsString;
	private HashMap<String, Double> pairsDouble;

	private Connection connection;
	private Properties properties;

	/**
	 * Simple Constructor
	 * 
	 */
	public DBConnector() {
		pairsString = new HashMap<String, String>();
		pairsDouble = new HashMap<String, Double>();
	}

	/**
	 * 
	 * Creates a propertie-object out of the constants above
	 * @return properties
	 * 
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
	 * Connects to the db, using the previous properties
	 * @return connection
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
	 * Disconnect from the db
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
	 * Selects witch table to use during excecution
	 * @param table Database-table to use
	 */
	public void insertIntoTable(String table) {
		this.table = table;
	}

	/**
	 * Adds a new pair, String value
	 * @param column	The column the value shold be written to
	 * @param value		The value
	 */
	public void addPairString(String column, String value) {
		pairsString.put(column, value);
	}
	/**
	 * Adds a new pair, Double value
	 * @param column	The column the value shold be written to
	 * @param value		The value
	 */
	public void addPairDouble(String column, double value) {
		pairsDouble.put(column, value);
	}

	/**
	 * Creates a full SQL-statement from saved Pairs 
	 * @return full SQL-Statement
	 */
	public String getFullStatement() {
		String columns = "(";
		String value = "(";
		Iterator it = pairsString.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if(it.hasNext()) {
				columns += ((String)pair.getKey())+ ",";
				value += "'" + (String)pair.getValue() +"',";
			}else {
				columns += ((String)pair.getKey());
				value += "'"+ (String)pair.getValue() +"'";
			}
		}

		Iterator it2 = pairsDouble.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry pair = (Map.Entry)it2.next();
			columns += "," +(String)pair.getKey();
			value += "," + pair.getValue().toString();
		}
		columns += ")";
		value += ")";

		String fullStatement = "INSERT INTO " + table +" "+columns +" VALUES " + value;
		System.out.println("Fullstatment: " +fullStatement);
		return fullStatement;
	}

	/**
	 * Removes all saved pairs
	 */
	public void clearPairs() {
		System.out.println("Nu tömms paren");
		pairsDouble.clear();
		pairsString.clear();
	}
	
	/**
	 * Excetues the saved SQL-Statement and then clears all pairs
	 */
	public void excecuteSavedStatement() {
		excecuteStatement(getFullStatement());
		clearPairs();
	}

	/**
	 * Excecutes a SQL-Statement. To be used with getFullStatement()
	 * @param sqlStatement, Full SQL-statement
	 */
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