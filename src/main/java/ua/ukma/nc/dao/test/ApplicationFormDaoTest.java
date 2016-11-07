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
import ua.ukma.nc.dao.ApplicationFormDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.dao.impl.ApplicationFormDaoImpl;
import ua.ukma.nc.entity.ApplicationForm;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ApplicationFormDaoTest {
    @Autowired
    private ApplicationFormDao applicationFormDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void getByIdTest() {
        ApplicationForm applicationForm= applicationFormDao.getByUserId(41L);
        Assert.assertNotNull(applicationForm);
    }

    @Test
    public void getAllTest() {
        List<ApplicationForm> all= applicationFormDao.getAll();
        Assert.assertNotNull(all);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        ApplicationForm applicationForm= applicationFormDao.getByUserId(41L);
        applicationForm.setPhotoScope("newPhotoScope");
        Assert.assertEquals(1, applicationFormDao.updateApplicationForm(applicationForm));
        Assert.assertEquals(applicationForm.getPhotoScope(), applicationFormDao.getByUserId(applicationForm.getUser().getId()).getPhotoScope());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        ApplicationForm applicationForm = applicationFormDao.getByUserId(41L);
        Assert.assertEquals(1, applicationFormDao.deleteApplicationForm(applicationForm));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        User user = userDao.getById(42L);
        ApplicationForm applicationForm = new ApplicationForm(user, "photoScopeee");
        Assert.assertEquals(1, applicationFormDao.createApplicationForm(applicationForm));
    }
}
