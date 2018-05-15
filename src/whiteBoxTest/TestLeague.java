package whiteBoxTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.League;
import entity.Match;
import entity.Season;
import entity.Team;
import gui.PrintListener;

class TestLeague {
	PrintListener pl = new PrintListener() {

		@Override
		public void updateText(String text) {
			// TODO Auto-generated method stub

		}

		@Override
		public void updateProgress(int current, int max) {
			// TODO Auto-generated method stub

		}
	};
	League league = new League("PL", pl);
	Season season = new Season(league, 2018, 1, 4, 0);
	ArrayList<Team> teams = new ArrayList<Team>();
	
	@BeforeEach
	void setUp() throws Exception {
		for(int i = 0; i < 20; i++) {
			league.addTeam(new Team("team"+(i+1)));
		}
		
		for(int i = 0; i < 10; i++) {
			Match match = new Match(league.getAllTeamObjects().get(i), league.getAllTeamObjects().get(19-i), 0);
			match.setHomeGoals(10-i);
			match.setAwayGoals(0);
			
			league.getAllTeamObjects().get(i).setGoalsFor(10-i);
			league.getAllTeamObjects().get(i).setGoalsAgainst(0);
			league.getAllTeamObjects().get(19-i).setGoalsFor(0);
			league.getAllTeamObjects().get(19-i).setGoalsAgainst(10-i);
			match.setIsFinished("FINISHED");
			match.setOutcome();
			season.addMatch(match);
		}
		season.updateTable();
//		league.removeLast3Teams();
//		league.resetTeamsForNewSeason();
	}

	@Test
	void test() {
		assertEquals(20, league.getAllTeamObjects().size());
		assertEquals(1, league.getAllTeamObjects().get(0).getMatchesPlayed());
	}

}
