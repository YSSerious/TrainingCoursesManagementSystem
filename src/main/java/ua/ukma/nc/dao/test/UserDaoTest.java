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
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.UserImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUserByIdTest() {
        User user = userDao.getById(1L);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getStudentStatus());
        Assert.assertNotNull(user.getRoles());
        Assert.assertNotNull(user.getRoles().get(0).getTitle());
    }

    @Test
    public void getAllUsersTest() {
        List<User> user = userDao.getAll();
        Assert.assertNotNull(user);
    }

    @Test
    @Transactional
    @Rollback
    public void getUserByEmailAndCreateTest() {
        User user = new UserImpl("email", "first", "second", "last", "password", true);
        Assert.assertEquals(1, userDao.createUser(user));
        User user1 =  userDao.getByEmail("email");
        Assert.assertEquals(user.getEmail(),user1.getEmail());
        Assert.assertEquals(user.getFirstName(),user1.getFirstName());
        Assert.assertEquals(user.getSecondName(),user1.getSecondName());
        Assert.assertEquals(user.getLastName(),user1.getLastName());
        Assert.assertEquals(user.getPassword(),user1.getPassword());
        Assert.assertEquals(user.isActive(),user1.isActive());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserTest() {
        User user = userDao.getById(1L);
        user.setEmail("email");
        Assert.assertEquals(1, userDao.updateUser(user));
        Assert.assertEquals(user.getEmail(), userDao.getById(1L).getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserTest() {
        User user = userDao.getById(8L);
        Assert.assertEquals(1, userDao.deleteUser(user));
    }
}
