package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class ProjectService {

	private ProjectMapper projectMapper;

	@Autowired
	public ProjectService(ProjectMapper  projectMapper) {
		this.projectMapper =  projectMapper;
	}

	public Project create(Project project) {
		projectMapper.createProject(project);

		return projectMapper.getProjectById(project.getId());
	}

	public Project update(Project project) {
		if (ValidationUtility.validateProjectExists(project.getId(), projectMapper)) {
			projectMapper.updateProject(project);
			return projectMapper.getProjectById(project.getId());
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
	}
	
	public Project get(long projectId) {
		if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
			return projectMapper.getProjectById(projectId);
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
	}

	public List<Project> getProjectsForUser(long userId) {
	    List<Project> projects = projectMapper.getProjectsForUser(userId);

	    if (projects.isEmpty()) {
	        throw new ResponseErrorException("You are currently not assigned to any projects.", HttpStatus.NOT_FOUND);
        }

        return projects;
	}

	public Project addToTeam(Project project) {
		try {
			for (User user : project.getUsers()) {
				projectMapper.addToProjectTeam(project.getId(), user.getId());
			}
		} catch (DataIntegrityViolationException e) {
			throw new ResponseErrorException("Project or User does not exist", HttpStatus.NOT_FOUND);
		}
		return project;
	}

	public List<User> getTeamMembers(long projectId) {
		if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
			return projectMapper.getUsersOnProject(projectId);
		}
		throw new ResponseErrorException("Project with specified ID does not exist", HttpStatus.NOT_FOUND);
	}

}
