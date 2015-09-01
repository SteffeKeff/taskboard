package se.eldebabe.taskboard.data.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.User;

public interface UserRepository extends CrudRepository<User, Long>{

	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);
	User findByUserName(String userName);
	User findByUserId(String userId);
	
	
	@Transactional
	List<User> deleteByUserId(String userId);
}
