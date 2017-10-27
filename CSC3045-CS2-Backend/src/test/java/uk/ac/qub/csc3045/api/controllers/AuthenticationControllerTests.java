package uk.ac.qub.csc3045.api.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import uk.ac.qub.csc3045.api.controller.AuthenticationController;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.service.AuthenticationService;

public class AuthenticationControllerTests {
	@Test
	public void registerAccountShouldReturn200OnSuccess() {
		AuthenticationService authenticationService = mock(AuthenticationService.class);
		Account account = new Account();
		String responseString = "Ok";
		when(authenticationService.register(account)).thenReturn(responseString);
		
		AuthenticationController authenticationController = new AuthenticationController(authenticationService);
		
		ResponseEntity response = authenticationController.register(account);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseString,response.getBody());
	}
}