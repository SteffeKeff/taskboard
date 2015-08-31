package se.eldebabe.taskboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	protected WorkItem(){}
	
	public WorkItem(String title, String description){
		this.title = title;
		this.description = description;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
}
