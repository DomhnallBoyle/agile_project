package uk.ac.qub.csc3045.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class UserStory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String name;
	
	private String description;
	
	private Long points;
	
	private Long marketValue;
	
	private Boolean assigned;
	
	@ManyToOne
	private Project project;
	
	public UserStory() {
		
	}
	public UserStory(String name, String description,long points, long marketValue, Boolean assigned,
			Project project) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.marketValue = marketValue;
		this.assigned = assigned;
		this.project = project;
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public Long getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(Long marketValue) {
		this.marketValue = marketValue;
	}
	public Boolean getIsAssigned() {
		return assigned;
	}
	public void setIsAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	

}
