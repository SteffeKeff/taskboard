package se.eldebabe.taskboard.data.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.repositories.TeamRepository;

public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Transactional
	public Team saveTeam(Team team){
		return teamRepository.save(team);
	}
	
	@Transactional
	public Team deleteTeam(Team team){
		teamRepository.delete(team.getId());
		return team;
	}
	
	@Transactional
	public Team updateTeam(Team team){
		return teamRepository.save(team);
	}
	
	@Transactional
	public List<Team> findAllTeams(){
		return (List<Team>) teamRepository.findAll();
	}
	
}
