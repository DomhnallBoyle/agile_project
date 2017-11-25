package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;

public class AcceptanceTest {
	
	private Long id;
	
	@NotNull
	private String given, when, then;
	
	private Boolean completed = false;
	
	private UserStory userStory;
	
	public AcceptanceTest() {
		
	}
	
	public AcceptanceTest(String given, String when, String then, Boolean completed, UserStory userStory) {
		this.given = given;
		this.when = when;
		this.then = then;
		this.completed = completed;
		this.userStory = userStory;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGiven() {
		return given;
	}
	
	public void setGiven(String given) {
		this.given = given;
	}
	
	public String getWhen() {
		return when;
	}
	
	public void setWhen(String when) {
		this.when = when;
	}
	
	public String getThen() {
		return then;
	}
	
	public void setThen(String then) {
		this.then = then;
	}
	
	public Boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public UserStory getUserStory() {
		return userStory;
	}
	
	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}
	
}
