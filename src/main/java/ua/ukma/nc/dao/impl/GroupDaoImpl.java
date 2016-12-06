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
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.GroupImpl;

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
    
    private static final String CAN_VIEW_MENTOR = "SELECT EXISTS (SELECT id FROM tcms.group WHERE id = ? AND id IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?) AND id NOT IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?))";
    
    private static final String GET_BY_PROJECT_ID_MENTOR = "SELECT id, id_project, name FROM tcms.group WHERE id_project = ? AND id IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?) AND id NOT IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?)";
    
    private static final String GET_BY_PROJECT_ID = "SELECT id, id_project, name FROM tcms.group WHERE id_project = ?";

    private static final String GET_ALL = "SELECT id, id_project, name FROM tcms.group";

    private static final String GET_BY_ID = "SELECT id, id_project, name FROM tcms.group WHERE id = ?";

    private static final String GET_BY_NAME = "SELECT id, id_project, name FROM tcms.group WHERE name = ?";

    private static final String DELETE_GROUP = "DELETE FROM tcms.group WHERE id = ?";

    private static final String CREATE_GROUP = "INSERT INTO tcms.group (id_project, name) VALUES (?,?)";

    private static final String UPDATE_GROUP = "UPDATE tcms.group SET id_project = ?, name = ? WHERE id = ?";

    private static final String GET_USERS_BY_ID = "SELECT id_user FROM tcms.user_group WHERE id_group = ?";
    
    private static final String GET_STUDENTS = "SELECT DISTINCT id_student as id_user FROM tcms.status_log WHERE id_group = ?";
    
    private static final String GET_MENTORS = "SELECT DISTINCT id_user FROM tcms.user_group WHERE id_group = ? AND id_user NOT IN (SELECT id_student FROM tcms.status_log WHERE id_group = ?)";
    
    private static final String GET_STUDENTS_AMOUNT = "select COUNT(*) FROM (SELECT DISTINCT id_student FROM tcms.status_log WHERE id_group = ?) as students";
    
    private static final String REMOVE_MENTOR = "delete from tcms.user_group where id_group = ? and id_user = ?";
    
    private static final String REMOVE_STUDENT = "delete from tcms.user_group where id_group = ? and id_user = ?";

    private static final String GET_BY_USER_PROJECT = "select * from tcms.group where id_project = ? and id in (select id_group from tcms.user_group where id_user = ?)";

    private static final String ADD_USER = "INSERT INTO tcms.user_group (id_group, id_user) VALUES (?,?)";



    @Override
    public Group getById(Long id) {
        log.info("Getting group with id = {}", id);
        List<Group> resultSet = jdbcTemplate.query(GET_BY_ID, new Object[] { id }, new GroupMapper());
        if (resultSet.isEmpty()) {
            return null;
        }
        return resultSet.get(0);
    }

    @Override
    public Group getByName(String name) {
        List<Group> resultSet = jdbcTemplate.query(GET_BY_NAME, new Object[] { name }, new GroupMapper());
        if (resultSet.isEmpty()) {
            return null;
        }
        return resultSet.get(0);
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

    @Override
    public Group getByUserProject(Long userId, Long projectId) {
        log.info("Getting group by user = {} & project id = {}", userId, projectId);
        return jdbcTemplate.queryForObject(GET_BY_USER_PROJECT, new GroupMapper(), projectId, userId);
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
	public Long getStudentsAmount(Long groupId) {
		return jdbcTemplate.queryForObject(GET_STUDENTS_AMOUNT, Long.class, groupId);
	}
        
	@Override
	public List<User> getMentors(Long groupId) {
		return jdbcTemplate.query(GET_MENTORS,new UserGroupMapper(),groupId, groupId);
	}

	@Override
	public int removeMentor(Long groupId,Long userId) {
		return jdbcTemplate.update(REMOVE_MENTOR,groupId,userId);
	}

	@Override
	public int removeStudent(Long groupId, Long userId) {
		return jdbcTemplate.update(REMOVE_STUDENT,groupId,userId);
	}

    @Override
    public void addUser(Long groupId, Long userId) {
        log.info("Adding user with id {} to the group with id {}", userId, groupId);
        jdbcTemplate.update(ADD_USER, groupId, userId);
    }

	@Override
	public List<Group> getByProjectId(Long projectId, Long mentorId) {
		log.info("Getting all groups");
        return jdbcTemplate.query(GET_BY_PROJECT_ID_MENTOR, new GroupMapper(), projectId, mentorId, mentorId);
	}

	@Override
	public boolean canView(Long mentorId, Long groupId) {
		return jdbcTemplate.queryForObject(CAN_VIEW_MENTOR, Boolean.class, groupId, mentorId, mentorId);
	}

}
