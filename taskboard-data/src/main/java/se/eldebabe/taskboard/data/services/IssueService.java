package se.eldebabe.taskboard.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.repositories.IssueRepository;

public class IssueService {

	@Autowired
	private IssueRepository issueRepository;

	public Issue saveIssue(Issue issue) {
		return issueRepository.save(issue);
	}

	public Issue findIssueById(Long id) {
		return issueRepository.findOne(id);
	}

	public Issue deleteIssue(Long id) {
		Issue issue = issueRepository.findOne(id);
		issueRepository.delete(id);
		return issue;
	}

	public Issue updateIssue(Issue newIssue) {
		Issue oldIssue = issueRepository.findOne(newIssue.getId());
		if (null != oldIssue) {
			return issueRepository.save(newIssue);
		} else {
			return oldIssue;
		}
	}
	
	public List<Issue> findAllIssues() {
		return issueRepository.findAll();
	}
	
	public Iterable<Issue> findAllIssues(int pages, int size) {
		PageRequest pr = new PageRequest(pages, size);
		
		Page<Issue> issues = issueRepository.findAll(pr);
		return issues;
	}
}
