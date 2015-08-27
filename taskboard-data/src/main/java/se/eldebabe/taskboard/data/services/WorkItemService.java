package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.repositories.WorkItemRepository;

public class WorkItemService {
	
	@Autowired
	private WorkItemRepository workitemRepository;

}
