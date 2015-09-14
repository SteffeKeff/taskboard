package se.eldebabe.taskboard.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.eldebabe.taskboard.data.models.Status;
import se.eldebabe.taskboard.data.models.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long> {

	List<WorkItem> findByStatus(Status status);

	List<WorkItem> findByDescriptionContaining(String description);

	List<WorkItem> findByIssueIdNotNull();

}
