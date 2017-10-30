package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private AuthenticationMapper authenticationMapperMock;

    private Account account;
    private User user;

    @Before
    public void setUp() throws Exception {
        authenticationMapperMock = mock(AuthenticationMapper.class);
        BCryptPasswordEncoder passwordEncoderMock =  mock(BCryptPasswordEncoder.class);
        authenticationService = new AuthenticationService(authenticationMapperMock, passwordEncoderMock );

        user = new User();
        user.setEmail("abcdef@gmail.com");

        account = new Account();
        account.setUsername("abcdef");
        account.setPassword("Abc1");
        account.setUser(user);
    }

    @Test
    public void handleRegisterRequestSuccessful() throws Exception {
        Account response = authenticationService.register(account);

        assertEquals(account.getUsername(), response.getUsername());
        assertEquals(account.getPassword(), response.getPassword());
    }

    @Test(expected = ResponseErrorException.class)
    public void handleRegisterRequestFailed() throws Exception {
        when(authenticationMapperMock.findAccountByUsername(account.getUsername())).thenReturn(account);

        authenticationService.register(account);
    }
}