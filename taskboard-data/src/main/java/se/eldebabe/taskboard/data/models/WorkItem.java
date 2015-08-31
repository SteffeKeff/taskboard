package se.eldebabe.taskboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	protected WorkItem(){}

	public WorkItem(String title, String description){
		status = Status.NOT_STARTED;
		this.title = title;
		this.description = description;
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

	public void setCompleted(Status status){
		this.status = status;
	}

}
