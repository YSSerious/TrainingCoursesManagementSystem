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
import ua.ukma.nc.dao.StudentStatusDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.UserImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class StudentStatusDaoTest {

    @Autowired
    private StudentStatusDao studentStatusDao;
    @Autowired
    private StatusDao statusDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void getByIdTest() {
        StudentStatus studentStatus = studentStatusDao.getByUserId(1L);
        Assert.assertNotNull(studentStatus.getStatus());
        Assert.assertNotNull(studentStatus.getStudent());
        Assert.assertNotNull(studentStatus.getStatus().getTitle());
        Assert.assertNotNull(studentStatus.getStudent().getId());
    }

    @Test
    public void getAllTest() {
        List<StudentStatus> studentStatuses = studentStatusDao.getAll();
        Assert.assertNotNull(studentStatuses);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        StudentStatus studentStatus = studentStatusDao.getByUserId(1L);
        Status status = statusDao.getById(4L);
        studentStatus.setStatus(status);
        Assert.assertEquals(1, studentStatusDao.updateStudentStatus(studentStatus));
        Assert.assertEquals(studentStatus.getStatus().getTitle(), studentStatusDao.getByUserId(1L).getStatus().getTitle());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        StudentStatus studentStatus = studentStatusDao.getByUserId(1L);
        Assert.assertEquals(1, studentStatusDao.deleteStudentStatus(studentStatus));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        userDao.createUser(new UserImpl("email", "first", "second", "last", "password", true));
        User student = userDao.getByEmail("email");
        Status status = statusDao.getById(4L);
        StudentStatus studentStatus = new StudentStatus();
        studentStatus.setStatus(status);
        studentStatus.setStudent(student);
        Assert.assertEquals(1, studentStatusDao.createStudentStatus(studentStatus));
        Assert.assertEquals(student.getId(), studentStatusDao.getByUserId(student.getId()).getStudent().getId());
    }
}
