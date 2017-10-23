package uk.ac.qub.csc3045cs2.csc3045cs2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.qub.csc3045cs2.csc3045cs2.model.User;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

	@RequestMapping(value = "/register", method = POST)
	public void register(@PathVariable String userName,
			@PathVariable String passWord, @RequestBody User user) {

		
	}
}
