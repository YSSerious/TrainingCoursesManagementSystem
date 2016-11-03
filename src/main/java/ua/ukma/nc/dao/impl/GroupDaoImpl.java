package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.entity.impl.real.ProjectImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class GroupDaoImpl implements GroupDao{

    private static Logger log = LoggerFactory.getLogger(GroupDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class GroupMapper implements RowMapper<Group> {
        public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Group group = new GroupImpl();
            group.setId(resultSet.getLong("id"));
            group.setName(resultSet.getString("name"));
            group.setProject(new ProjectProxy(resultSet.getLong("id_project")));
            group.setUsers(getUsers(resultSet.getLong("id")));
            return group;
        }
    }

    private static final String GET_ALL = "SELECT id, id_project, name FROM tcms.group";

    private static final String GET_BY_ID = "SELECT id, id_project, name FROM tcms.group WHERE id = ?";

    private static final String DELETE_GROUP = "DELETE FROM tcms.group WHERE id = ?";

    private static final String CREATE_GROUP = "INSERT INTO tcms.group (id_project, name) VALUES (?,?)";

    private static final String UPDATE_GROUP = "UPDATE tcms.group SET id_project = ?, name = ? WHERE id = ?";

    private static final String GET_USERS_BY_ID = "SELECT id_user FROM tcms.user_group WHERE id_group = ?";

    @Override
    public Group getById(Long id) {
        log.info("Getting group with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new GroupMapper(), id);
    }

    @Override
    public int deleteGroup(Group group) {
        log.info("Deleting group with id = {}", group.getId());
        return jdbcTemplate.update(DELETE_GROUP, group.getId());
    }

    @Override
    public int updateGroup(Group group) {
        log.info("Updating group with id = {}", group.getId());
        return jdbcTemplate.update(UPDATE_GROUP,  group.getProject().getId(), group.getName(), group.getId());
    }

    @Override
    public List<Group> getAll() {
        log.info("Getting all groups");
        return jdbcTemplate.query(GET_ALL, new GroupMapper());
    }

    @Override
    public int createGroup(Group group) {
        log.info("Create new group with name = {}", group.getName());
        return jdbcTemplate.update(CREATE_GROUP, group.getProject().getId(),group.getName());
    }

    private List<User> getUsers(Long groupID) {
        log.info("Getting all users with group id = {}", groupID);
        return jdbcTemplate.query(GET_USERS_BY_ID, new UserGroupMapper(), groupID);
    }

    public class UserGroupMapper implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new UserProxy();
            user.setId(resultSet.getLong("id_user"));
            return user;
        }
    }
}
