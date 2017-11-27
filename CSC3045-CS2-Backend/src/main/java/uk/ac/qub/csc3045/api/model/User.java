package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class User {

    private Long id;

    @NotNull
    private String forename;

    @NotNull
    private String surname;

    @NotNull
    private String email;
    
    private String profilePicture;

    private Roles roles = new Roles();

    private List<Project> projects;

    private List<Sprint> sprints;

    public User() {
    }

    public User(String forename, String surname, String email, Roles roles) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
    }
    
    public User(String forename, String surname, String email, String profilePicture, Roles roles) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.profilePicture = profilePicture;
        this.roles = roles;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForename() {
    	return forename == null ? forename : forename.trim();
    }

    public void setForename(String forename) {
        this.forename = forename == null ? forename : forename.trim();
    }

    public String getSurname() {
    	return surname == null ? surname : surname.trim();
    }

    public void setSurname(String surname) {
        this.surname = surname == null ? surname : surname.trim();
    }

    public String getEmail() {
    	return email == null ? email : email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email == null ? email : email.toLowerCase();
    }
    
    public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
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

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (forename == null) {
			if (other.forename != null)
				return false;
		} else if (!forename.equals(other.forename))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (profilePicture == null) {
			if (other.profilePicture != null)
				return false;
		} else if (!profilePicture.equals(other.profilePicture))
			return false;
		if (projects == null) {
			if (other.projects != null)
				return false;
		} else if (!projects.equals(other.projects))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (sprints == null) {
			if (other.sprints != null)
				return false;
		} else if (!sprints.equals(other.sprints))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}


}
