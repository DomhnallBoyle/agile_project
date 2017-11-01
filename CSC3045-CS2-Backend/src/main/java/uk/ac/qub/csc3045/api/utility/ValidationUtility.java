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
            errorMessages.add("The Username does not meet the requirements:\n\tUsername length must be between 3 and 15 characters\n\tUsername can only contain the following special characters '_', '-', '.'\n");
        }
        if (!validatePassword(account.getPassword())) {
            errorMessages.add("The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n");
        }
        if (!validateEmail(account.getUser().getEmail())) {
            errorMessages.add("The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n");
        }

        if (!errorMessages.isEmpty()) {
            for (int i = 0; i < errorMessages.size(); i++) {
                sb.append(errorMessages.get(i));
            }
            System.out.println(sb.toString());
            throw new ResponseErrorException(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        if (mapper.findAccountByUsername(account.getUsername()) != null) {
            sb.append("This username already exists, please select another one");
            throw new ResponseErrorException(sb.toString(), HttpStatus.CONFLICT);
        }

        return true;
    }
	
    public static boolean validateProject(Project project, ProjectMapper mapper) {
        if (!validateProjectExists(project, mapper)) {
            throw new ResponseErrorException("Project does not exist", HttpStatus.NOT_FOUND);
        }
        
        for (User user : project.getUsers()) {
            if (!validateUserExists(user, mapper)) {
                throw new ResponseErrorException("User" + user.getForename() + " " + user.getSurname() + " does not exist", HttpStatus.NOT_FOUND);
            }
        }
        
        return true;
    }
    
    private static boolean validateProjectExists(Project project, ProjectMapper mapper) {
        return (mapper.findProjectByProjectName(project.getName()) != null);
    }
    
    private static boolean validateUserExists(User user, ProjectMapper mapper) {
        return (mapper.findUserByEmail(user.getEmail()) != null);
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