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
	public void registerAccountShouldReturn201OnSuccess() {
		//Arrange
		AuthenticationService authenticationService = mock(AuthenticationService.class);
		Account account = new Account();
		AuthenticationController authenticationController = new AuthenticationController(authenticationService);
		when(authenticationService.register(account)).thenReturn(account);
		
		//Act
		ResponseEntity response = authenticationController.register(account);
		
		//Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(account, response.getBody());
	}
}