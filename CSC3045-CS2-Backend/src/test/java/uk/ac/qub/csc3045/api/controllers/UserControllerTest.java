package uk.ac.qub.csc3045.api.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.ac.qub.csc3045.api.controller.UserController;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private User user;
    private long userId;
    private List<Skill> skills;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        userId = 1l;
        skills = setUpSkills();
        user = new User("Richard", "Hendrix", "r.hendrix@valley.com", new Roles(true, true, false), skills);
    }
    
    private List<Skill> setUpSkills() {
    	List<Skill> skills = new ArrayList<Skill>();
    	for (int i=0; i<5; i++) {
    		skills.add(new Skill("skill " + i, userId));
    	}
    	return skills;
    }

    @Test
    public void searchingUsersShouldReturn200OnSuccess() {
        User searchCriteria = new User();

        ResponseEntity response = userController.search(searchCriteria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void getUserSkillsShouldReturn200OnSuccess() {
    	when(userService.getUserSkills(userId).thenReturn(skills));
    	
    	ResponseEntity response = userController.getUserSkills(userId);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(response.getBody(), skills);
    }
    
    @Test
    public void updateUserSkillsShouldReturn200OnSuccess() {
    	when(userService.updateUserSkills(userId).thenReturn(skills));
    	
    	ResponseEntity response = userController.updateUserSkills(userId, user);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(response.getBody(), skills);
    }
}
