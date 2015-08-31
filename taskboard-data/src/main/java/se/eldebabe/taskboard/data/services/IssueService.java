package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.repositories.IssueRepository;

public class IssueService {
	
	@Autowired
	private IssueRepository issueRepository;
	
	public Issue saveDepartment(Issue issue){
		return issueRepository.save(issue);
	}

}
