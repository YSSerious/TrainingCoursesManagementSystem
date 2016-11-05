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
import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.impl.real.StatusImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class StatusDaoTest {

    @Autowired
    private StatusDao statusDao;

    @Test
    public void getByIdTest() {
        Status status = statusDao.getById(1L);
        Assert.assertNotNull(status);
    }

    @Test
    public void getAllTest() {
        List<Status> statuses = statusDao.getAll();
        Assert.assertNotNull(statuses);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Status status = statusDao.getById(1L);
        status.setDescription("new description");
        Assert.assertEquals(1, statusDao.updateStatus(status));
        Assert.assertEquals(status.getDescription(), statusDao.getById(status.getId()).getDescription());
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void deleteTest() {
//        Status status = statusDao.getById(1L);
//        Assert.assertEquals(1, statusDao.deleteStatus(status));
//    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Status status = new StatusImpl("newTitle","newDescription");
        Assert.assertEquals(1, statusDao.createStatus(status));
        Assert.assertEquals(status.getDescription(), statusDao.getByTitle("newTitle").getDescription());
    }
}
