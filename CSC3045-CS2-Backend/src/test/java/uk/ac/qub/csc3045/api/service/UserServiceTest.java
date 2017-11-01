package uk.ac.qub.csc3045.api.service;

import org.junit.Before;
import org.junit.Test;
import uk.ac.qub.csc3045.api.mapper.UserMapper;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private UserMapper userMapper;

    private List<User> userList;

    @Before
    public void setUp() {
        userMapper = mock(UserMapper.class);
        userService = new UserService(userMapper);

        userList = new ArrayList<>();

        User user1 = new User("Ragnar", "Lothbrok", "ragnar.lothbrok@valhalla.odin");
        user1.setRoles(new Roles(true, true, false));

        User user2 = new User("Bjorn", "Ironside", "bjorn.ironside@valhalla.odin");
        user2.setRoles(new Roles(false, false, true));

        User user3 = new User("Rollo", "Lothbrok", "rollo.lothbrok@valhalla.odin");
        user3.setRoles(new Roles(true, false, false));

        User user4 = new User("Floki", "Boatbuilder", "floki.boatbuilder@valhalla.odin");
        user4.setRoles(new Roles(true, true, true));

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }

    @Test
    public void searchShouldReturnAllUsers() {
        User searchCriteria = new User();
        searchCriteria.setRoles(new Roles(true, true, true));

        when(userMapper.searchUsers(searchCriteria)).thenReturn(userList);

        List<User> returnedUsers = userService.search(searchCriteria);

        assertThat(returnedUsers.size()).isEqualTo(4);
    }

    @Test
    public void searchShouldReturnProductOwners() {
        User searchCriteria = new User();
        searchCriteria.setRoles(new Roles(false, false, true));

        when(userMapper.searchUsers(searchCriteria)).thenReturn(userList);

        List<User> returnedUsers = userService.search(searchCriteria);

        assertThat(returnedUsers.size()).isEqualTo(2);
        assertThat(returnedUsers).contains(userList.get(1));
        assertThat(returnedUsers).contains(userList.get(3));
    }

    @Test
    public void searchShouldReturnDevelopersAndScrumMasters() {
        User searchCriteria = new User();
        searchCriteria.setRoles(new Roles(true, true, false));

        when(userMapper.searchUsers(searchCriteria)).thenReturn(userList);

        List<User> returnedUsers = userService.search(searchCriteria);

        assertThat(returnedUsers.size()).isEqualTo(3);
        assertThat(returnedUsers).contains(userList.get(0));
        assertThat(returnedUsers).contains(userList.get(2));
        assertThat(returnedUsers).contains(userList.get(3));
    }

    @Test
    public void searchShouldReturnNoUsers() {
        User searchCriteria = new User();
        searchCriteria.setRoles(new Roles(false, false, false));

        when(userMapper.searchUsers(searchCriteria)).thenReturn(userList);

        List<User> returnedUsers = userService.search(searchCriteria);

        assertTrue(returnedUsers.isEmpty());
    }
}
