package uk.ac.qub.csc3045.api.utility;

import org.springframework.http.HttpStatus;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.mapper.SprintMapper;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.UserStory;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtility {

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
     *
     * @param account the account to be validated
     * @param mapper db access
     * @return a boolean showing true if the details provided were true
     */
    public static boolean validateAccount(Account account, AuthenticationMapper mapper) {
        List<String> errorMessages = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

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

        if (mapper.findAccountByEmail(account.getUser().getEmail()) != null) {
            sb.append(ErrorMessages.EMAIL_ALREADY_EXISTS);
            throw new ResponseErrorException(sb.toString(), HttpStatus.CONFLICT);
        }

        return true;
    }

    /**
     * Validates that a Sprint with the specified Sprint ID exists within the database
     * 
     * @param sprintId the Sprint ID to search for
     * @param mapper db access
     * @return a boolean showing true if the sprint was found
     */
    public static boolean validateSprintExists(long sprintId, SprintMapper mapper) {
        return (mapper.getSprintById(sprintId) != null);
    }
    
    /**
     * Validates that a Project with the specified Project ID exists within the database
     * 
     * @param projectId the Project ID to search for
     * @param mapper db access
     * @return a boolean showing true if the Project was found
     */
    public static boolean validateProjectExists(long projectId, ProjectMapper mapper) {
        return (mapper.getProjectById(projectId) != null);
    }
    /**
     * Validates that User Story with the specified User Story ID exists within the database
     * 
     * @param storyId the User Story ID to search for 
     * @param mapper db access
     * @return a boolean showing true if the User Story was found
     */
    public static boolean validateUserStoryExists(long storyId, UserStoryMapper mapper) {
        return (mapper.getUserStoryById(storyId) != null);
    }
    
    /**
     * Validates that a User Story exists within a given Project
     * 
     * @param storyId the User Story ID we are searching the Project Backlog for
     * @param projectId The Project ID representing the Project we are searching in
     * @param userStoryMapper db access
     * @return a boolean showing true if the Project Backlog contained the specified User Story ID
     */
    public static boolean validateProjectContainsUserStory(long storyId, long projectId, UserStoryMapper userStoryMapper) {
        UserStory story = userStoryMapper.getUserStoryById(storyId);
        List<UserStory> projectBacklog = userStoryMapper.getUserStoriesByProject(projectId);

        for (int i = 0; i < projectBacklog.size(); i++) {
            if (story.equals(projectBacklog.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the password against the length requirements and the regex
     *
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
     *
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