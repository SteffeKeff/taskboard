package se.eldebabe.taskboard.data.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User extends AbstractEntity{

	@Column(name = "user_id", unique = true)
	private String userId;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "team_id")
	@JsonBackReference
	private Team team;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
//	@JsonManagedReference
	private Collection<WorkItem> workItems;

	protected User() {
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setWorkItems(Collection<WorkItem> workItems) {
		this.workItems = workItems;
	}

	public User(String userId, String userName, String firstName, String lastName) {
		this.userId = userId;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		workItems = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Team getTeam() {
		return team;
	}

	public Collection<WorkItem> getWorkItems() {
		return workItems;
	}

	public WorkItem addWorkItem(WorkItem workItem) {
		workItems.add(workItem);
		return workItem;
	}

	public Team setTeam(Team team) {
		this.team = team;
		return this.team;
	}
}
