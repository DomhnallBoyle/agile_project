package uk.ac.qub.csc3045.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.service.AuthenticationService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity<Account> register(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(this.authenticationService.register(account), HttpStatus.OK);
    }
}