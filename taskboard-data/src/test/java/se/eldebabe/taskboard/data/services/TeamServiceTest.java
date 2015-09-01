package se.eldebabe.taskboard.data.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.models.User;

public class TeamServiceTest {

	private static AnnotationConfigApplicationContext context;
	private static TeamService teamService;
	private Team team;
	private static ArrayList<Team> teams;
	
	
	@BeforeClass
	public static void setupConfigs(){
		context = new AnnotationConfigApplicationContext();
		context.scan("se.eldebabe.taskboard.data.configs");
		context.refresh();
		teams = new ArrayList<>();
		teamService = context.getBean(TeamService.class);
	}
	
	@Test
	public void assertThatTeamIsSavable(){
		team = new Team("deldebabe0");
		assertThat("Added Team should be returned", team, is(teamService.saveTeam(team)));
	}
	
	@Test
	public void assertThatTeamCanBeDeleted(){
		team = new Team("deldebabe1");
		teamService.saveTeam(team);
		teamService.deleteTeam(team);
		assertThat("teamService cant find team", null, is(teamService.findTeam(team)));
	}
	
	@Test
	public void assertThatTeamCanBeUpdated(){
		team = new Team("deldebabe2");
		Team updatedTeam = teamService.updateTeam(team);
		assertThat("Team is updated", team, is(updatedTeam));			
	}
	
	@Test
	public void assertThatAllTeamesCanBeFetched(){
		team = new Team("deldebabe3");
		Team team2 = new Team("team2");
		Team team3 = new Team("team2");
		teamService.saveTeam(team);
		teamService.saveTeam(team2);
		teamService.saveTeam(team3);
		
		teams = (ArrayList<Team>) teamService.findAllTeams();
		assertThat("Team count matches", 4, is(teams.size()));
	}
	
	/*//// These tests has relations ////*/
	
	@Test
	public void assertThatUserCanBeAddedToTeam(){
		team = new Team("deldebabe4");
		UserService us = context.getBean(UserService.class);
		User user = new User("blabla", "userName", "firstName","lastName");
		user.setTeam(team);
		us.saveUser(user);
		assertThat("user is in team", 1, is(teamService.findTeam(team).getUsers().size()));
	}
	
	@Test
	public void assertThatAllWorkingItemsCanBeFetchedByTeam(){
		
	}
	
	@AfterClass
	public static void tearDown(){
		context.close();
	}

}