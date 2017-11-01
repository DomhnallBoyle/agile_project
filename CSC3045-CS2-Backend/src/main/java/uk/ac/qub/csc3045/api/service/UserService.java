package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> search(User user) {
        Roles rolesCriteria = user.getRoles();

        return userMapper.searchUsers(user).stream()
                .filter(foundUser ->
                        (rolesCriteria.isDeveloper() && foundUser.getRoles().isDeveloper() == rolesCriteria.isDeveloper())
                                || (rolesCriteria.isScrumMaster() && foundUser.getRoles().isScrumMaster() == rolesCriteria.isScrumMaster())
                                || (rolesCriteria.isProductOwner() && foundUser.getRoles().isProductOwner() == rolesCriteria.isProductOwner()))
                .collect(Collectors.toList());
    }
}
