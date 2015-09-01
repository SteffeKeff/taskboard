package se.eldebabe.taskboard.data.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "work_items")
public class WorkItem extends AbstractEntity{

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "completed")
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Issue> issues;
	
	protected WorkItem(){}

	public WorkItem(String title, String description){
		status = Status.NOT_STARTED;
		this.title = title;
		this.description = description;
		issues = new ArrayList<Issue>();
	}

	public String getTitle(){
		return title;
	}

	public String getDescription(){
		return description;
	}

	public Status getStatus(){
		return status;
	}
	
	public Collection<Issue> getIssues(){
		return issues;
	}

	public void setCompleted(Status status){
		this.status = status;
	}
	
	public void addIssue(Issue issue){
		this.issues.add(issue);
	}

}
