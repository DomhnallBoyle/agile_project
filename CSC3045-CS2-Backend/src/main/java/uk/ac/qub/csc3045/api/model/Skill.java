package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;

public class Skill {

    private Long id;
	
	@NotNull
	private String description;
	
	@NotNull
	private User user;
	
	public Skill() {
		
	}
	
	public Skill(String description, User user) {
		this.description = description;
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
