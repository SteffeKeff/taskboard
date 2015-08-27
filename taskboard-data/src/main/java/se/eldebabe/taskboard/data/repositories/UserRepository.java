package se.eldebabe.taskboard.data.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	List<User> findByuserID(String userID);
	List<User> findByuserName(String userName);
	List<User> findByfirstName(String firstName);


	@Transactional
	List<User> deleteByuserID(String userID);

}
