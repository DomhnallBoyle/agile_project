package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import uk.ac.qub.csc3045.api.exception.ResponseErrorException;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.User;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> search(User user) {
        List<User> users = userMapper.searchUsers(user);

        if (users == null || users.isEmpty()) {
            throw new ResponseErrorException("Could no find user(s) matching criteria.", HttpStatus.NOT_FOUND);
        }

        return users;
    }
}
