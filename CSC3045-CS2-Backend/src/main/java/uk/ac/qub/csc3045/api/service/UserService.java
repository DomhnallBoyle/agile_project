package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
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
}
