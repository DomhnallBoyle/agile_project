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

    private ProjectMapper mapper;
    
    @Autowired
    public ProjectService(ProjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public Project create(Project project) {
        mapper.createProject(project);

        return mapper.getProjectById(project.getId());
    }
    
    public Project addToTeam(Project project) {
       try {
    	   for (User user : project.getUsers()) {
               mapper.addToProjectTeam(project.getId(), user.getId());
           }
       }catch(DataIntegrityViolationException e) {
    	   throw new ResponseErrorException("Project or User does not exist", HttpStatus.NOT_FOUND);
       }
        return project;
    }
    
    public List<User> getTeamMembers(long projectId) {
       if(ValidationUtility.validateProjectExists(projectId, mapper)) {
    	   return mapper.getUsersOnProject(projectId);
    } 
       throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }     
       	
}
    
