package uk.ac.qub.csc3045.api.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.ac.qub.csc3045.api.controller.UserController;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void searchingUsersShouldReturn200OnSuccess() {
        User searchCriteria = new User();

        ResponseEntity response = userController.search(searchCriteria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
