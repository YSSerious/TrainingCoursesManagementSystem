package ua.ukma.nc.dao;

import ua.ukma.nc.entity.ProjectAttachment;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ProjectAttachmentDao {
    ProjectAttachment getById(Long id);

    int deleteProjectAttachment(ProjectAttachment projectAttachment);

    int updateProjectAttachment(ProjectAttachment projectAttachment);

    List<ProjectAttachment> getAll();

    int createProjectAttachment(ProjectAttachment projectAttachment);

	List<ProjectAttachment> getAllById(Long id_project);
}
