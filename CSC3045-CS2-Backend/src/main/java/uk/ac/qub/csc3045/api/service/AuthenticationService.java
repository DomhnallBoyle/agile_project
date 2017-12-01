package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Skill;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

import static java.util.Collections.emptyList;

@Service
public class AuthenticationService implements UserDetailsService {

	/**
	 * Private variables
	 */
    private AuthenticationMapper authMapper;
	private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor for the authentication service
     * @param mapper
     * @param passwordEncoder
     */
    @Autowired
    public AuthenticationService(AuthenticationMapper authMapper, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.authMapper = authMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register an account. 
     * Validate account exists and then create roles, user and account in the database.
     * Return the account added to the database
     * @param account
     * @return
     */
    public Account register(Account account) {
        ValidationUtility.validateAccount(account, authMapper);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        authMapper.createRoles(account.getUser().getRoles());
        authMapper.createUser(account.getUser());
        authMapper.createAccount(account);
        
        // adding the users skills
    	for(Skill skill: account.getUser().getSkills()) {
    		if (skill.getDescription() != "")
    			userMapper.addUserSkill(account.getUser().getId(), skill);
    	}
    	
        return authMapper.findAccountByEmail(account.getUser().getEmail());
    }

    /**
     * Retrieves the user by email address
     * If the account is null (not found), an exception is thrown
     * else return user
     * @param email unique identifier of user
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = authMapper.findAccountByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(account.getUser().getEmail(), account.getPassword(), emptyList());
    }
}