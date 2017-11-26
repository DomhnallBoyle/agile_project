
package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.service.UserStoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/story")
public class UserStoryController {

    private final UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping()
    public ResponseEntity<UserStory> create(@Valid @RequestBody UserStory userStory) {
        return new ResponseEntity<>(this.userStoryService.create(userStory), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserStory> getUserStory(@PathVariable("id") long id) {

        return new ResponseEntity<>(this.userStoryService.getUserStory(id), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{id}")
    public ResponseEntity<List<UserStory>> getUserStoriesByProject(@PathVariable("id") long id) {

        return new ResponseEntity<>(this.userStoryService.getAllUserStories(id), HttpStatus.OK);
    }

    @PutMapping(value = "/backlog/order")
    public ResponseEntity<List<UserStory>> updateBacklogOrder(@RequestBody List<UserStory> backlog) {
        return new ResponseEntity<>(userStoryService.updateBacklogOrder(backlog), HttpStatus.OK);
    }
    
    @PostMapping(value = "/{id}/acceptancetest")
    public ResponseEntity<AcceptanceTest> addAcceptanceTest(@Valid @PathVariable("id") long id, @Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.addAcceptanceTest(id, acceptanceTest), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}/acceptancetest")
    public ResponseEntity<List<AcceptanceTest>> getAcceptanceTests(@Valid @PathVariable("id") long id) {
    	return new ResponseEntity<>(this.userStoryService.getAcceptanceTests(id), HttpStatus.OK);
    }
    
    @PutMapping(value = "/acceptancetest")
    public ResponseEntity<AcceptanceTest> updateAcceptanceTest(@Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.updateAcceptanceTest(acceptanceTest), HttpStatus.OK);
    }
    
    @GetMapping(value = "/project/{id}/unassigned")
    public ResponseEntity<List<UserStory>> getAvailableUserStories(@Valid @PathVariable("id") long id) {
        return new ResponseEntity<>(this.userStoryService.getAvailableUserStories(id), HttpStatus.OK);
	}

}