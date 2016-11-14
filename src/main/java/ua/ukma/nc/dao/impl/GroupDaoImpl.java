package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ApplicationContext appContext;

    public class GroupMapper implements RowMapper<Group> {
        public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Group group = new GroupImpl();
            group.setId(resultSet.getLong("id"));
            group.setName(resultSet.getString("name"));
            group.setProject(appContext.getBean(ProjectProxy.class, resultSet.getLong("id_project")));
            group.setUsers(getUsers(resultSet.getLong("id")));
            return group;
        }
    }
    private static final String GET_BY_PROJECT_ID = "SELECT id, id_project, name FROM tcms.group WHERE id_project = ?";

    private static final String GET_ALL = "SELECT id, id_project, name FROM tcms.group";

    private static final String GET_BY_ID = "SELECT id, id_project, name FROM tcms.group WHERE id = ?";

    private static final String DELETE_GROUP = "DELETE FROM tcms.group WHERE id = ?";

    private static final String CREATE_GROUP = "INSERT INTO tcms.group (id_project, name) VALUES (?,?)";

    private static final String UPDATE_GROUP = "UPDATE tcms.group SET id_project = ?, name = ? WHERE id = ?";

    private static final String GET_USERS_BY_ID = "SELECT id_user FROM tcms.user_group WHERE id_group = ?";
    
    private static final String GET_MENTORS = "select tcms.user_group.id_user from tcms.user_group inner join tcms.user_role on tcms.user_group.id_user = tcms.user_role.id_user where tcms.user_group.id_group = ? and tcms.user_role.id_role = 2";

    private static final String GET_STUDENTS = "select tcms.user_group.id_user from tcms.user_group inner join tcms.user_role on tcms.user_group.id_user = tcms.user_role.id_user where tcms.user_group.id_group = ? and tcms.user_role.id_role = 4";
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
    public List<Group> getByProjectId(Long projectId) {
        log.info("Getting all groups");
        return jdbcTemplate.query(GET_BY_PROJECT_ID, new GroupMapper(), projectId);
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
            User user = appContext.getBean(UserProxy.class, resultSet.getLong("id_user"));
            return user;
        }
    }

	@Override
	public List<User> getStudents(Long groupId) {
		return jdbcTemplate.query(GET_STUDENTS,new UserGroupMapper(),groupId);
	}

	@Override
	public List<User> getMentors(Long groupId) {
		return jdbcTemplate.query(GET_MENTORS,new UserGroupMapper(),groupId);
	}
}
