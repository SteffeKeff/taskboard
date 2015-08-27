package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.repositories.UserRepository;

public class UserService {
	
	@Autowired
	private UserRepository userRepository;

}
