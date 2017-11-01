package uk.ac.qub.csc3045.api.service;

import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.mapper.ProjectMapper;
import uk.ac.qub.csc3045.api.model.Project;

@Service
public class ProjectService {
	private ProjectMapper mapper;
	
	public ProjectService(ProjectMapper mapper) {
		this.mapper = mapper;
	}
		
	public Project create(Project project) {
		mapper.createProject(project);

		return mapper.getProjectById(project.getId());
	}
}
