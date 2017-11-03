package uk.ac.qub.csc3045.api.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.service.UserStoryService;

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
    
    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<UserStory> getUserStory(@PathVariable("id") long id) {
        return new ResponseEntity<>(this.userStoryService.getUserStory(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/project/{id}", method = GET)
    public ResponseEntity<List<UserStory>> getUserStoriesByProject(@PathVariable("id") long id) {
        return new ResponseEntity<>(this.userStoryService.getAllUserStories(id), HttpStatus.OK);
    }
}
