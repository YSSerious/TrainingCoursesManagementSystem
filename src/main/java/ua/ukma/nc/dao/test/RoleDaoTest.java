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
import ua.ukma.nc.dao.RoleDao;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.impl.real.RoleImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class RoleDaoTest {
    @Autowired
    private RoleDao roleDao;

    @Test
    public void getByIdTest() {
        Role role = roleDao.getById(1L);
        Assert.assertNotNull(role);
    }

    @Test
    public void getAllTest() {
        List<Role> roles = roleDao.getAll();
        Assert.assertNotNull(roles);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Role role= roleDao.getById(1L);
        role.setTitle("newTitle");
        Assert.assertEquals(1, roleDao.updateRole(role));
        Assert.assertEquals(role.getTitle(), roleDao.getById(role.getId()).getTitle());
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void deleteTest() {
//        Role role= roleDao.getById(1L);
//        Assert.assertEquals(1, roleDao.deleteRole(role));
//    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Role role= new RoleImpl("newTitle");
        Assert.assertEquals(1, roleDao.createRole(role));
    }

}
