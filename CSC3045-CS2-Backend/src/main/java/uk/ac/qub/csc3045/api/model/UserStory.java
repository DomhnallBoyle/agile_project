package uk.ac.qub.csc3045.api.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserStory {

    private Long id;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    @Size(max=50)
    private String description;

    private Integer points;

    private Integer marketValue;

    private int index;

    private Project project;
    
    private List<AcceptanceTest> acceptanceTests;

    private Sprint sprint;

    private List<Task> tasks;

    public UserStory() {

    }

    public UserStory(String name, String description, Integer points, Integer marketValue,
                     Project project) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.marketValue = marketValue;
        this.project = project;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Integer marketValue) {
        this.marketValue = marketValue;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public List<AcceptanceTest> getAcceptanceTests() {
    	return acceptanceTests;
    }
    
    public void setAcceptanceTests(List<AcceptanceTest> acceptanceTests) {
    	this.acceptanceTests = acceptanceTests;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

        UserStory other = (UserStory) obj;

        if (id == null) {
            if (other.name != null) {
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

        if (points == null) {
            if (other.points != null) {
                return false;
            }
        } else if (!points.equals(other.points)) {
            return false;
        }

        if (marketValue == null) {
            if (other.marketValue != null) {
                return false;
            }
        } else if (!marketValue.equals(other.marketValue)) {
            return false;
        }

        return true;
    }
}