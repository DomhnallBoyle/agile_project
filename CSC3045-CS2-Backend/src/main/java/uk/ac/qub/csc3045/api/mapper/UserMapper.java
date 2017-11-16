package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import uk.ac.qub.csc3045.api.model.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    // SELECT Queries

    List<User> searchUsers(User user);
}
