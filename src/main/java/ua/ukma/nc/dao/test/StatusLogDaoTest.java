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
import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.dao.StatusLogDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class StatusLogDaoTest {

    @Autowired
    private StatusLogDao statusLogDao;
    @Autowired
    private StatusDao statusDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void getByIdTest() {
        StatusLog statusLog= statusLogDao.getById(2L);
        Assert.assertNotNull(statusLog);
    }

    @Test
    public void getAllTest() {
        List<StatusLog> statusLogs= statusLogDao.getAll();
        Assert.assertNotNull(statusLogs);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        StatusLog statusLog= statusLogDao.getById(2L);
        statusLog.setCommentary("newCommentary");
        Assert.assertEquals(1, statusLogDao.updateStatusLog(statusLog));
        Assert.assertEquals(statusLog.getCommentary(), statusLogDao.getById(statusLog.getId()).getCommentary());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        StatusLog statusLog= statusLogDao.getById(2L);
        Assert.assertEquals(1, statusLogDao.deleteStatusLog(statusLog));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        StatusLog statusLog= new StatusLog();
        Status oldStatus = statusDao.getById(1L);
        Status newStatus = statusDao.getById(2L);
        User student = userDao.getById(40L);
        User employee = userDao.getById(41L);
        Group group = groupDao.getById(7L);
        statusLog.setStudent(student);
        statusLog.setEmployee(employee);
        statusLog.setGroup(group);
        statusLog.setOldStatus(oldStatus);
        statusLog.setNewStatus(newStatus);
        statusLog.setCommentary("Commentary");
        statusLog.setDate(new Timestamp(1L));
        Assert.assertEquals(1, statusLogDao.createStatusLog(statusLog));
    }
}
