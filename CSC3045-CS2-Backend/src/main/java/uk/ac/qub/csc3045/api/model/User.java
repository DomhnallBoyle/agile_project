package uk.ac.qub.csc3045.api.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String forename;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @OneToOne
    private Roles roles = new Roles();
    @ManyToMany(mappedBy="users")
    private List<Project>projects;

	public User() {
    }

    public User(String forename, String surname, String email, Roles roles) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	 public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public boolean equals(Object toCompare) {
		if (!(toCompare instanceof User))
			return false;
		
		User user = ((User)toCompare);
		user.setId(null);
		
		if (this.forename.equalsIgnoreCase(user.getForename())
			&& this.surname.equalsIgnoreCase(user.getSurname())
			&& this.email.equalsIgnoreCase(user.getEmail()) 
			&& this.roles.equals(user.getRoles())) {
			return true;
		}
		return false;
	}
}
