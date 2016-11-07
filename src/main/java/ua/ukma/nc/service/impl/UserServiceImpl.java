package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.StatusImpl;
import ua.ukma.nc.service.UserService;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatusDao statusDao;

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }
    @Override
    public int count(){
    	return userDao.count();
    }

    @Override
    public boolean isExist(User user) {
        return userDao.isExist(user);
    }

    @Override
    public int addRole(User user, Role role) {
        return 0;
    }

    @Override
    public int setStatus(User user, Status status) {
        return 0;
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
    @Override
    public List<User> getSome(int limit, int offset) {
        return userDao.getSome(limit, offset);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createUser(User user, List<Role> roles) {
        userDao.createUser(user);
        User createdUser = userDao.getByEmail(user.getEmail());
        boolean isStudent = false;
        for (Role role : roles) {
            userDao.addRole(createdUser, role);
            if (role.getTitle().equalsIgnoreCase("ROLE_STUDENT"))
                isStudent = true;
        }
        if (isStudent)
            userDao.setStatus(createdUser, statusDao.getById(1L));
    }
}
