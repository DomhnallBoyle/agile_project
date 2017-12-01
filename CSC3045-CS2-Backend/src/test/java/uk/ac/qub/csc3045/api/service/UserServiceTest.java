package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.constraints.AssertTrue;

public class UserServiceTest {

    private UserService userService;
    private UserMapper userMapper;

    private User user;

    @Before
    public void setUp() {
    	user = new User("Ragnar", "Lothbrok", "ragnar.lothbrok@valhalla.odin", new Roles(true, true, false));
    	userMapper = mock(UserMapper.class);
        userService = new UserService(userMapper);
    }

    @Test
    public void searchForExistingUserShouldReturnUser() {
    	when(userMapper.findUserByEmail(user.getEmail())).thenReturn(user);
    	
    	User returnedUser = userService.search(user);
    	
    	assertEquals(returnedUser, user);
    }
    
    @Test()
    public void searchForNonExistingUserShouldThrowException() {
    	when(userMapper.findUserByEmail(user.getEmail())).thenReturn(null);
    	try {
    		userService.search(user);
    	}
    	catch(ResponseErrorException e) {
    		assertTrue(e.getMessage() == "User with the specified email does not exist");
    	}
    }
}