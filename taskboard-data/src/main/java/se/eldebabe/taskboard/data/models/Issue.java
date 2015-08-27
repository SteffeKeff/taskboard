package se.eldebabe.taskboard.data.models;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "issues")
public class Issue extends AbstractEntity {

	@Column
	private String description;
	
	protected Issue(){}
	
	public Issue(String description)
	{
		super();
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
	
}
