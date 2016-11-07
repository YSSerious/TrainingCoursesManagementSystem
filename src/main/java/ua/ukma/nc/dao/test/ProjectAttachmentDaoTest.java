package ua.ukma.nc.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.config.TestConfig;
import ua.ukma.nc.dao.ProjectAttachmentDao;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.ProjectAttachment;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ProjectAttachmentDaoTest {

    @Autowired
    private ProjectAttachmentDao projectAttachmentDao;
    @Autowired
    private ProjectDao projectDao;

    @Test
    public void getByIdTest() {
        ProjectAttachment projectAttachment= projectAttachmentDao.getById(1L);
        Assert.assertNotNull(projectAttachment);
    }

    @Test
    public void getAllTest() {
        List<ProjectAttachment> projectAttachments = projectAttachmentDao.getAll();
        Assert.assertNotNull(projectAttachments);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        ProjectAttachment projectAttachment = projectAttachmentDao.getById(1L);
        projectAttachment.setName("newName");
        Assert.assertEquals(1, projectAttachmentDao.updateProjectAttachment(projectAttachment));
        Assert.assertEquals(projectAttachment.getName(), projectAttachmentDao.getById(projectAttachment.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        ProjectAttachment projectAttachment= projectAttachmentDao.getById(1L);
        Assert.assertEquals(1, projectAttachmentDao.deleteProjectAttachment(projectAttachment));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        ProjectAttachment projectAttachment = new ProjectAttachment();
        Project project = projectDao.getById(1L);
        projectAttachment.setName("name");
        projectAttachment.setProject(project);
        projectAttachment.setAttachmentScope("attScope");
        Assert.assertEquals(1, projectAttachmentDao.createProjectAttachment(projectAttachment));
    }
}
