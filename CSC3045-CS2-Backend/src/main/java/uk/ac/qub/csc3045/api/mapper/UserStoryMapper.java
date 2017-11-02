package uk.ac.qub.csc3045.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.UserStory;

@Mapper
@Repository
public interface UserStoryMapper {
	void createUserStory(UserStory userStory);
	UserStory getUserStoryById(@Param("id")long id);
	List<UserStory> getUserStoryByProject(@Param("id")long id);
}