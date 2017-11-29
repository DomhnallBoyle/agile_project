package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;

public class Task {
	
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer initialEstimate;
    @NotNull
    private UserStory userStory;

    private User assignee;
    
    public Task() {
    	
    }

    public Task(String name, String description, Integer initialEstimate, UserStory userStory, User assignee) {

        this.name = name;
        this.description = description;
        this.initialEstimate = initialEstimate;
        this.userStory = userStory;
        this.assignee = assignee;
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

    public Integer getInitialEstimate() {
        return initialEstimate;
    }

    public void setInitialEstimate(Integer initialEstimate) {
        this.initialEstimate = initialEstimate;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        return description != null ? description.equals(task.description) : task.description == null;
    }
}
