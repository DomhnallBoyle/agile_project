package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

import java.util.Comparator;
import java.util.List;

@Service
public class UserStoryService {

    private UserStoryMapper userStoryMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public UserStoryService(UserStoryMapper userStoryMapper, ProjectMapper projectMapper) {
        this.userStoryMapper = userStoryMapper;
        this.projectMapper = projectMapper;
    }

    public UserStory create(UserStory userStory) {
        if (ValidationUtility.validateProjectExists(userStory.getProject().getId(), projectMapper)) {
            long projectId = userStory.getProject().getId();
            List<UserStory> backlog = userStoryMapper.getUserStoriesByProject(projectId);
            userStory.setIndex(backlog.size());

            userStoryMapper.createUserStory(userStory);
            return userStoryMapper.getUserStoryById(userStory.getId());
        }
        throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }

    public UserStory getUserStory(Long id) {
        if (ValidationUtility.validateUserStoryExists(id, userStoryMapper)) {
            return userStoryMapper.getUserStoryById(id);
        }
        throw new ResponseErrorException("User Story does not exist", HttpStatus.NOT_FOUND);
    }

    public List<UserStory> getAllUserStories(Long id) {
        if (ValidationUtility.validateProjectExists(id, projectMapper)) {
            return userStoryMapper.getUserStoriesByProject(id);
        }
        throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }

    public List<UserStory> updateBacklogOrder(List<UserStory> backlog) {
        UserStory firstStory = backlog.get(0);
        long projectId = firstStory.getProject().getId();

        if (ValidationUtility.validateProjectExists(projectId, projectMapper)) {
            if (ValidationUtility.validateProjectContainsUserStory(firstStory.getId(), projectId, userStoryMapper)) {
                for (int i = 0; i < backlog.size(); i++) {
                    userStoryMapper.updateUserStoryIndex(backlog.get(i).getId(), i);
                }

                backlog = userStoryMapper.getUserStoriesByProject(backlog.get(0).getProject().getId());
                backlog.sort(Comparator.comparing(UserStory::getIndex));

                return backlog;
            }
            throw new ResponseErrorException("These User Stories don't exist on the given Project", HttpStatus.NOT_FOUND);
        }
        throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
    }
    
    public AcceptanceTest addAcceptanceTest(long id, AcceptanceTest acceptanceTest) {
    	try {
        	userStoryMapper.createAcceptanceTest(id, acceptanceTest);
        	
        	return userStoryMapper.getAcceptanceTestById(acceptanceTest.getId());
    	}
    	catch (DataIntegrityViolationException e) {
            throw new ResponseErrorException("The User story does not exist", HttpStatus.NOT_FOUND);
        }
    }
    
    public List<AcceptanceTest> getAcceptanceTests(long id) {
    	if (ValidationUtility.validateUserStoryExists(id, userStoryMapper))
        	return userStoryMapper.getAcceptanceTestsByStoryId(id);
    	throw new ResponseErrorException("The User story does not exist", HttpStatus.NOT_FOUND);
    }
   
    public AcceptanceTest updateAcceptanceTest(AcceptanceTest acceptanceTest) {
    	try {
    		if (ValidationUtility.validateAcceptanceTestExists(acceptanceTest.getId(), userStoryMapper)) {
            	userStoryMapper.updateAcceptanceTest(acceptanceTest);
            	
            	return userStoryMapper.getAcceptanceTestById(acceptanceTest.getId());
    		}
    		throw new ResponseErrorException("The acceptance test does not exist", HttpStatus.NOT_FOUND);
    	}
    	catch (DataIntegrityViolationException e) {
    		throw new ResponseErrorException("The User story does not exist", HttpStatus.NOT_FOUND);
    	}
    }
    
}
