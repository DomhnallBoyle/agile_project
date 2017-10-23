package uk.ac.qub.csc3045cs2.csc3045cs2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045cs2.csc3045cs2.model.Account;
import uk.ac.qub.csc3045cs2.csc3045cs2.model.User;

@Mapper
@Repository
public interface AuthenticationMapper {

	void createUser(User user);
	
	void createAccount(Account account);
	
	User findUserByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
