package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.repositories.UserRepository;

public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User saveDepartment(User user){
		return userRepository.save(user);
	}

}
