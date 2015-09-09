package se.eldebabe.taskboard.data.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass
public abstract class AbstractEntity{

	@Id
	@GeneratedValue
	private Long id;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	protected AbstractEntity(){
	}
	
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//	}
}