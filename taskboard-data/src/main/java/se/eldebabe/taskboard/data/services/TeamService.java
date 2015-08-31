package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.repositories.TeamRepository;

public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	public Team saveTeam(Team team){
		return teamRepository.save(team);
	}
	
}
