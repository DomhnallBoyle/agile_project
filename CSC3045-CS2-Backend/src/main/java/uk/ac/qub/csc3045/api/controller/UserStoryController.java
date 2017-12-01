package uk.ac.qub.csc3045.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.service.UserStoryService;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(value = "/project/{projectId}/story")
public class UserStoryController {
	
	/**
	 * Private variables
	 */
    private final UserStoryService userStoryService;
    
    /**
     * Constructor for the controller
     * @param userStoryService - service for the controller
     */
    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    /**
     * Endpoint for updating the user story
     * @param userStory object from the request
     * @param storyId id of the story
     * @return updated user story
     */
    @PutMapping(value="/{storyId}")
    public ResponseEntity<UserStory> update(@Valid @RequestBody UserStory userStory, @PathVariable("storyId") long storyId) {
    	return new ResponseEntity<>(this.userStoryService.updateUserStory(userStory, storyId), HttpStatus.OK);
    }

    /**
     * Endpoint for getting user stories by project
     * @param projectId id of the project to get the user stories from
     * @return list of user storys that belong to that project
     */
    @GetMapping()
    public ResponseEntity<List<UserStory>> getUserStoriesByProject(@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.userStoryService.getAllUserStories(projectId), HttpStatus.OK);
    }
    
    /**
     * Endpoint for updating the order of a backlog
     * @param backlog list of user stories to be updated
     * @return updated list of user stories
     */
    @PutMapping()
    public ResponseEntity<List<UserStory>> updateBacklogOrder(@RequestBody List<UserStory> backlog) {
        return new ResponseEntity<>(userStoryService.updateBacklogOrder(backlog), HttpStatus.OK);
    }
    
    /**
     * Endpoint for creating a user story
     * @param projectId id of the project that the user story belongs to
     * @param userStory object from the request body
     * @return created user story from the database
     */
    @PostMapping()
    public ResponseEntity<UserStory> create(@PathVariable("projectId") long projectId, @Valid @RequestBody UserStory userStory) {
    	if(userStory.getProject().getId() != null && (projectId != userStory.getProject().getId())) {
    		throw new ResponseErrorException("Id in URL and body do not match", HttpStatus.BAD_REQUEST);
    	}
    	
        return new ResponseEntity<>(this.userStoryService.create(userStory), HttpStatus.CREATED);
    }
    
    /**
     * Endpoint for getting a particular user story
     * @param projectId id of the project to retrieve the user story from
     * @param storyId id of the story being retrieved
     * @return the user story from the database
     */
    @GetMapping(value = "/{storyId}")
    public ResponseEntity<UserStory> getUserStory(@PathVariable("projectId") long projectId, @PathVariable("storyId") long storyId) {
        return new ResponseEntity<>(this.userStoryService.getUserStory(projectId, storyId), HttpStatus.OK);
    }
    
    /**
     * Endpoint to add an acceptance test to a particular user story
     * @param projectId id of the project the user story belongs to
     * @param storyId id of the user story that the acceptance test belongs to
     * @param acceptanceTest object from the request body
     * @return created acceptance test object from the database
     */
    @PostMapping(value = "/{storyId}/acceptancetest")
    public ResponseEntity<AcceptanceTest> addAcceptanceTest(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId, @Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.addAcceptanceTest(projectId, storyId, acceptanceTest), HttpStatus.CREATED);
    }
    
    /**
     * Endpoint to retrieve the acceptance tests for a particular user story
     * @param projectId id of the project in which the user story belongs to
     * @param storyId id of the user story that the acceptance tests belong to
     * @return the list of acceptance tests from the user story
     */
    @GetMapping(value = "/{storyId}/acceptancetest")
    public ResponseEntity<List<AcceptanceTest>> getAcceptanceTests(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId) {
    	return new ResponseEntity<>(this.userStoryService.getAcceptanceTests(projectId, storyId), HttpStatus.OK);
    }
    
    /**
     * Endpoint to update a particular acceptance test
     * @param projectId id of the project the acceptance test belongs to
     * @param storyId id 
     * @param acceptanceTestId
     * @param acceptanceTest
     * @return
     */
    @PutMapping(value = "/{storyId}/acceptancetest/{acceptanceTestId}")
    public ResponseEntity<AcceptanceTest> updateAcceptanceTest(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId, @Valid @PathVariable("acceptanceTestId") long acceptanceTestId, @Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.updateAcceptanceTest(projectId, storyId, acceptanceTest), HttpStatus.OK);
    }
    
    /**
     * Endpoint to get a list of available user stories (stories with no sprint assigned to them) 
     * @param projectId id of the project where the user stories exist
     * @return list of available user stories
     */
    @GetMapping(value = "/available")
    public ResponseEntity<List<UserStory>> getAvailableUserStories(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.userStoryService.getAvailableUserStories(projectId), HttpStatus.OK);
    }
}