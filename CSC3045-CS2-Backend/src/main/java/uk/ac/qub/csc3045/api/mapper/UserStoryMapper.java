
package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.ac.qub.csc3045.api.model.AcceptanceTest;
import uk.ac.qub.csc3045.api.model.UserStory;

import java.util.List;

@Mapper
@Repository
public interface UserStoryMapper {

    // SELECT Queries

    UserStory getUserStoryById(@Param("id") long id);

    List<UserStory> getUserStoriesByProject(@Param("id") long id);
    
    List<AcceptanceTest> getAcceptanceTestsByStoryId(@Param("id") long id);
    
    AcceptanceTest getAcceptanceTestById(@Param("id") long id);
    
    List<UserStory> getAvailableUserStories(@Param("id")long id);

    // INSERT Queries

    void createUserStory(UserStory userStory);
    
    void createAcceptanceTest(@Param("userStoryId") long userStoryId, @Param("acceptanceTest") AcceptanceTest acceptanceTest);

    // UPDATE Queries
    
    void updateUserStory(UserStory userStory);

    void updateUserStoryIndex(@Param("userStoryId") long userStoryId, @Param("index") int index);
    
    void updateAcceptanceTest(AcceptanceTest acceptanceTest);

}