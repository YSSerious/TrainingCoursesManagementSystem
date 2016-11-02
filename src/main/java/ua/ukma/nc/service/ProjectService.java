package ua.ukma.nc.service;

import ua.ukma.nc.entity.Project;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ProjectService {
    Project getById(Long id);

    int deleteProject(Project project);

    int updateProject(Project project);

    List<Project> getAll();

    int createProject(Project project);
}
