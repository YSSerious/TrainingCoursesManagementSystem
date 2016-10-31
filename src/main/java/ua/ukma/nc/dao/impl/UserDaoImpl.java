package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.UserImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Repository
public class UserDaoImpl implements UserDao{

    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new UserImpl();
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setSecondName(resultSet.getString("second_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPassword(resultSet.getString("password"));
            user.setActive(resultSet.getBoolean("is_active"));
            //
            //
            return user;
        }
    }

    private static final String GET_ALL = "select id, email, first_name, second_name, last_name, password, is_active from tcms.user";

    private static final String GET_BY_ID = "select id, email, first_name, second_name, last_name, password, is_active from tcms.user where id = ?";

    private static final String GET_BY_EMAIL = "select id, email, first_name, second_name, last_name, password, is_active from tcms.user where email=?";

    private static final String DELETE_USER = "delete from tcms.user where id = ?";

    private static final String CREATE_USER = " insert into tcms.user (email, first_name, second_name, last_name, password, is_active) values (?,?,?,?,?,?)";

    private static final String UPDATE_USER = "UPDATE tcms.user SET email = ?, first_name  = ?, second_name = ?, last_name = ?, password = ?, is_active = ? WHERE id = ?";

    @Override
    public User getByEmail(String email) {
        log.info("Getting user with email = {}", email);
        return jdbcTemplate.queryForObject(GET_BY_EMAIL, new UserMapper(), email);
    }

    @Override
    public User getById(Long id) {
        log.info("Getting user with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new UserMapper(), id);
    }

    @Override
    public int deleteUser(User user) {
        log.info("Deleting user with id = {}", user.getId());
        return jdbcTemplate.update(DELETE_USER, user.getId());
    }

    @Override
    public int updateUser(User user) {
        log.info("Updating user with id = {}", user.getId());
        return jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getFirstName(), user.getSecondName(), user.getLastName(), user.getPassword(), user.isActive(), user.getId());
    }

    @Override
    public List<User> getAll() {
        log.info("Getting all users");
        return jdbcTemplate.query(GET_ALL, new UserMapper());
    }

    @Override
    public int createUser(User user) {
        log.info("Create new user with email = {}", user.getEmail());
        return jdbcTemplate.update(CREATE_USER, user.getEmail(), user.getFirstName(), user.getSecondName(), user.getLastName(), user.getPassword(), user.isActive());
    }

    @Override
    public boolean isExist(User user) {
        return false;
    }

}
