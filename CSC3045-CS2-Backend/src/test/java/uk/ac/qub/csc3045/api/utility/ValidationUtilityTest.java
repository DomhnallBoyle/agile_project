package uk.ac.qub.csc3045.api.utility;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ValidationUtilityTest {

    private Account account;
    private User user;

    private AuthenticationMapper authenticationMapperMock;

    @Before
    public void setup() {
        authenticationMapperMock = Mockito.mock(AuthenticationMapper.class);

        user = new User();
        user.setEmail("abcdef@gmail.com");

        account = new Account();
        account.setUsername("abcdef");
        account.setPassword("Abc1");
        account.setUser(user);
    }

    @Test
    public void usernameMinLengthTest() {
        try {
            account.setUsername("ab");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameMaxLengthTest() {
        try {
            account.setUsername("abcdefghijklmnopqrstuvwxyz1234567890");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameStartSpecialCharacterTest() {
        try {
            account.setUsername("_abc1");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameEndSpecialCharacterTest() {
        try {
            account.setUsername("abc1_");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameIllegalSpecialCharactersTest() {
        try {
            account.setUsername("a%b c-d#e");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameSuccessiveSpecialCharactersTest() {
        try {
            account.setUsername("a__bcd1");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Username does not meet the requirements"));
        }
    }

    @Test
    public void usernameValidTest() {
        try {
            account.setUsername("abc123");
            boolean validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            account.setUsername("abc_123-cba");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            account.setUsername("1_2-3-4_5-6_abc");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);
        } catch (ResponseErrorException e) {
            fail("An unexpected exception was thrown by validateAccount");
        }
    }

    @Test
    public void usernameAlreadyExists() {
        try {
            when(authenticationMapperMock.findAccountByUsername(account.getUsername())).thenReturn(account);

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("username already exists"));
        }
    }

    @Test
    public void passwordMinLengthTest() {
        try {
            account.setPassword("Ab1");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Password does not meet the requirements"));
        }
    }

    @Test
    public void passwordMaxLengthTest() {
        try {
            account.setPassword("Abcdefghijklmnopqrstuvwxyz1234567890");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Password does not meet the requirements"));
        }
    }

    @Test
    public void passwordNoUpperCaseLetterTest() {
        try {
            account.setPassword("abcdef123");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Password does not meet the requirements"));
        }
    }

    @Test
    public void passwordNoLowerCaseLetterTest() {
        try {
            account.setPassword("ABCDEF123");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Password does not meet the requirements"));
        }
    }

    @Test
    public void passwordNoDigitTest() {
        try {
            account.setPassword("ABCDEFGHI");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Password does not meet the requirements"));
        }
    }

    @Test
    public void passwordValidTest() {
        try {
            account.setPassword("ABCdef123");
            boolean validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            account.setPassword("Ab1nb1nb1nb1nb1nb1nb1nb1n");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            account.setPassword("1234TESTING4321password0");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);
        } catch (ResponseErrorException e) {
            fail("An unexpected exception was thrown by validateAccount");
        }
    }

    @Test
    public void emailMinLengthTest() {
        try {
            user.setEmail("a@gmail.com");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Email does not meet the requirements"));
        }
    }

    @Test
    public void emailIncompleteDomain() {
        try {
            user.setEmail("abcdef@gmail");

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("The Email does not meet the requirements"));
        }
    }

    @Test
    public void emailAlreadyExists() {
        try {
            when(authenticationMapperMock.findUserByEmail(account.getUser().getEmail())).thenReturn(account.getUser());

            ValidationUtility.validateAccount(account, authenticationMapperMock);
            fail("An exception was not thrown by validateAccount");
        } catch (ResponseErrorException e) {
            assertThat(e.getMessage(), containsString("email already exists"));
        }
    }

    @Test
    public void emailValidTest() {
        try {
            user.setEmail("abcdef@gmail.com");
            boolean validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            user.setEmail("testing@qub.ac.uk");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);

            user.setEmail("testing._%+$@qub.ac.uk");
            validated = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertTrue(validated);
        } catch (ResponseErrorException e) {
            fail("An unexpected exception was thrown by validateAccount");
        }
    }
}