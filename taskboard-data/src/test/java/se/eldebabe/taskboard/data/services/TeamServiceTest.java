package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Team;

public class TeamServiceTest {

	private static AnnotationConfigApplicationContext context;
	private TeamService teamService;
	private Team team;
	private List<Team> teams;
	
	@BeforeClass
	public static void setupConfigs(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
	}
	
	@Before
	public void setup(){
		team = new Team("deldebabe");
		teamService = context.getBean(TeamService.class);
	}
	
	@Test
	public void assertThatTeamIsSavable(){
		assertThat("Added Team should be returned", team, is(teamService.saveTeam(team)));
	}
	
	@Test
	public void assertThatTeamCanBeDeleted(){
		teamService.saveTeam(team);
		teamService.deleteTeam(team);
		assertThat("teamService cant find team", null, is(teamService.findTeam(team)));
	}
	
	@Test
	public void assertThatTeamCanBeUpdated(){
		Team updatedTeam = teamService.updateTeam(team);
		assertThat("Team is updated", team, is(updatedTeam));			
	}
	
	@Test
	public void assertThatAllTeamesCanBeFetched(){
		Team team2 = new Team("team2");
		Team team3 = new Team("team2");
		teamService.saveTeam(team);
		teamService.saveTeam(team2);
		teamService.saveTeam(team3);
		
		teams = teamService.findAllTeams();
		assertThat("Team count matches", 3, is(teams.size()));
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}

}