package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ProjectDao {
	Project getById(Long id);
	
	Project getByName(String name);

	int deleteProject(Project project);

	int deleteProjectCriterion(Long projectId, Criterion criterion);

	int deleteCriterionInAllProjectMeetings(Long projectId, Criterion criterion);

	int updateProject(Project project);

	List<Project> getAll();

	List<Project> query(String query);
	
	List<Project> getStudentProjects(Long userId);
	
	List<Project> getMentorProjects(Long userId);

	List<Project> getMentorStudentProjects(Long mentorId, Long studentId);

	Integer count(String query);

	int createProject(Project project);

	int addCriteria(Long projectId, Criterion criterion);
}
