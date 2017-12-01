package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Skill;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.UserService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	/**
	 * Private variables
	 */
    private final UserService userService;

    /**
     * Constructor for the User controller
     * @param userService - service for the controller
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for searching for a user
     * @param user object from the request body
     * @return user object returned from the database
     */
    @PostMapping(value = "/search")
    public ResponseEntity<User> search(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.search(user), HttpStatus.OK);
    }
    
    /**
     * Endpoint to retrieve a list of projects for the user
     * @param userId id of the user to return the projects for
     * @return list of projects
     */
    @GetMapping(value = "/{userId}/project")
    public ResponseEntity<List<Project>> getProjectsForUser(@Valid @PathVariable("userId") long userId) {
        return new ResponseEntity<>(this.userService.getProjectsForUser(userId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{userId}/skill")
    public ResponseEntity<List<Skill>> getUserSkills(@PathVariable("userId") long userId) {
    	return new ResponseEntity<>(this.userService.getUserSkills(userId), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{userId}/skill")
    public ResponseEntity<List<Skill>> updateUserSkills(@PathVariable("userId") long userId, @Valid @RequestBody User user) {
    	return new ResponseEntity<>(this.userService.updateUserSkills(userId, user), HttpStatus.OK);
    }
}
