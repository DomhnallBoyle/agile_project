package uk.ac.qub.csc3045.api.utility;

public abstract class ErrorMessages {

    /**
     * Account Validation Error Messages
     */
    public static final String INVALID_USERNAME = "The Username does not meet the requirements:\n\tUsername length must be between 3 and 15 characters\n\tUsername can only contain the following special characters '_', '-', '.'\n";
    public static final String INVALID_PASSWORD = "The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n";
    public static final String INVALID_EMAIL = "The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n";
    public static final String USERNAME_ALREADY_EXISTS = "This username already exists, please select another one";
    public static final String EMAIL_ALREADY_EXISTS = "This email already exists, please select another one";
}