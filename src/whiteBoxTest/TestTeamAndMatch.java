package whiteBoxTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Match;
import entity.Team;

class TestTeamAndMatch {
	Team team1 = new Team("team1");
	Team team2 = new Team("team2");
	ArrayList<Match> matches = new ArrayList<Match>();
	int[] team1Goals = {1,1,2,2,3,3,4,4,5,5}; //sum = 30 avg last 5 = 21/5
	int[] team2Goals = {2,2,2,2,1,1,1,5,0,0}; //sum = 16 avg last 5 = 7/5
	@BeforeEach
	void setUp() throws Exception {
		for(int i = 0; i < 10; i++) {
			if(i%2 == 0) {
				matches.add(new Match(team1, team2, i));
				matches.get(i).setHomeGoals(team1Goals[i]);
				matches.get(i).setAwayGoals(team2Goals[i]);
				team1.setGoalsFor(team1Goals[i]);
				team2.setGoalsFor(team2Goals[i]);
				
				team1.setGoalsAgainst(team2Goals[i]);
				team2.setGoalsAgainst(team1Goals[i]);
			}else {
				matches.add(new Match(team2, team1, i));
				matches.get(i).setAwayGoals(team1Goals[i]);
				matches.get(i).setHomeGoals(team2Goals[i]);
				team1.setGoalsFor(team1Goals[i]);
				team2.setGoalsFor(team2Goals[i]);
				
				team1.setGoalsAgainst(team2Goals[i]);
				team2.setGoalsAgainst(team1Goals[i]);
				
			}
			team1.addPlayedMatch();
			team2.addPlayedMatch();
			
			
		}
	}

	@Test
	void test() {
		assertEquals("team1", team1.getName());
		assertEquals("team2", team2.getName());
		assertEquals(10, team1.getMatchesPlayed());
		assertEquals(10, team2.getMatchesPlayed());
		
		assertEquals(1, team1.getGoalsFor(0));
		assertEquals(2, team2.getGoalsFor(0));
		assertEquals(2, team1.getGoalsFor(3));
		assertEquals(2, team2.getGoalsFor(3));
		assertEquals(5, team1.getGoalsFor(8));
		assertEquals(0, team2.getGoalsFor(8));
		
		
		assertEquals(30, team1.getSumOfList(team1.getGoalsFor()));
		assertEquals(16, team2.getSumOfList(team2.getGoalsFor()));
		
		assertEquals(2, team1.getGoalsAgainst(1));
		assertEquals(1, team2.getGoalsAgainst(1));
		assertEquals(1, team1.getGoalsAgainst(4));
		assertEquals(3, team2.getGoalsAgainst(4));
		assertEquals(0, team1.getGoalsAgainst(9));
		assertEquals(5, team2.getGoalsAgainst(9));
		
		assertEquals(16, team1.getSumOfList(team1.getGoalsAgainst()));
		assertEquals(30, team2.getSumOfList(team2.getGoalsAgainst()));
		
		assertEquals(7.0/5.0, team1.getAverageForNGames(team1.getGoalsAgainst(), 5));
		assertEquals(21.0/5.0, team2.getAverageForNGames(team2.getGoalsAgainst(), 5));
		
		
	}

}
