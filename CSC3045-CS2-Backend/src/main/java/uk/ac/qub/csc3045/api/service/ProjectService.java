package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.utility.EmailUtility;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

import java.util.List;

@Service
public class ProjectService {

    private ProjectMapper mapper;

    @Autowired
    public ProjectService(ProjectMapper mapper) {
        this.mapper = mapper;
    }

    public Project create(Project project) {
        try {
            mapper.createProject(project);

            Project newProject = mapper.getProjectById(project.getId());

            if (newProject.getProductOwner() != null) {
                EmailUtility.sendEmail(newProject.getProductOwner().getEmail(), "You Have been added as a Product Owner",
                        "Hello " + newProject.getProductOwner().getForename() +
                                " You are now the Product Owner for " + newProject.getName());
            }

            if (newProject.getScrumMaster() != null) {

                EmailUtility.sendEmail(newProject.getScrumMaster().getEmail(), "You Have been added as a Scrum Master",
                        "Hello " + newProject.getScrumMaster().getForename() +
                                " You are now the Scrum Master for " + newProject.getName());
            }

            return newProject;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("Project Manager does not exist in the database", HttpStatus.NOT_FOUND);
        }

    }

    public Project update(Project project) {
		try {
			if (ValidationUtility.validateProjectExists(project.getId(), mapper)) {
				Project oldProject = mapper.getProjectById(project.getId());

				mapper.updateProject(project);

				Project updatedProject = mapper.getProjectById(project.getId());

				if (updatedProject.getProductOwner() != null
						&& !updatedProject.getProductOwner().equals(oldProject.getProductOwner())) {
					EmailUtility.sendEmail(updatedProject.getProductOwner().getEmail(),
							"You Have been added as a Product Owner",
							"Hello " + updatedProject.getProductOwner().getForename()
									+ "You are now the Product Owner for " + updatedProject.getName());

				}
				if (updatedProject.getScrumMaster() != null
						&& !updatedProject.getScrumMaster().equals(oldProject.getScrumMaster())) {
					EmailUtility.sendEmail(updatedProject.getScrumMaster().getEmail(),
							"You Have been added as a Scrum Master",
							"Hello " + updatedProject.getScrumMaster().getForename()
									+ " You are now the Scrum Master for "
									+ updatedProject.getScrumMaster().getForename());

				}
				return updatedProject;
			}
			throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseErrorException("User for Product Owner or Scrum Master does not exist", HttpStatus.NOT_FOUND);
		}
		
	}

    public Project get(long projectId) {
        if (ValidationUtility.validateProjectExists(projectId, mapper)) {
            return mapper.getProjectById(projectId);
        }
        throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }

    public List<Project> getProjectsForUser(long userId) {
        List<Project> projects = mapper.getProjectsForUser(userId);

        if (projects.isEmpty()) {
            throw new ResponseErrorException("You are currently not assigned to any projects.", HttpStatus.NOT_FOUND);
        }

        return projects;
    }

    public Project addToTeam(Project project) {
        try {
            for (User user : project.getUsers()) {
                mapper.addToProjectTeam(project.getId(), user.getId());

                EmailUtility.sendEmail(user.getEmail(), "You have been added to a new Project - " + project.getName(),
                        "Hello " + user.getForename() +
                                ", \n\nYou have been added to the Project Team for " + project.getName());
            }
        } catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("Project or User does not exist", HttpStatus.NOT_FOUND);
        }
        return project;
    }

    public List<User> getTeamMembers(long projectId) {
        if (ValidationUtility.validateProjectExists(projectId, mapper)) {
            return mapper.getUsersOnProject(projectId);
        }
        throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }

}
