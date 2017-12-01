package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.csc3045.api.model.Skill;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

public class UserServiceTest {

    private UserService userService;
    private UserMapper userMapper;
    private User user;
    private List<Skill> skills;
    private long userId;

    @Before
    public void setUp() {
    	userId = 1l;
    	skills = setUpSkills();
    	user = new User("Ragnar", "Lothbrok", "ragnar.lothbrok@valhalla.odin", new Roles(true, true, false), skills);
    	userMapper = mock(UserMapper.class);
        userService = new UserService(userMapper);
    }
    
    private List<Skill> setUpSkills() {
    	List<Skill> skills = new ArrayList<Skill>();
    	for (int i=0; i<5; i++) {
    		skills.add(new Skill("skill " + i, user));
    	}
    	return skills;
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
    
    @Test
    public void getUserSkillsShouldReturn200() {
    	when(userMapper.getUserSkills(userId)).thenReturn(skills);
    	
    	List<Skill> returnedSkills = userService.getUserSkills(userId);
    	
    	assertEquals(skills, returnedSkills);
    }
    
    @Test
    public void updateUserSkillsShouldReturn200() {
    	doNothing().when(userMapper).removeUserSkills(userId);
    	doNothing().when(userMapper).addUserSkill(eq(userId), any(Skill.class));
    	when(userMapper.getUserSkills(userId)).thenReturn(skills);
    	
    	List<Skill> returnedSkills = userService.updateUserSkills(userId, user);
    	
    	assertEquals(skills, returnedSkills);
    }
}