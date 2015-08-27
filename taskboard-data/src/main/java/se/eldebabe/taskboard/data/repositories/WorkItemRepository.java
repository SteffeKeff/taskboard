package se.eldebabe.taskboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>{

}
