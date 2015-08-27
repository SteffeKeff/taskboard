package se.eldebabe.taskboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{

}
