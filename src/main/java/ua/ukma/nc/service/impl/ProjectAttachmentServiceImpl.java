package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.ProjectAttachmentDao;
import ua.ukma.nc.entity.ProjectAttachment;
import ua.ukma.nc.service.ProjectAttachmentService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class ProjectAttachmentServiceImpl implements ProjectAttachmentService{

    @Autowired
    ProjectAttachmentDao projectAttachmentDao;

    @Override
    public ProjectAttachment getById(Long id) {
        return projectAttachmentDao.getById(id);
    }

    @Override
    public int deleteProjectAttachment(ProjectAttachment projectAttachment) {
        return projectAttachmentDao.deleteProjectAttachment(projectAttachment);
    }

    @Override
    public int updateProjectAttachment(ProjectAttachment projectAttachment) {
        return projectAttachmentDao.updateProjectAttachment(projectAttachment);
    }

    @Override
    public List<ProjectAttachment> getAll() {
        return projectAttachmentDao.getAll();
    }

    @Override
    public int createProjectAttachment(ProjectAttachment projectAttachment) {
        return projectAttachmentDao.createProjectAttachment(projectAttachment);
    }
}
