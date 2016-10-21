package ua.ukma.nc.service;

import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 15.10.2016.
 */
public interface UserService {

    User getByEmail(String email);

    User getById(Long id);

    boolean isExist(User user);

    int deleteUser(User user);

    int updateUser(User user);

    List<User> getAll();

    int createUser(User user);
}
