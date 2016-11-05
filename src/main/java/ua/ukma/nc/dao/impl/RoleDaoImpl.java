package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.RoleDao;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.RoleImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class RoleMapper implements RowMapper<Role> {
        public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Role role = new RoleImpl();
            role.setId(resultSet.getLong("id"));
            role.setTitle(resultSet.getString("role"));
            role.setUsers(getUsers(resultSet.getLong("id")));
            return role;
        }
    }

    private static final String GET_ALL = "SELECT id, role FROM tcms.role";

    private static final String GET_BY_ID = "SELECT id, role FROM tcms.role WHERE id = ?";

    private static final String DELETE_ROLE = "DELETE FROM tcms.role WHERE id = ?";

    private static final String CREATE_ROLE = "INSERT INTO tcms.role (role) VALUES (?)";

    private static final String UPDATE_ROLE = "UPDATE tcms.role SET role = ? WHERE id = ?";

    private static final String GET_USERS_BY_ID = "SELECT id_user FROM tcms.user_role WHERE id_role = ?";

    @Override
    public Role getById(Long id) {
        log.info("Getting role with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new RoleMapper(), id);
    }

    @Override
    public int deleteRole(Role role) {
        log.info("Deleting role with id = {}", role.getId());
        return jdbcTemplate.update(DELETE_ROLE, role.getId());
    }

    @Override
    public int updateRole(Role role) {
        log.info("Updating role with id = {}", role.getId());
        return jdbcTemplate.update(UPDATE_ROLE, role.getTitle(), role.getId());
    }

    @Override
    public List<Role> getAll() {
        log.info("Getting all users");
        return jdbcTemplate.query(GET_ALL, new RoleMapper());
    }

    @Override
    public int createRole(Role role) {
        log.info("Create new role with title = {}", role.getTitle());
        return jdbcTemplate.update(CREATE_ROLE, role.getTitle());
    }

    private List<User> getUsers(Long roleID) {
        log.info("Getting all users with role id = {}", roleID);
        return jdbcTemplate.query(GET_USERS_BY_ID, new RoleUserMapper(), roleID);
    }

    public class RoleUserMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = appContext.getBean(UserProxy.class, resultSet.getLong("id_user"));
            return user;
        }
    }

    @Override
    public boolean isExist(Role role) {
        return false;
    }
}
