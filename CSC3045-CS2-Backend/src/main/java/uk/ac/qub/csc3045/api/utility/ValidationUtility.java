package uk.ac.qub.csc3045.api.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;

public class ValidationUtility {

	/**
	 * Username must start and end with an Alphanumeric character
	 * Username may contain any of the following special characters: '_', '-', '.'
	 * Username can't contain 2 of the above special characters in a row
	 * Username must be between 5 and 15 characters in length
	 */
    private static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9]+([-_\\.][a-zA-Z0-9]+)*$";
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 15;
	
	/**
	 * Password must contain a lowercase letter, an uppercase letter and a digit
	 * Password must be between 8 and 25 characters in length
	 */
    private static final String PASSWORD_VALIDATION_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 25;
	
	/**
	 * Email must contain a domain (case-insensitive) after the @ and an identifier before the @
	 * Email identifier must be at least 4 characters
	 * Email may contain any of the following special characters: '.', '_', '%', '+', '$'
	 */
    private static final String EMAIL_VALIDATION_REGEX = "^\\b[a-zA-Z0-9._%+$]{4,}@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}\\b";
	
	/**
	 * Validates a user account
	 * @param account the account to be validated
	 * @param mapper db access
	 * @return a String with the response message
	 */
    public static boolean validateAccount(Account account, AuthenticationMapper mapper) {
        List<String> errorMessages = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        if (!validateUsername(account.getUsername())) {
            errorMessages.add(ErrorMessages.INVALID_USERNAME);
        }
        if (!validatePassword(account.getPassword())) {
            errorMessages.add(ErrorMessages.INVALID_PASSWORD);
        }
        if (!validateEmail(account.getUser().getEmail())) {
            errorMessages.add(ErrorMessages.INVALID_EMAIL);
        }

        if (!errorMessages.isEmpty()) {
            for (int i = 0; i < errorMessages.size(); i++) {
                sb.append(errorMessages.get(i));
            }
            throw new ResponseErrorException(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        if (mapper.findAccountByUsername(account.getUsername()) != null) {
            sb.append(ErrorMessages.USERNAME_ALREADY_EXISTS);
            throw new ResponseErrorException(sb.toString(), HttpStatus.CONFLICT);
        }
        
        if (mapper.findUserByEmail(account.getUser().getEmail()) != null) {
            sb.append(ErrorMessages.EMAIL_ALREADY_EXISTS);
            throw new ResponseErrorException(sb.toString(), HttpStatus.CONFLICT);
        }
        
        return true;
    }
    
    public static boolean validateProjectExists(long projectId, ProjectMapper mapper) {
        return (mapper.findProjectByProjectId(projectId) != null);
    }

	/**
	 * Validates the username against the length requirements and the regex
	 * @param - username the username to be validated
	 * @return a boolean if the username meets the requirements
	 */
    private static boolean validateUsername(String username) {
        if (username.length() >= USERNAME_MIN_LENGTH && username.length() <= USERNAME_MAX_LENGTH) {
            if (username.matches(USERNAME_VALIDATION_REGEX)) {
                return true;
            }
        }

        return false;
    }
	
	/**
	 * Validates the password against the length requirements and the regex
	 * @param - password the password to be validated
	 * @return a boolean if the password meets the requirements
	 */
    private static boolean validatePassword(String password) {
        if (password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH) {
            if (password.matches(PASSWORD_VALIDATION_REGEX)) {
                return true;
            }
        }

        return false;
    }
	
	/**
	 * Validates the email against the regex
	 * @param email - the email to be validated
	 * @return a boolean if the email meets the requirements
	 */
    private static boolean validateEmail(String email) {
        if (email.matches(EMAIL_VALIDATION_REGEX)) {
            return true;
        }

        return false;
    }
}