package uk.ac.qub.csc3045.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.utility.EmailUtility;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class SprintService {

	private SprintMapper sprintMapper;
	private ProjectMapper projectMapper;
	@Autowired
    private EmailUtility emailSender;

    @Autowired
    public SprintService(SprintMapper sprintMapper, ProjectMapper projectMapper) {
        this.sprintMapper = sprintMapper;
        this.projectMapper = projectMapper;
    }
    
    public Sprint createSprint(Sprint sprint) {
        try {
            sprintMapper.createSprint(sprint);

            Sprint newSprint = sprintMapper.getSprintById(sprint.getId());

            return newSprint;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("Scrum Master does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }
    
    public Sprint getSprint(long sprintId) {
        if (ValidationUtility.validateSprintExists(sprintId, sprintMapper)) {
            return sprintMapper.getSprintById(sprintId);
        }
        throw new ResponseErrorException("Sprint does not exist", HttpStatus.NOT_FOUND);
    }
    
	public List<Sprint> getProjectSprints(long projectId) {
        List<Sprint> sprints = sprintMapper.getProjectSprints(projectId);

        if (sprints.isEmpty()) {
            throw new ResponseErrorException("There are currently no sprints in this project", HttpStatus.NOT_FOUND);
        }

        return sprints;
    }
	
    public List<User> getSprintTeam(long sprintId) {
        if (ValidationUtility.validateSprintExists(sprintId, sprintMapper)) {
            return sprintMapper.getSprintTeam(sprintId);
        }
        throw new ResponseErrorException("Sprint does not exist", HttpStatus.NOT_FOUND);
    }

    public List<User> getAvailableDevelopers(long sprintId) {
        if (ValidationUtility.validateSprintExists(sprintId, sprintMapper)) {
            Sprint sprint = sprintMapper.getSprintById(sprintId);

            List<User> developers = projectMapper.getProjectDevelopers(sprint.getProject().getId());
            List<User> availableDevelopers = new ArrayList<>();

            for (User developer : developers) {
                List<Sprint> clashingSprints = sprintMapper.getClashingSprintsForUser(developer.getId(), sprint.getStartDate(), sprint.getEndDate());

                if (clashingSprints.isEmpty()) {
                    availableDevelopers.add(developer);
                }
            }

            if (availableDevelopers.isEmpty()) {
                throw new ResponseErrorException("There are no available developers for this sprint.", HttpStatus.NOT_FOUND);
            }

            return availableDevelopers;
        }

        throw new ResponseErrorException("Sprint does not exist", HttpStatus.NOT_FOUND);
    }

    public List<User> updateSprintTeam(Sprint sprint) {
        List<User> oldTeam = sprintMapper.getSprintTeam(sprint.getId());
        sprintMapper.resetSprintTeam(sprint.getId());

        for (User user : sprint.getUsers()) {
            sprintMapper.addToSprintTeam(sprint.getId(), user.getId());
        }

        List<User> newTeam = sprintMapper.getSprintTeam(sprint.getId());

        sendTeamMemberEmails(oldTeam, newTeam, sprint.getProject().getName());

        return newTeam;
    }

    private void sendTeamMemberEmails(List<User> oldTeam, List<User> newTeam, String projectName) {
    	if (newTeam != null) {
    		for (User teamMember : newTeam) {
    			if (!oldTeam.contains(teamMember)) {
    				 try {
    					 emailSender.sendSprintEmails(projectName, teamMember);
             		}catch( Exception e ){
             			// catch error
             			System.out.print("Error Sending Email: " + e.getMessage());
             		}
    			}
    		}
    	}
    }
   
}
