package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.UserService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/search")
    public ResponseEntity<User> search(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.search(user), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{userId}/project")
    public ResponseEntity<List<Project>> getProjectsForUser(@Valid @PathVariable("userId") long userId) {
        return new ResponseEntity<>(this.userService.getProjectsForUser(userId), HttpStatus.OK);
    }
}
