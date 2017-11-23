package uk.ac.qub.csc3045.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Sprint {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ssZ")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ssZ")
    private Date endDate;

    private Project project;

    private User scrumMaster;

    private List<User> users;

    public Sprint() {

    }

    public Sprint(Long id, Date startDate, Date endDate, Project project, User scrumMaster, List<User> users) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprint sprint = (Sprint) o;

        if (id != null ? !id.equals(sprint.id) : sprint.id != null) return false;
        if (startDate != null ? !startDate.equals(sprint.startDate) : sprint.startDate != null) return false;
        return endDate != null ? endDate.equals(sprint.endDate) : sprint.endDate == null;
    }
}
