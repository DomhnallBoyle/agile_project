package uk.ac.qub.csc3045.api.service;

import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class AuthenticationService {

    private AuthenticationMapper mapper;

    @Autowired
    public AuthenticationService(AuthenticationMapper mapper) {
        this.mapper = mapper;
    }

    public String register(Account account) {
    	String responseMessage = validateAccount(account);

        mapper.createUser(account.getUser());
        mapper.createAccount(account);

        return responseMessage;
    }
    
    private String validateAccount(Account account) {
    	ValidationUtility validator = new ValidationUtility();
    	PriorityQueue<String> errorMessages = new PriorityQueue<>();
    	StringBuilder sb = new StringBuilder();
    	
    	if (!validator.validateUsername(account.getUsername())) {
    		System.out.println("hello yes i am here");
    		errorMessages.add("The Username does not meet the requirements:\n\tUsername length must be between 3 and 15 characters\n\tUsername can only contain the following special characters '_', '-', '.'\n");
    	}
    	if (!validator.validatePassword(account.getPassword())) {
    		errorMessages.add("The Password does not meet the requirements:\n\tPassword length must be between 4 and 25 characters\n\tPassword must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit\n");
    	}
    	if (!validator.validateEmail(account.getUser().getEmail())) {
    		errorMessages.add("The Email does not meet the requirements:\n\tEmail identifier must be at least 4 letters and have a valid domain\n\tEmail identifier can only contain the following special characters '.', '_', '%', '+', '$'\n");
    	}
		
		while (!errorMessages.isEmpty()) {
			sb.append("Registration Failed:\n");
			for (int i = 0; i < errorMessages.size(); i++) {
				sb.append(errorMessages.poll());
			}
			System.out.println(sb.toString());
			throw new ResponseErrorException(sb.toString(), HttpStatus.BAD_REQUEST);
		}
    	if (mapper.findAccountbyUsername(account.getUsername()) != null){
    		sb.append("This username already exists, please select another one");
    		throw new ResponseErrorException(sb.toString(), HttpStatus.CONFLICT);
    	}
    	
    	return "Registration Successful!";
    }
}