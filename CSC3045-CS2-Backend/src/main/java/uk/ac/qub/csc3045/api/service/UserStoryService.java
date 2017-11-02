package uk.ac.qub.csc3045.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.UserStory;

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
		return mapper.getUserStoryById(id);
	}
	public List<UserStory> getAllUserStory(Long id) {
		return mapper.getUserStoryByProject(id);
	}
}
