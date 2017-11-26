package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
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
    private EmailUtility emailSender;

    @Autowired
    public ProjectService(ProjectMapper mapper, EmailUtility emailSender) {
        this.mapper = mapper;
        this.emailSender = emailSender;
    }

    public Project create(Project project) {
        try {
            mapper.createProject(project);

            Project newProject = mapper.getProjectById(project.getId());

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

                if (project.getScrumMasters() != null) {
                    updateScrumMasters(oldProject, project);
                }

                Project updatedProject = mapper.getProjectById(project.getId());

                sendProductOwnerEmail(oldProject, updatedProject);
                sendScrumMasterEmails(oldProject, updatedProject);

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
        	Project oldProject = mapper.getProjectById(project.getId());
        	
            for (User user : project.getUsers()) {
                mapper.addToProjectTeam(project.getId(), user.getId());
            }
            
            Project updatedProject = mapper.getProjectById(project.getId());
            
            sendTeamMemberEmails(oldProject, updatedProject);
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

    private void updateScrumMasters(Project oldProject, Project inboundProject) {
        for (User user : oldProject.getScrumMasters()) {
            mapper.setUserAsScrumMaster(oldProject.getId(), user.getId(), false);
        }

        for (User user : inboundProject.getScrumMasters()) {
            mapper.setUserAsScrumMaster(inboundProject.getId(), user.getId(), true);
        }
    }

    private void sendProductOwnerEmail(Project oldProject, Project updatedProject) {
        if (updatedProject.getProductOwner() != null
                && !updatedProject.getProductOwner().equals(oldProject.getProductOwner())) {
        	try {
        		emailSender.sendProductOwnerEmail(updatedProject);
    		}catch( Exception e ){
    			// catch error
    			System.out.print("Error Sending Email: " + e.getMessage());
    		}
        }
    }

    private void sendScrumMasterEmails(Project oldProject, Project updatedProject) {
        if (updatedProject.getScrumMasters() != null) {
            for (User scrumMaster : updatedProject.getScrumMasters()) {
                if (!oldProject.getScrumMasters().contains(scrumMaster)) {               
                    try {
                    	emailSender.sendScrumMasterEmails(updatedProject,scrumMaster);
            		}catch( Exception e ){
            			// catch error
            			System.out.print("Error Sending Email: " + e.getMessage());
            		}
                }
            }
        }
    }
    
    private void sendTeamMemberEmails(Project oldProject, Project updatedProject) {
    	if (updatedProject.getUsers() != null) {
    		for (User teamMember : updatedProject.getUsers()) {
    			if (!oldProject.getUsers().contains(teamMember)) {
    				try {
    					emailSender.sendTeamMemberEmails(updatedProject,teamMember);
            		}catch( Exception e ){
            			// catch error
            			System.out.print("Error Sending Email: " + e.getMessage());
            		}
    			}
    		}
    	}
    }
}
