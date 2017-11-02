package uk.ac.qub.csc3045.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class UserStoryService {

	private final UserStoryMapper mapper;
	
	@Autowired
	public UserStoryService(UserStoryMapper mapper) {
		this.mapper = mapper;
	}
		
	public UserStory create(UserStory userStory) {
		mapper.createUserStory(userStory);
		return mapper.getUserStoryById(userStory.getId());
	}
	public UserStory getUserStory(Long id) {
		if (ValidationUtility.validateUserStoryExists(id, mapper)) {
			return mapper.getUserStoryById(id);
		}
		throw new ResponseErrorException("User Story does not exist", HttpStatus.NOT_FOUND);
	}
	public List<UserStory> getAllUserStory(Long id) {
		if (ValidationUtility.validateUserStoryExists(id, mapper)) {
			return mapper.getUserStoryByProject(id);
		}
		throw new ResponseErrorException("User Story does not exist", HttpStatus.NOT_FOUND);
	}
}
