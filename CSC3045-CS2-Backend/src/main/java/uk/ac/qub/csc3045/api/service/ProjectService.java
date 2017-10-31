package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public Project addToTeam(Project project) {
//        if (ValidationUtility.validateProject(project, mapper)) {
            for (User user : project.getProjectTeam()) {
                mapper.addToProjectTeam(user);
            }
//        }
        
        return project;
    }
    
    public List<User> getTeamMembers(Project project) {
        return mapper.getProjectTeam(project);
    }
    
}