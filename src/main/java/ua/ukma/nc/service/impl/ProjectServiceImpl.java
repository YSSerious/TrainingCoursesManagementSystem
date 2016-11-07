package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.query.ProjectParamResolver;
import ua.ukma.nc.query.ProjectSearch;
import ua.ukma.nc.service.ProjectService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectParamResolver projectParamResolver;

	@Autowired
	private ProjectDao projectDao;

	@Override
	public Project getById(Long id) {
		return projectDao.getById(id);
	}
	
	@Override
	public Project getByName(String name){
		return projectDao.getByName(name);
	}
	
	@Override
	public int deleteProject(Project project) {
		return projectDao.deleteProject(project);
	}

	@Override
	public int updateProject(Project project) {
		return projectDao.updateProject(project);
	}

	@Override
	public List<Project> getAll() {
		return projectDao.getAll();
	}

	@Override
	public int createProject(Project project) {
		return projectDao.createProject(project);
	}

	@Override
	public List<Project> search(ProjectSearch projectSearch) {
		return projectDao.query(projectParamResolver.getQueryBuilder(projectSearch).generateQuery());
	}

	@Override
	public Integer getMaxPage(ProjectSearch projectSearch) {
		int maxPage = projectDao.count(projectParamResolver.getQueryBuilder(projectSearch).generateCountQuery());

		if (maxPage % ProjectParamResolver.ITEMS_PER_PAGE == 0 && maxPage != 0)
			maxPage /= ProjectParamResolver.ITEMS_PER_PAGE;
		else
			maxPage = maxPage / ProjectParamResolver.ITEMS_PER_PAGE + 1;

		return maxPage;
	}

	@Override
	public List<Project> getStudentProjects(Long userId) {
		return projectDao.getStudentProjects(userId);
	}
	
	@Override
	public List<Project> getMentorProjects(Long userId) {
		return projectDao.getMentorProjects(userId);
	}
}
