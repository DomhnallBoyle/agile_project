package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.qub.csc3045.api.model.UserStory;
import uk.ac.qub.csc3045.api.service.UserStoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/story")
public class UserStoryController {

    private UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PutMapping(value = "/backlog/order")
    public ResponseEntity<List<UserStory>> updateBacklogOrder(@RequestBody List<UserStory> backlog) {
        return new ResponseEntity<>(userStoryService.updateBacklogOrder(backlog), HttpStatus.OK);
    }

}
