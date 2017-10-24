package uk.ac.qub.csc3045.api.utility;

public class ValidationUtility {

	/**
	 * Username must start and end with an Alphanumeric character
	 * Username may contain any of the following special characters: '_', '-', '.'
	 * Username can't contain 2 of the above special characters in a row
	 * Username must be between 5 and 15 characters in length
	 */
	private final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9]+([-_\\.][a-zA-Z0-9]+)*$";
	private final int USERNAME_MIN_LENGTH = 3;
	private final int USERNAME_MAX_LENGTH = 15;
	
	/**
	 * Password must contain a lowercase letter, an uppercase letter and a digit
	 * Password must be between 8 and 25 characters in length
	 */
	private final String PASSWORD_VALIDATION_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
	private final int PASSWORD_MIN_LENGTH = 4;
	private final int PASSWORD_MAX_LENGTH = 25;
	
	/**
	 * Email must contain a domain (case-insensitive) after the @ and an identifier before the @
	 * Email identifier must be at least 4 characters
	 * Email may contain any of the following special characters: '.', '_', '%', '+', '$'
	 */
	private final String EMAIL_VALIDATION_REGEX = "^\\b[a-zA-Z0-9._%+$]{4,}@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}\\b";	
	
	/**
	 * Validates the username against the length requirements and the regex
	 * @param - username the username to be validated
	 * @return a boolean if the username meets the requirements
	 */
	public boolean validateUsername(String username) {
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
	public boolean validatePassword(String password) {
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
	public boolean validateEmail(String email) {
		if (email.matches(EMAIL_VALIDATION_REGEX)) {
			return true;
		}
		
		return false;
	}

}