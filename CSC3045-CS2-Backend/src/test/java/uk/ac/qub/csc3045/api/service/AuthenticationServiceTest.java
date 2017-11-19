package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private AuthenticationMapper authenticationMapperMock;

    private Account account;
    private User user;

    @Before
    public void setup() throws Exception {
        authenticationMapperMock = mock(AuthenticationMapper.class);
        BCryptPasswordEncoder passwordEncoderMock = mock(BCryptPasswordEncoder.class);
        authenticationService = new AuthenticationService(authenticationMapperMock, passwordEncoderMock);

        user = new User();
        user.setEmail("abcdef@gmail.com");

        account = new Account();
        account.setPassword("Abc1");
        account.setUser(user);
    }

    @Test
    public void handleRegisterRequestSuccessful() throws Exception {
        Account response = authenticationService.register(account);

        assertEquals(account.getUser().getEmail(), response.getUser().getEmail());
        assertEquals(account.getPassword(), response.getPassword());
    }

    @Test(expected = ResponseErrorException.class)
    public void handleRegisterRequestFailed() throws Exception {
        when(authenticationMapperMock.findAccountByEmail(account.getUser().getEmail())).thenReturn(account);

        authenticationService.register(account);
    }
}