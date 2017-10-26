package uk.ac.qub.csc3045.api.utility;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;

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
            String successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            account.setUsername("abc_123-cba");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            account.setUsername("1_2-3-4_5-6_abc");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));
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
            String successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            account.setPassword("Ab1nb1nb1nb1nb1nb1nb1nb1n");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            account.setPassword("1234TESTING4321password0");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));
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
    public void emailValidTest() {
        try {
            user.setEmail("abcdef@gmail.com");
            String successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            user.setEmail("testing@qub.ac.uk");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));

            user.setEmail("testing._%+$@qub.ac.uk");
            successMessage = ValidationUtility.validateAccount(account, authenticationMapperMock);
            assertThat(successMessage, containsString("Registration Successful"));
        } catch (ResponseErrorException e) {
            fail("An unexpected exception was thrown by validateAccount");
        }
    }
}