package se.eldebabe.taskboard.data.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Table(name = "Team")
@Entity
public class Team extends AbstractEntity {
	
	@Column(name = "team_id")
	private String teamName;
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private Collection<User> users;
	
	public Team(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return teamName;
	}

}
