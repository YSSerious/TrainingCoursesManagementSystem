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
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;

import java.sql.Date;
import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ProjectDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Test
    public void getByIdTest() {
        Project project = projectDao.getById(1L);
        Assert.assertNotNull(project);
    }

    @Test
    public void getAllTest() {
        List<Project> projects = projectDao.getAll();
        Assert.assertNotNull(projects);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Project project = projectDao.getById(1L);
        project.setName("newName");
        Assert.assertEquals(1, projectDao.updateProject(project));
        Assert.assertEquals(project.getName(), projectDao.getById(project.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Project project = projectDao.getById(1L);
        Assert.assertEquals(1, projectDao.deleteProject(project));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Project project = new ProjectImpl("newTitle","newDescription", new Date(1), new Date(2));
        Assert.assertEquals(1, projectDao.createProject(project));
    }
}
