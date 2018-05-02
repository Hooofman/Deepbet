package testing;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Team;

class TeamTest {
	Team team;
	
	@BeforeEach
	void setUp() throws Exception {
		team = new Team("Lag1");
		
		for(int i = 0; i < 10; i++) {
			team.addPlayedMatch();
			team.setGoalsFor(1);
			team.setGoalsAgainst(1);
			team.setTablePosition(i+1);
		}
		team.addPlayedMatch();
		team.setGoalsFor(2);
		team.setGoalsAgainst(3);
		team.setTablePosition(12);
		
	}

	@Test
	void test() {
		Assert.assertEquals("Lag1", team.getName());
		Assert.assertEquals(11, team.getMatchesPlayed());
		Assert.assertEquals(2, (int)team.getGoalsFor(10));
		Assert.assertEquals(12, (int)team.getTablePosition(10));
	}

}
