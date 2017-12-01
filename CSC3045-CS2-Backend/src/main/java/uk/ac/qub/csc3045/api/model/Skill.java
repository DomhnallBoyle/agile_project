package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;

public class Skill {

    private Long id;
	
	@NotNull
	private String description;
	
	public Skill() {
		
	}
	
	public Skill(String description) {
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
