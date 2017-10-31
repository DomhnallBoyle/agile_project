package uk.ac.qub.csc3045.api.model;

import javax.persistence.GenerationType;

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
    private String projectName;

    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    private User projectManager;

    @ManyToOne
    private User productOwner;

    @ManyToMany
    private List<User> projectTeam;
    
    public Project() { }

    public Project(String projectName, String description, User projectManager, User productOwner) {
        this.projectName = projectName;
        this.description = description;
        this.projectManager = projectManager;
        this.productOwner = productOwner;
    }

    public Long getId() {
        return id;
    }

    public List<User> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(List<User> projectTeam) {
        this.projectTeam = projectTeam;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }

    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof Project))
            return false;
        
        Project project = ((Project)toCompare);
        project.setId(null);

        return this.projectName.equalsIgnoreCase(project.getProjectName());
    }
    
}