package se.eldebabe.taskboard.data.services;

import org.springframework.beans.factory.annotation.Autowired;

import se.eldebabe.taskboard.data.models.Issue;
import se.eldebabe.taskboard.data.repositories.IssueRepository;

public class IssueService{

	@Autowired
	private IssueRepository issueRepository;

	public Issue saveIssue(Issue issue){
		return issueRepository.save(issue);
	}

	public Issue findIssueById(Long id){
		return issueRepository.findOne(id);
	}

	public Issue deleteIssue(Issue issue){
		issueRepository.delete(issue);
		return issue;
	}

	public Issue updateIssue(Issue newIssue){
		Issue oldIssue = issueRepository.findOne(newIssue.getId());
		if(null != oldIssue) {
			return issueRepository.save(newIssue);
		}else{
			return oldIssue;
		}
	}
}
