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
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.GroupImpl;

import java.sql.Date;
import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ProjectDao projectDao;

    @Test
    public void getByIdTest() {
        Group group= groupDao.getById(7L);
        Assert.assertNotNull(group);
    }

    @Test
    public void getAllTest() {
        List<Group> projects = groupDao.getAll();
        Assert.assertNotNull(projects);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Group group = groupDao.getById(7L);
        group.setName("newName");
        Assert.assertEquals(1, groupDao.updateGroup(group));
        Assert.assertEquals(group.getName(), groupDao.getById(group.getId()).getName());
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void deleteTest() {
//        Group group= groupDao.getById(7L);
//        Assert.assertEquals(1, groupDao.deleteGroup(group));
//    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Project project = projectDao.getById(1L);
        Group group = new GroupImpl();
        group.setProject(project);
        group.setName("newName");
        Assert.assertEquals(1, groupDao.createGroup(group));
    }
}
