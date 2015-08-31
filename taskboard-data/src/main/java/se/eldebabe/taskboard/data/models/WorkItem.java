package se.eldebabe.taskboard.data.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private boolean completed;

	@OneToMany
	private Collection<Issue> issues;
	
	protected WorkItem(){}

	public WorkItem(String title, String description){
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

	public boolean isCompleted(){
		return completed;
	}
	
	public Collection<Issue> getIssues(){
		return issues;
	}

	public void setCompleted(boolean completed){
		this.completed = completed;
	}
	
	public void addIssue(Issue issue){
		this.issues.add(issue);
	}

}
