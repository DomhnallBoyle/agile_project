package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserStoryMapper {
    void updateUserStoryIndex(@Param("userStoryId") long userStoryId, @Param("index") int index);
}
