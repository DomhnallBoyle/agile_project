package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.utility.EmailUtility;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class SprintService {

	private SprintMapper mapper;

    @Autowired
    public SprintService(SprintMapper mapper) {
        this.mapper = mapper;
    }
    
    public Sprint get(long sprintId) {
        if (ValidationUtility.validateSprintExists(sprintId, mapper)) {
            return mapper.getSprintById(sprintId);
        }
        throw new ResponseErrorException("Sprint does not exist", HttpStatus.NOT_FOUND);
    }
    
    public Sprint create(Sprint sprint) {
        try {
            mapper.createSprint(sprint);

            Sprint newSprint= mapper.getSprintById(sprint.getId());

            return newSprint;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("Scrum Master does not exist in the database", HttpStatus.NOT_FOUND);
        }

    }
    
    public Sprint addToTeam(Sprint sprint) {
        try {
        	Sprint oldSprint = mapper.getSprintById(sprint.getId());
        	
            for (User user : sprint.getUsers()) {
                mapper.addToSprintTeam(sprint.getId(), user.getId());
            }
            
            Sprint updatedSprint = mapper.getSprintById(sprint.getId());
            
            sendTeamMemberEmails(oldSprint, updatedSprint);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("Sprint or User does not exist", HttpStatus.NOT_FOUND);
        }
        return sprint;
    }
    
	public List<Sprint> getSprintsInProject(long projectId) {
        List<Sprint> sprints = mapper.getSprintsInProject(projectId);

        if (sprints.isEmpty()) {
            throw new ResponseErrorException("There are currently no sprints in this project", HttpStatus.NOT_FOUND);
        }

        return sprints;
    }
	
    public List<User> getSprintTeamMembers(long sprintId) {
        if (ValidationUtility.validateSprintExists(sprintId, mapper)) {
            return mapper.getUsersOnSprint(sprintId);
        }
        throw new ResponseErrorException("Sprint does not exist", HttpStatus.NOT_FOUND);
    }

    private void sendTeamMemberEmails(Sprint oldSprint, Sprint updatedSprint) {
    	if (updatedSprint.getUsers() != null) {
    		for (User teamMember : updatedSprint.getUsers()) {
    			if (!oldSprint.getUsers().contains(teamMember)) {
    				EmailUtility.sendEmail(teamMember.getEmail(), "You have been added to a new Sprint",
    		                "Hello " + teamMember.getForename() +
    		                        ",\n\nYou have been added to a sprint for the Project " + updatedSprint.getProject().getName() + ".");
    			}
    		}
    	}
    }
   
}
