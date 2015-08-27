package se.eldebabe.taskboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.User;


public interface UserRepository extends CrudRepository<User, Long>{

}
