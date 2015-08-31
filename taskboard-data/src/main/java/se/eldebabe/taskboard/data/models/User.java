package se.eldebabe.taskboard.data.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity{

	@Column(name = "user_id", unique = true)
	private String userID;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	private Team team;

	@OneToMany
	private Collection<WorkItem> workItems;

	protected User(){}

	public User(String userID, String userName, String firstName, String lastName, Team team,
			Collection<WorkItem> workItems){
		this.userID = userID;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.workItems = workItems;
	}

	public String getUserID(){
		return userID;
	}

	public String getUserName(){
		return userName;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public Team getTeam(){
		return team;
	}

	public Collection<WorkItem> getWorkItems(){
		return workItems;
	}

	public WorkItem addWorkItem(WorkItem workItem){
		workItems.add(workItem);
		return workItem;
	}
	
}
