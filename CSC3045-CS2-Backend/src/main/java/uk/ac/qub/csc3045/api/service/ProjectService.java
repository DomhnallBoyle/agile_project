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
		mapper.createProject(project);
		
		Project newProject = mapper.getProjectById(project.getId());
		
		if ( newProject.getProductOwner() != null) {
			EmailUtility.sendEmail(newProject.getProductOwner().getEmail(), "You Have been added as a Product Owner",
					"Hello "+newProject.getProductOwner().getForename()+
					" You are now the Product Owner for "+newProject.getName());
		}
		
		/*if ( newProject.getScrumMaster() != null) {
			
			EmailUtility.sendEmail(newProject.getScrumMaster().getEmail(), "You Have been added as a Scrum Master",
					"Hello "+newProject.getScrumMaster().getForename()+
					" You are now the Scrum Master for "+newProject.getName());
			
		}*/
		return mapper.getProjectById(project.getId());	
	}

	public Project update(Project project) {
		
		Project startingProject = mapper.getProjectById(project.getId());
		
		mapper.updateProject(project);
		
		project = mapper.getProjectById(project.getId());
		
		if ( !project.getProductOwner().equals(startingProject.getProductOwner())) {
			EmailUtility.sendEmail(project.getProductOwner().getEmail(), "You Have been added as a Product Owner",
					"Hello "+project.getProductOwner().getForename()+
					"You are now the Product Owner for "+project.getName());
			
		}
		/*if ( !project.getScrumMaster().equals(startingProject.getScrumMaster())) {
			EmailUtility.sendEmail(project.getScrumMaster().getEmail(), "You Have been added as a Scrum Master",
					"Hello "+project.getScrumMaster().getForename()+
					" You are now the Scrum Master for "+project.getScrumMaster().getForename());
			
		}*/
		return project;
	}

	public Project addToTeam(Project project) {
		try {
			for (User user : project.getUsers()) {
				mapper.addToProjectTeam(project.getId(), user.getId());
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
