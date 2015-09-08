package se.eldebabe.taskboard.data.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import se.eldebabe.taskboard.data.models.Team;
import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.repositories.TeamRepository;

public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Transactional
	public Team saveTeam(Team team){
		return teamRepository.save(team);
	}
	
	@Transactional
	public void deleteByName(String name){
		teamRepository.deleteByName(name);
	}
	
	@Transactional
	public Team deleteTeam(Team team){
		teamRepository.delete(team.getId());
		return team;
	}
	
	public List<Team> findTeam(String name){
		return teamRepository.findByName(name);
	}
	
	@Transactional
	public Team updateTeam(Team team){
		return teamRepository.save(team);
	}
	
	public List<Team> findAllTeams(){
		return (List<Team>) teamRepository.findAll();
	}
	
	@Transactional
	public Team addUserToTeam(User user, Team team){
		team.addUser(user);
		return updateTeam(team); 
	}

	public Collection<User> findUsersInTeam(Long id) {
		return teamRepository.findOne(id).getUsers();
	}

	public Collection<WorkItem> findWorkItemsInTeam(Long id) {
		Collection<User> users = findUsersInTeam(id);
		Collection<WorkItem> workItems = new HashSet<>(); 
		for(User user : users) {
			for(WorkItem workItem: user.getWorkItems()){
				workItems.add(workItem);
			}
		}
		return workItems;
	}
	
	
}
