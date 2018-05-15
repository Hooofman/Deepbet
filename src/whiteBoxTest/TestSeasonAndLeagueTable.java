package whiteBoxTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.League;
import entity.LeagueTable;
import entity.Match;
import entity.Season;
import entity.Team;
import gui.PrintListener;

class TestSeasonAndLeagueTable {
	ArrayList<Team> teams = new ArrayList<Team>();
	LeagueTable leagueTable = new LeagueTable();
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
	@BeforeEach
	void setUp() throws Exception {
		league.addSeason(season);
		for(int i = 0; i<6; i++) {
			teams.add(new Team("team"+(i+1)));
			league.addTeam(teams.get(i));	
		}
		Match match = new Match(teams.get(0), teams.get(5),0);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(0).setGoalsFor(3);
		teams.get(0).setGoalsAgainst(0);
		teams.get(5).setGoalsFor(0);
		teams.get(5).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();		
		season.addMatch(match);

		match = new Match(teams.get(0), teams.get(1),1);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(0).setGoalsFor(3);
		teams.get(0).setGoalsAgainst(0);
		teams.get(1).setGoalsFor(0);
		teams.get(1).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);

		match = new Match(teams.get(0), teams.get(2),2);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(0).setGoalsFor(3);
		teams.get(0).setGoalsAgainst(0);
		teams.get(2).setGoalsFor(0);
		teams.get(2).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);
		
		match = new Match(teams.get(0), teams.get(3),3);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(0).setGoalsFor(3);
		teams.get(0).setGoalsAgainst(0);
		teams.get(3).setGoalsFor(0);
		teams.get(3).setGoalsAgainst(3);	
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);		
		
		match = new Match(teams.get(0), teams.get(4),4);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(0).setGoalsFor(3);
		teams.get(0).setGoalsAgainst(0);
		teams.get(4).setGoalsFor(0);
		teams.get(4).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);			
		
		match = new Match(teams.get(1), teams.get(4),0);
		match.setHomeGoals(5);
		match.setAwayGoals(0);
		teams.get(1).setGoalsFor(5);
		teams.get(1).setGoalsAgainst(0);
		teams.get(4).setGoalsFor(0);
		teams.get(4).setGoalsAgainst(5);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);			
		
		match = new Match(teams.get(1), teams.get(5),2);
		match.setHomeGoals(5);
		match.setAwayGoals(0);
		teams.get(1).setGoalsFor(5);
		teams.get(1).setGoalsAgainst(0);
		teams.get(5).setGoalsFor(0);
		teams.get(5).setGoalsAgainst(5);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);		
		
		match = new Match(teams.get(1), teams.get(2),3);
		match.setHomeGoals(0);
		match.setAwayGoals(1);
		teams.get(1).setGoalsFor(0);
		teams.get(1).setGoalsAgainst(1);
		teams.get(2).setGoalsFor(1);
		teams.get(2).setGoalsAgainst(0);		
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);			
		
		match = new Match(teams.get(1), teams.get(3),4);
		match.setHomeGoals(1);
		match.setAwayGoals(0);
		teams.get(1).setGoalsFor(1);
		teams.get(1).setGoalsAgainst(0);
		teams.get(3).setGoalsFor(0);
		teams.get(3).setGoalsAgainst(1);	
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);	
		
		match = new Match(teams.get(2), teams.get(3),0);
		match.setHomeGoals(0);
		match.setAwayGoals(1);
		teams.get(2).setGoalsFor(0);
		teams.get(2).setGoalsAgainst(1);
		teams.get(3).setGoalsFor(1);
		teams.get(3).setGoalsAgainst(0);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);	
		
		match = new Match(teams.get(2), teams.get(4),1);
		match.setHomeGoals(4);
		match.setAwayGoals(0);
		teams.get(2).setGoalsFor(4);
		teams.get(2).setGoalsAgainst(0);
		teams.get(4).setGoalsFor(0);
		teams.get(4).setGoalsAgainst(4);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);		
		
		match = new Match(teams.get(2), teams.get(5),4);
		match.setHomeGoals(4);
		match.setAwayGoals(0);
		teams.get(2).setGoalsFor(4);
		teams.get(2).setGoalsAgainst(0);
		teams.get(5).setGoalsFor(0);
		teams.get(5).setGoalsAgainst(4);	
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);		
		
		match = new Match(teams.get(3), teams.get(5),1);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(3).setGoalsFor(3);
		teams.get(3).setGoalsAgainst(0);
		teams.get(5).setGoalsFor(0);
		teams.get(5).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();	
		season.addMatch(match);			
		
		match = new Match(teams.get(3), teams.get(4),2);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(3).setGoalsFor(3);
		teams.get(3).setGoalsAgainst(0);
		teams.get(4).setGoalsFor(0);
		teams.get(4).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);			
		
		match = new Match(teams.get(4), teams.get(5),3);
		match.setHomeGoals(3);
		match.setAwayGoals(0);
		teams.get(4).setGoalsFor(3);
		teams.get(4).setGoalsAgainst(0);
		teams.get(5).setGoalsFor(0);
		teams.get(5).setGoalsAgainst(3);
		match.setIsFinished("FINISHED");
		match.setOutcome();
		season.addMatch(match);	
	
		season.updateTable();
		
		Match match2 = new Match(teams.get(0), teams.get(1), 5);
		match2.setIsFinished("TIMED");
		season.addMatch(match2);
		
		Match match3 = new Match(teams.get(2), teams.get(2), 5);
		match3.setIsFinished("TIMED");
		season.addMatch(match3);
	}

	@Test
	void test() {
		assertEquals(15, teams.get(0).getSumOfList(teams.get(0).getTotalPointsThisSeason()));
		assertEquals(9, teams.get(1).getSumOfList(teams.get(1).getTotalPointsThisSeason()));
		assertEquals(9, teams.get(2).getSumOfList(teams.get(2).getTotalPointsThisSeason()));
		assertEquals(9, teams.get(3).getSumOfList(teams.get(3).getTotalPointsThisSeason()));
		assertEquals(3, teams.get(4).getSumOfList(teams.get(4).getTotalPointsThisSeason()));
		assertEquals(0, teams.get(5).getSumOfList(teams.get(5).getTotalPointsThisSeason()));
		
		assertEquals(15, teams.get(0).getSumOfList(teams.get(0).getGoalsFor()));
		assertEquals(11, teams.get(1).getSumOfList(teams.get(1).getGoalsFor()));
		assertEquals(9, teams.get(2).getSumOfList(teams.get(2).getGoalsFor()));
		assertEquals(7, teams.get(3).getSumOfList(teams.get(3).getGoalsFor()));
		assertEquals(3, teams.get(4).getSumOfList(teams.get(4).getGoalsFor()));
		assertEquals(0, teams.get(5).getSumOfList(teams.get(5).getGoalsFor()));
		
		assertEquals(0, teams.get(0).getSumOfList(teams.get(0).getGoalsAgainst()));
		assertEquals(4, teams.get(1).getSumOfList(teams.get(1).getGoalsAgainst()));
		assertEquals(4, teams.get(2).getSumOfList(teams.get(2).getGoalsAgainst()));
		assertEquals(4, teams.get(3).getSumOfList(teams.get(3).getGoalsAgainst()));
		assertEquals(15, teams.get(4).getSumOfList(teams.get(4).getGoalsAgainst()));
		assertEquals(18, teams.get(5).getSumOfList(teams.get(5).getGoalsAgainst()));
		
		for(int i = 0; i < league.getAllTeamObjects().size(); i++) {
			assertEquals((i+1), season.getAllTeams().get(i).getTablePosition(0));
		}

		
		
		assertEquals(2, season.getMatchesByStatus("TIMED").size());
		assertEquals(15, season.getMatchesByStatus("FINISHED").size());
	}

}
