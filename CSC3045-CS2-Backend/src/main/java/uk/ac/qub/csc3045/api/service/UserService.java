package uk.ac.qub.csc3045.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Skill;
import uk.ac.qub.csc3045.api.model.User;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User search(User searchedUser) {
    	User returnedUser = userMapper.findUserByEmail(searchedUser.getEmail());
    	
    	if (returnedUser != null) {
    		return returnedUser;
    	}
        throw new ResponseErrorException("User with the specified email does not exist", HttpStatus.NOT_FOUND);
    }
    
    public List<Project> getProjectsForUser(long userId) {
        List<Project> projects = userMapper.getProjectsForUser(userId);

        if (projects.isEmpty()) {
            throw new ResponseErrorException("You are currently not assigned to any projects.", HttpStatus.NOT_FOUND);
        }

        return projects;
    }
    
    public List<Skill> getUserSkills(long userId) {
    	List<Skill> userSkills = userMapper.getUserSkills(userId);
    	return userSkills;
    }
    
    public List<Skill> updateUserSkills(long userId, User user) {
    	userMapper.removeUserSkills(userId);
    	
    	for(Skill skill: user.getSkills()) {
    		userMapper.addUserSkill(userId, skill);
    	}
    	
    	return userMapper.getUserSkills(userId);
    }
}
