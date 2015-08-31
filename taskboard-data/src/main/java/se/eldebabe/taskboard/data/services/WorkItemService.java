package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.WorkItem;
import se.eldebabe.taskboard.data.repositories.WorkItemRepository;

public class WorkItemService {
	
	@Autowired
	private WorkItemRepository workItemRepository;
	
	public WorkItem saveDepartment(WorkItem workItem){
		return workItemRepository.save(workItem);
	}

}
