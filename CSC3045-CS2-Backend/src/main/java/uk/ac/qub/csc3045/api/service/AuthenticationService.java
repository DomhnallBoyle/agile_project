package uk.ac.qub.csc3045.api.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.utility.ValidationUtility;

@Service
public class AuthenticationService implements UserDetailsService {

    private AuthenticationMapper mapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationMapper mapper, BCryptPasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Account register(Account account) {
    	ValidationUtility.validateAccount(account, mapper);

    	account.setPassword(passwordEncoder.encode(account.getPassword()));
        mapper.createRoles(account.getUser().getRoles());
        mapper.createUser(account.getUser());
        mapper.createAccount(account);

        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Account account = mapper.findAccountByUsername(username);
    	if (account == null) {
             throw new UsernameNotFoundException(username);
        }
        return new User(account.getUsername(), account.getPassword(), emptyList());
   }
}