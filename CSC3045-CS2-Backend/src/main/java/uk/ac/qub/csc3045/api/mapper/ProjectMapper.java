package uk.ac.qub.csc3045.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import uk.ac.qub.csc3045.api.model.Project;

@Mapper
@Repository
public interface ProjectMapper {
	void createProject(Project project);
	void updateProject(Project project);
	Project getProjectById(@Param("id")long id);
}


