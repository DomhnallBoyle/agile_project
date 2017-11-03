package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
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
		if(ValidationUtility.validateProjectExists(backlog.get(0).getProject().getId(), projectMapper)) {
			for (int i = 0; i < backlog.size(); i++) {
				userStoryMapper.updateUserStoryIndex(backlog.get(i).getId(), i);
			}
	
			backlog = userStoryMapper.getUserStoriesByProject(backlog.get(0).getProject().getId());
			backlog.sort(Comparator.comparing(UserStory::getIndex));
	
			return backlog;
		}
		throw new ResponseErrorException("Project for these user stories does not exist", HttpStatus.NOT_FOUND);
	}
}
