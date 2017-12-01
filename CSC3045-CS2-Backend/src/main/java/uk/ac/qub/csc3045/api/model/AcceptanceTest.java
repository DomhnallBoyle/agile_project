package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AcceptanceTest {
	
	/**
	 * Model for an Acceptance Test
	 */
	
	private Long id;
	
	@NotNull
	@Size(max=255)
	private String given, when, then;
	
	private Boolean completed = false;
	
	private UserStory userStory;
	
	public AcceptanceTest() {
		
	}
	
	public AcceptanceTest(String given, String when, String then) {
		this.given = given;
		this.when = when;
		this.then = then;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AcceptanceTest that = (AcceptanceTest) o;

		if (!given.equals(that.given)) return false;
		if (!when.equals(that.when)) return false;
		return then.equals(that.then);
	}
}
