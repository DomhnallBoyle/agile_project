package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

	/**
	 * Private variables
	 */
    private final AuthenticationService authenticationService;

    /**
     * Constructor for the Authentication controller
     * @param authenticationService - service for this controller
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint for registering
     * @param account - Request takes in account object
     * @return Account object from the database
     */
    @PostMapping(value = "/register")
    public ResponseEntity<Account> register(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(this.authenticationService.register(account), HttpStatus.CREATED);
    }
}