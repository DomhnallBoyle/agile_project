package uk.ac.qub.csc3045.api.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Project {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private User manager;

    private User productOwner;

    private List<User> scrumMasters;

    private List<User> users;

    private List<UserStory> userStories;

    public Project() {

    }

    public Project(long id) {
        this.id = id;
    }

    public Project(String name, String description, User manager, User productOwner, List<User> scrumMasters, List<User> users, List<UserStory> userStories) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.productOwner = productOwner;
        this.scrumMasters = scrumMasters;
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

    public List<User> getScrumMasters() {
        return scrumMasters;
    }

    public void setScrumMasters(List<User> scrumMasters) {
        this.scrumMasters = scrumMasters;
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

        if (scrumMasters == null) {
            if (other.scrumMasters != null) {
                return false;
            }
        } else if (!scrumMasters.equals(other.scrumMasters)) {
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