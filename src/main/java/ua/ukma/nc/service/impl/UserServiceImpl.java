package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public boolean isExist(User user) {
        return userDao.isExist(user);
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
    public int createUser(User user) {
        return userDao.createUser(user);
    }
}
