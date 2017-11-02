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
	private User productOwner;

	@ManyToMany
	@JoinTable(
			name = "PROJECT_USER", 
			joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"), 
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
	private List<User> users;

	public Project() {

	}

	public Project(String name, String description, User manager, User productOwner, List<User> users) {
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Project other = (Project) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager)) {
			return false;
		}
		
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description)) {
			return false;
		}
		
		if (productOwner == null) {
			if (other.productOwner != null)
				return false;
		} else if (!productOwner.equals(other.productOwner)) {
			return false;
		}
		
		if (users == null) {
			if (other.users != null)
				return false;
		} else {
			for (User user : users) {
				if (!other.users.contains(user)) {
					return false;
				}
			}
		}
		return true;
	}
}