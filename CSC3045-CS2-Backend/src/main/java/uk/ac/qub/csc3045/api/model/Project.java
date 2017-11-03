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

    @OneToOne
    private User scrumMaster;

	@ManyToMany
	@JoinTable(
			name = "PROJECT_USER", 
			joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"), 
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
	private List<User> users;

    @OneToMany(mappedBy = "project")
    private List<UserStory>userStories;

	public Project() {

	}

	public Project(long id) {
		this.id = id;
	}

	public Project(String name, String description, User manager, User productOwner, User scrumMaster, List<User> users, List<UserStory> userStories) {
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.productOwner = productOwner;
        this.scrumMaster = scrumMaster;
		this.users = users;
        this.userStories = userStories;
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

    public User getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(User scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public List<UserStory> getUserStories() {
		return userStories;
	}

	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
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
		
		if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
		
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
		
		if (manager == null) {
			if (other.manager != null) {
				return false;
			}
		} else if (!manager.equals(other.manager)) {
			return false;
		}

		if (productOwner == null) {
			if (other.productOwner != null) {
				return false;
			}
		} else if (!productOwner.equals(other.productOwner)) {
			return false;
		}
		
		if (scrumMaster == null) {
            if (other.scrumMaster != null) {
                return false;
            }
        } else if (!scrumMaster.equals(other.scrumMaster)) {
            return false;
        }
		
		if (users == null) {
			if (other.users != null) {
				return false;
			}
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