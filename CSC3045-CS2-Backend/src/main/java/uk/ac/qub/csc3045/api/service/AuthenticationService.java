package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    	String responseMessage = ValidationUtility.validateAccount(account, mapper);

        mapper.createUser(account.getUser());
        mapper.createAccount(account);

        return responseMessage;
    }
}