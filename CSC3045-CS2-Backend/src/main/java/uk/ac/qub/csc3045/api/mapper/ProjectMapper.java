package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;

import java.util.List;


@Mapper
@Repository
public interface ProjectMapper {

    // SELECT Queries

    Project getProjectById(@Param("id") long id);

    List<User> getUsersOnProject(@Param("projectId") long projectId);

    List<User> getProjectDevelopers(@Param("projectId") long projectId);

    // INSERT Queries

    void createProject(Project project);

    void addToProjectTeam(@Param("projectId") long projectId, @Param("userId") long userId);

    // UPDATE Queries

    void updateProject(Project project);

    void setUserAsScrumMaster(@Param("projectId") long projectId, @Param("userId") long userId, @Param("value") boolean value);
}