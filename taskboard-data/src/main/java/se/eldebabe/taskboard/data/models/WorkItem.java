package se.eldebabe.taskboard.data.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "work_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkItem extends AbstractEntity {

	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate;
	
	@Column(name = "modified_date")
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "completed")
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Issue issue;

	protected WorkItem() {
	}

	public WorkItem(String title, String description) {
		status = Status.NOT_STARTED;
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setCompleted(Status status) {
		this.status = status;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	public void complete(){
		status = Status.COMPLETED;
	}
	
	public void incomplete(){
		status = Status.IN_PROGRESS;
	}
	public void notStarted(){
		status = Status.NOT_STARTED;
	}

}
