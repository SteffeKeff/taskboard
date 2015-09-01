package se.eldebabe.taskboard.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.User;
import se.eldebabe.taskboard.data.repositories.UserRepository;

public class UserService{

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user){
		return userRepository.save(user);
	}
	
	public User updateUser(Long id, User user){
		User oldUser = userRepository.findOne(id);
		user.setId(oldUser.getId());
		return userRepository.save(user);
	}

	public User findUser(String userID){
		return userRepository.findByUserId(userID);
	}

	public List<User> findByFirstname(String firstName){
		return userRepository.findByFirstName(firstName);
	}

	public List<User> findByLastname(String lastName){
		return userRepository.findByLastName(lastName);
	}

	public User findByUserName(String userName){
		return userRepository.findByUserName(userName);
	}

	public void deleteUser(Long id){
		userRepository.delete(id);
	}
}
