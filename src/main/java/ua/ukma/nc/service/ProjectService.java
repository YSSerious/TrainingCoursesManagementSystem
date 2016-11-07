package ua.ukma.nc.service;

import ua.ukma.nc.entity.Project;
import ua.ukma.nc.query.ProjectSearch;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ProjectService {
	Project getById(Long id);
	
	Project getByName(String name);

	int deleteProject(Project project);

	int updateProject(Project project);

	List<Project> getAll();

	List<Project> search(ProjectSearch projectSearch);

	Integer getMaxPage(ProjectSearch projectSearch);

	int createProject(Project project);
	
	List<Project> getStudentProjects(Long userId);
	
	List<Project> getMentorProjects(Long userId);
}
