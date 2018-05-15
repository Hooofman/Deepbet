package whiteBoxTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boundary.ConnectDatabase;
import entity.Match;
import entity.Team;

class TestDatabase {
	ConnectDatabase connection = new ConnectDatabase();
	@BeforeEach
	void setUp() throws Exception {
		connection.setDatabaseSettings("jdbc:mysql://192.168.1.2:3306/deepbet", "deepbet", "Deepbet123", "250", "games");
		connection.connect();
		Match match = new Match(new Team("team1"), new Team("team2"), 0);
		match.setDate("2018-09-12");
		match.setTime("18:59:00");
		match.setIsFinished("TIMED");
		double[] calcOutcome = {0.9, 0.0, 0.0};
		match.setCalcOutput(calcOutcome);
		match.setRecommendation('1');
		
		connection.createNewMatch(match, 2018, "PL");
		
	}

	@Test
	void test() {


	}

}
