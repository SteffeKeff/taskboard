package se.eldebabe.taskboard.data.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import se.eldebabe.taskboard.data.models.Status;
import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.repositories.WorkItemRepository;

public class WorkItemService {

	@Autowired
	private WorkItemRepository workItemRepository;

	public WorkItem saveWorkItem(WorkItem workItem) {
		return workItemRepository.save(workItem);
	}

	public WorkItem findWorkItem(long id) {
		return workItemRepository.findOne(id);
	}

	public void deleteWorkItem(long id) {
		workItemRepository.delete(id);
	}

	public List<WorkItem> findWorkItemsWithStatus(Status status) {
		return workItemRepository.findByStatus(status);
	}
	
	public ArrayList<WorkItem> findWithinDate(Date fromDate, Date toDate) {
		return workItemRepository.findByModifiedDateBetweenAndStatus(fromDate, toDate, Status.COMPLETED);
	}

	public List<WorkItem> findWorkItemWithDescriptionContaining(String description) {
		return workItemRepository.findByDescriptionContaining(description);
	}

	public List<WorkItem> findWorkItemsWithIssue() {
		return workItemRepository.findByIssueIdNotNull();
	}
	
	public List<WorkItem> findAllWorkItems() {
		return workItemRepository.findAll();
	}
	
	public Iterable<WorkItem> findAllWorkItems(int pages, int size) {
		PageRequest pr = new PageRequest(pages, size);
		
		Page<WorkItem> workItems = workItemRepository.findAll(pr);
		return workItems;
	}

}
