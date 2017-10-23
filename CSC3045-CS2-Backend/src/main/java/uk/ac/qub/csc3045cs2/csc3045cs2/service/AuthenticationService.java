package uk.ac.qub.csc3045cs2.csc3045cs2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045cs2.csc3045cs2.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045cs2.csc3045cs2.model.Account;
import uk.ac.qub.csc3045cs2.csc3045cs2.model.User;

@Service
public class AuthenticationService {

	private AuthenticationMapper mapper;

	@Autowired
	public AuthenticationService(AuthenticationMapper mapper) {
		this.mapper = mapper;
	}

	public String register(String userName, String passWord, User user) {
		mapper.createUser(user);
		User retrievedUser = mapper.findUserByName(user.getFirstName(), user.getLastName());
		Account account = new Account();
		account.setUser(retrievedUser);
		account.setUsername(userName);
		account.setPassword(passWord);
		mapper.createAccount(account);
		
		return "Registration Successful";
	}
}
