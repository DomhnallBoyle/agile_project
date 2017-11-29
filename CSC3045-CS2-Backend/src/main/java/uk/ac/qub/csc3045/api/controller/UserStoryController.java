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
    private final UserStoryService userStoryService;
    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }
    
    @GetMapping()
    public ResponseEntity<List<UserStory>> getUserStoriesByProject(@PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.userStoryService.getAllUserStories(projectId), HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<List<UserStory>> updateBacklogOrder(@RequestBody List<UserStory> backlog) {
        return new ResponseEntity<>(userStoryService.updateBacklogOrder(backlog), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<UserStory> create(@PathVariable("projectId") long projectId, @Valid @RequestBody UserStory userStory) {
    	if(userStory.getProject().getId() != null && (projectId != userStory.getProject().getId())) {
    		throw new ResponseErrorException("Id in URL and body to not match", HttpStatus.BAD_REQUEST);
    	}
    	
        return new ResponseEntity<>(this.userStoryService.create(userStory), HttpStatus.CREATED);
    }
    @GetMapping(value = "/{storyId}")
    public ResponseEntity<UserStory> getUserStory(@PathVariable("projectId") long projectId, @PathVariable("storyId") long storyId) {
        return new ResponseEntity<>(this.userStoryService.getUserStory(projectId, storyId), HttpStatus.OK);
    }
    
    @PostMapping(value = "/{storyId}/acceptancetest")
    public ResponseEntity<AcceptanceTest> addAcceptanceTest(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId, @Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.addAcceptanceTest(projectId, storyId, acceptanceTest), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{storyId}/acceptancetest")
    public ResponseEntity<List<AcceptanceTest>> getAcceptanceTests(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId) {
    	return new ResponseEntity<>(this.userStoryService.getAcceptanceTests(projectId, storyId), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{storyId}/acceptancetest/{acceptanceTestId}")
    public ResponseEntity<AcceptanceTest> updateAcceptanceTest(@Valid @PathVariable("projectId") long projectId, @Valid @PathVariable("storyId") long storyId, @Valid @PathVariable("acceptanceTestId") long acceptanceTestId, @Valid @RequestBody AcceptanceTest acceptanceTest) {
    	return new ResponseEntity<>(this.userStoryService.updateAcceptanceTest(projectId, storyId, acceptanceTest), HttpStatus.OK);
    }
    
    @GetMapping(value = "/available")
    public ResponseEntity<List<UserStory>> getAvailableUserStories(@Valid @PathVariable("projectId") long projectId) {
        return new ResponseEntity<>(this.userStoryService.getAvailableUserStories(projectId), HttpStatus.OK);
    }
}