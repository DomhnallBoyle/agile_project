package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    
    @Test(expected = ResponseErrorException.class)
    public void searchForNonExistingUserShouldThrowException() {
    	when(userMapper.findUserByEmail(user.getEmail())).thenReturn(null);
    	
    	userService.search(user);
    	
    	fail();
    }
}