package uk.ac.qub.csc3045.api.service;

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

import static java.util.Collections.emptyList;

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
        return mapper.findAccountByEmail(account.getUser().getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = mapper.findAccountByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(account.getUser().getEmail(), account.getPassword(), emptyList());
    }
}