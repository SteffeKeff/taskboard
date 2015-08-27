package se.eldebabe.taskboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

}
