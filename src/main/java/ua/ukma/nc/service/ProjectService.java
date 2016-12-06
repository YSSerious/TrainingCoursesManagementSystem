package ua.ukma.nc.service;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.query.ProjectSearch;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ProjectService {
	boolean canView(Long projectId);
	
	Project getById(Long id);
	
        boolean exist(Long id);
        
	Project getByName(String name);

	int deleteProject(Project project);

	int updateProject(Project project);

	List<Project> getAll();

	List<Project> getAllFinished();

	List<Project> getAllStudentProjectsWithoutAnyOfHrReviews(Long userId);

	List<Project> search(ProjectSearch projectSearch);

	Integer getMaxPage(ProjectSearch projectSearch);

	int createProject(Project project);
	
	List<Project> getStudentProjects(Long userId);
	
	List<Project> getMentorProjects(Long userId);

	List<Project> getMentorStudentProjects(Long mentorId, Long studentId);

	int addCriteria(Long projectId, Criterion criterion);

	int deleteProjectCriterion(Long projectId, Criterion criterion);

	int deleteCriterionInAllProjectMeetings(Long projectId, Criterion criterion);

	List<Project> getAllUpcoming();
}
