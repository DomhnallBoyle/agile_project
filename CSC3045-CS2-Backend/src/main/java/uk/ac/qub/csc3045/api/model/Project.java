package uk.ac.qub.csc3045.api.model;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;
    
    @OneToOne
    @NotNull
    private User manager;
    
    @OneToOne
    @NotNull
    private User productOwner;

    @ManyToMany
    /* @JoinTable (name="Project_Team",joinColumns=@JoinColumn(name="project_id",referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="user_id",referencedColumnName="id"))*/
    @JoinTable
    private List<User> users;
    
    public Project() {
    	
    }
    
    public Project(String name, String description, User manager, User productOwner, List<User>users) {
    	this.name = name;
    	this.description = description;
    	this.manager = manager;
    	this.productOwner = productOwner;
    	this.users = users;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public User getProductOwner() {
		return productOwner;
	}
	public void setProductOwner(User productOwner) {
		this.productOwner = productOwner;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

}