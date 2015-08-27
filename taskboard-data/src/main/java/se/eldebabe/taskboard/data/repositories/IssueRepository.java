package se.eldebabe.taskboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long>{

}
