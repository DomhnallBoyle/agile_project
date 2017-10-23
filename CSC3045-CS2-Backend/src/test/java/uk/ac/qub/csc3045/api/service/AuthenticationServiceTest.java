package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

//    private AuthenticationService authenticationService;
//    private AuthenticationMapper authenticationMapperMock;
//
//    @Before
//    public void setUp() throws Exception {
//        authenticationMapperMock = Mockito.mock(AuthenticationMapper.class);
//        authenticationService = new AuthenticationService(authenticationMapperMock);
//    }
//
//    @Test
//    public void handleLoginRequestSuccessful() throws Exception {
//        Account mockAccount = new Account();
//        mockAccount.setUsername("russell.kane");
//        mockAccount.setPassword("intense");
//
//        String expectedResponse = "Login successful!";
//
//        when(authenticationMapperMock.findAccount(mockAccount.getUsername(), mockAccount.getPassword()))
//                .thenReturn(mockAccount);
//
//        String response = authenticationService.register(mockAccount.getUsername(), mockAccount.getPassword());
//        assertEquals(expectedResponse, response);
//    }
//
//    @Test(expected = ResponseErrorException.class)
//    public void handleLoginRequestFailed() throws Exception {
//        Account mockAccount = new Account();
//        mockAccount.setUsername("ji.ming");
//        mockAccount.setPassword("ok");
//
//        String expectedResponse = "Login failed!";
//
//        when(authenticationMapperMock.findAccount(mockAccount.getUsername(), mockAccount.getPassword()))
//                .thenReturn(null);
//
//        String response = authenticationService.register(mockAccount.getUsername(), mockAccount.getPassword());
//        assertEquals(expectedResponse, response);
//    }

}