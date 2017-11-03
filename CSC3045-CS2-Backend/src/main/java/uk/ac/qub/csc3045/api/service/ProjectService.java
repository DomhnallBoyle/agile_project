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
import uk.ac.qub.csc3045.api.utility.EmailUtility;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

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
			return mapper.getProjectById(project.getId());
			
		} catch (DataIntegrityViolationException e) {
			throw new ResponseErrorException("Project or User does not exist", HttpStatus.NOT_FOUND);
		}
		
	}

	public Project update(Project project) {
		if (ValidationUtility.validateProjectExists(project.getId(), mapper)) {
			mapper.updateProject(project);
			return mapper.getProjectById(project.getId());
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
	}
	
	public Project get(long projectId) {
		if (ValidationUtility.validateProjectExists(projectId, mapper)) {
			return mapper.getProjectById(projectId);
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
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
		throw new ResponseErrorException("Project with specified ID does not exist", HttpStatus.NOT_FOUND);
	}

}
