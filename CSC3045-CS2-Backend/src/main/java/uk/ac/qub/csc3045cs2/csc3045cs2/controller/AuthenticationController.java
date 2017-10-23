package uk.ac.qub.csc3045cs2.csc3045cs2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045cs2.csc3045cs2.model.User;
import uk.ac.qub.csc3045cs2.csc3045cs2.service.AuthenticationService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

	private AuthenticationService service;

	@Autowired
	public AuthenticationController(AuthenticationService service) {
		this.service = service;
	}

	@RequestMapping(value = "/register", method = POST)
	public ResponseEntity<String> register(@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String passWord, @RequestBody User user) {

		return new ResponseEntity<>(service.register(userName, passWord, user), HttpStatus.OK);
	}
}
