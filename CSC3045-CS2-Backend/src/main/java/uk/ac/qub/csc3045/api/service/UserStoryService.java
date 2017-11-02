package uk.ac.qub.csc3045.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class UserStoryService {

	private final UserStoryMapper mapper;
	private final ProjectMapper projectMapper;
	
	@Autowired
	public UserStoryService(UserStoryMapper mapper, ProjectMapper projectMapper) {
		this.mapper = mapper;
		this.projectMapper = projectMapper;
	}
		
	public UserStory create(UserStory userStory) {
		if (ValidationUtility.validateProjectExists(userStory.getProject().getId(), projectMapper)) {
			mapper.createUserStory(userStory);
			return mapper.getUserStoryById(userStory.getId());
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
	}
	public UserStory getUserStory(Long id) {
		if (ValidationUtility.validateUserStoryExists(id, mapper)) {
			return mapper.getUserStoryById(id);
		}
		throw new ResponseErrorException("User Story does not exist", HttpStatus.NOT_FOUND);
	}
	public List<UserStory> getAllUserStories(Long id) {
		if (ValidationUtility.validateProjectExists(id, projectMapper)) {
			return mapper.getUserStoriesByProject(id);
		}
		throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
	}
}
