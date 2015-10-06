package se.eldebabe.taskboard.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.repositories.UserRepository;

public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User findUser(String userId) {
		return userRepository.findByUserId(userId);
	}

	public List<User> findByFirstname(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

	public List<User> findByLastname(String lastName) {
		return userRepository.findByLastName(lastName);
	}

	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	public void clearUsers() {
		userRepository.deleteAll();
	}
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public Iterable<User> findAllUsers(int pages, int size) {
		PageRequest pr = new PageRequest(pages, size);
		
		Page<User> users = userRepository.findAll(pr);
		return users;
	}
}
