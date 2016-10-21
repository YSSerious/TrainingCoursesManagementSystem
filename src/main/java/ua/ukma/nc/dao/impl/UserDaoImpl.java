package ua.ukma.nc.dao.impl;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new UserImpl();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("name"));
            user.setSecondName(resultSet.getString("message"));
            return user;
        }
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getById(Long id) {
        return jdbcTemplate.queryForObject("select id, name, message from test where id = ?", new UserMapper(), id);//Test query from test table.
    }

    @Override
    public boolean isExist(User user) {
        return false;
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public int createUser(User user) {
        return 0;
    }

}
