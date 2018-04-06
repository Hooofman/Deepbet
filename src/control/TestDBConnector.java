package control;

public class TestDBConnector {

	public static void main(String[] args) {

		DBConnector dbConnector = new DBConnector();
		dbConnector.connect();
		dbConnector.insertIntoTable("odds");

		dbConnector.addPairDouble("bet365Home", 12.3);
		dbConnector.addPairString("homeTeam", "Namn p� hemmalag");
		dbConnector.addPairString("awayTeam", "Namn p� bortalag");

		dbConnector.getFullStatement();
		dbConnector.excecuteSavedStatement();
		dbConnector.getFullStatement();

		dbConnector.addPairString("awayTeam", "Nytt bortalag");
		dbConnector.addPairString("homeTeam", "Nytt hemmalag");
		String sqlStatement = dbConnector.getFullStatement();
		dbConnector.excecuteStatement(sqlStatement);

		dbConnector.addPairString("awayTeam", "Nytt bortalag");
		dbConnector.getFullStatement();

		dbConnector.clearPairs();
		dbConnector.addPairString("awayTeam", "Nytt bortalag");
		dbConnector.getFullStatement();
	}
}
