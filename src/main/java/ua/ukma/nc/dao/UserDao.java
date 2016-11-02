package ua.ukma.nc.dao;

import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 15.10.2016.
 */
public interface UserDao {

    User getByEmail(String email);

    User getById(Long id);

    int deleteUser(User user);

    int updateUser(User user);

    List<User> getAll();

    int createUser(User user);

    boolean isExist(User user);

}
