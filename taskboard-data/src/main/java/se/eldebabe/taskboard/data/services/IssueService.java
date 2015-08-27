package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.repositories.IssueRepository;

public class IssueService {
	
	@Autowired
	private IssueRepository issueRepository;

}
