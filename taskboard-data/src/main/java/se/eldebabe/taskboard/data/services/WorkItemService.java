package se.eldebabe.taskboard.data.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.Status;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.repositories.WorkItemRepository;

public class WorkItemService {
	
	@Autowired
	private WorkItemRepository workItemRepository;
	
	public WorkItem saveWorkItem(WorkItem workItem){
		return workItemRepository.save(workItem);
	}
	
	public WorkItem findWorkItem(long id){
		return workItemRepository.findOne(id);
	}
	
	public void deleteWorkItem(long id){
		workItemRepository.delete(id);
	}

	public List<WorkItem> findWorkItemsWithStatus(Status status) {
		return workItemRepository.findByStatus(status);
	}

	public List<WorkItem> findWorkItemWithDescriptionContaining(String description) {
		return workItemRepository.findByDescriptionContaining(description);
	}

	public List<WorkItem> findWorkItemsWithIssue() {
		return workItemRepository.findByIssueIdNotNull();
	}

}
