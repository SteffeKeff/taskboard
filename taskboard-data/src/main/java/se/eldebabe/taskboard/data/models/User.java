package se.eldebabe.taskboard.data.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Users")
public class User extends AbstractEntity{
	
	@Column(name = "user_id", unique = true)
	private String userID;
	
	@Column(name = "name")
	private String firstName;

	@Column(name = "user_name")
	private String userName;
	
	@ManyToOne
	private Team team;
	
	@OneToMany
	private Collection<WorkItem> workItems;
	
	protected User() {
	}

	
	public User(String userID, String  firstName, String userName, Team team){
		this.userID = userID;
		this.firstName = firstName;
		this.userName = userName;
		this.team = team;
	}
	
	public WorkItem addWorkItem(WorkItem workItem){
		workItems.add(workItem);
		return workItem;
	}
}
