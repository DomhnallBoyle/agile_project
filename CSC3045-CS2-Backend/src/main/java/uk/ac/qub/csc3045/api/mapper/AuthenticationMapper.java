package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import uk.ac.qub.csc3045.api.model.Account;
import uk.ac.qub.csc3045.api.model.Roles;
import uk.ac.qub.csc3045.api.model.User;

@Mapper
@Repository
public interface AuthenticationMapper {

    // SELECT Queries

    Account findAccountByEmail(@Param("email") String email);

    User findUserByEmail(@Param("email") String email);

    // INSERT Queries

    void createUser(User user);

    void createAccount(Account account);

    void createRoles(Roles roles);
}