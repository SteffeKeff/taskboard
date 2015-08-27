package se.eldebabe.taskboard.data.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Table(name = "teams")
@Entity
public class Team extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private Collection<User> users;
	
	protected Team() {
	}
	
	public Team(String teamName) {
		this.name = teamName;
	}

	public String getName() {
		return name;
	}
	
	public User addUser(User user){
		users.add(user);
		return user;
	}

}
