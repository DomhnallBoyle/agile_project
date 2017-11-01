package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;

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
//        if (ValidationUtility.validateProject(project, mapper)) {
            for (User user : project.getUsers()) {
                mapper.addToProjectTeam(project.getId(), user.getId());
            }
//        }
        //org.springframework.dao.DataIntegrityViolationException
        return project;
    }
    
    public List<User> getTeamMembers(long projectId) {
        List<User> kek = mapper.getUsersOnProject(projectId);
        return kek;
    }
    
}