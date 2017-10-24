package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.User;

@Service
public class AuthenticationService {

    private AuthenticationMapper mapper;

    @Autowired
    public AuthenticationService(AuthenticationMapper mapper) {
        this.mapper = mapper;
    }

    public String register(Account account) {
        //Validation here
        if (null != null) {
            throw new ResponseErrorException("Registration failed! Username already in use.", HttpStatus.FORBIDDEN);
        }


        mapper.createUser(account.getUser());
        mapper.createAccount(account);

        return "Registration successful!";
    }
}
