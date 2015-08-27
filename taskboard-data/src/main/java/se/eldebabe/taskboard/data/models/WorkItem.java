package se.eldebabe.taskboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Work_Items")
public class WorkItem extends AbstractEntity{

	@Column(name = "title")
	private String title;
	
	@Column(name = "desc")
	private String description;
	
	@Column(name = "completed")
	private boolean completed;
	
	protected WorkItem(){}
	
	public WorkItem(String title, String description){
		this.title = title;
		this.description = description;
	}
	
	
	
}
