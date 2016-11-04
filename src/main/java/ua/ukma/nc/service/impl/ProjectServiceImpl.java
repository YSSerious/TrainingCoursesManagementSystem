package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.service.ProjectService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project getById(Long id) {
        return projectDao.getById(id);
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
	public List<Project> query(String query) {
		return projectDao.query(query);
	}

	@Override
	public Integer count(String query) {
		return projectDao.count(query);
	}
}
