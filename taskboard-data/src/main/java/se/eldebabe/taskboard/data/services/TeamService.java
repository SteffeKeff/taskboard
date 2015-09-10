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
import se.eldebabe.taskboard.data.repositories.UserRepository;

public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public Team saveTeam(Team team){
		return teamRepository.save(team);
	}
	
	public Team deleteByName(String name){
		Team team = findTeamByName(name);
		if(team != null){
			for(User user: team.getUsers())
			{
				user.setTeam(null);
				userRepository.save(user);
			}
			team.getUsers().clear();
			teamRepository.delete(team.getId());
			return team;
		}else{
			return null;
		}
	}

	
	public Team delete(Long id){
		Team team = teamRepository.findOne(id);
		
		if(team != null){
			teamRepository.deleteById(id);
			return team;
		}
		return null;
	}
	
	public Team findTeamByName(String name){
		return teamRepository.findByName(name).get(0);
	}
	
	@Transactional
	public Team updateTeam(Team team){
		return teamRepository.save(team);
	}
	
	public Team findById(Long id){
		return teamRepository.findOne(id);
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
