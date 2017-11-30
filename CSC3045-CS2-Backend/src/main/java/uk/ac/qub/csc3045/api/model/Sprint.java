package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class Sprint {
    private Long id;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private Project project;

    @NotNull
    private User scrumMaster;

    private List<User> users;
    
    private List<UserStory> userStories;

    public Sprint() {

    }

    public Sprint(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprint sprint = (Sprint) o;

        if (id != null ? !id.equals(sprint.id) : sprint.id != null) return false;
        if (name != null ? !name.equals(sprint.name) : sprint.name != null) return false;
        if (startDate != null ? !startDate.equals(sprint.startDate) : sprint.startDate != null) return false;
        return endDate != null ? endDate.equals(sprint.endDate) : sprint.endDate == null;
    }
}
