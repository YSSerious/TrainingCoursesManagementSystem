package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.dao.StudentStatusDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.proxy.RoleProxy;
import ua.ukma.nc.entity.impl.proxy.StatusProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.UserImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Repository
public class UserDaoImpl implements UserDao {

	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StudentStatusDao studentStatusDao;
	
	@Autowired
	private StatusDao statusDao;

	@Autowired
	private ApplicationContext appContext;

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
			user.setRoles(getRoles(resultSet.getLong("id")));
			user.setStudentStatus(new StudentStatus(appContext.getBean(UserProxy.class, resultSet.getLong("id")),
					appContext.getBean(StatusProxy.class, resultSet.getLong("id_status"))));
			return user;
		}
	}

	private static final String MENTOR_CHECK = "SELECT EXISTS (SELECT * FROM tcms.user WHERE id = ? AND id IN (SELECT id_user FROM tcms.user_group WHERE id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = (SELECT id FROM tcms.user WHERE email = ?)) AND id_group NOT IN (SELECT id_group FROM tcms.status_log WHERE id_student IN (SELECT id FROM tcms.user WHERE email = ?))))";

	private static final String GET_ALL = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student";

	private static final String GET_BY_ID = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id = ?";

	private static final String GET_BY_EMAIL = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE email=?";

	private static final String COUNT = "SELECT COUNT(*) FROM tcms.user";

	private static final String DELETE_ROLE = "DELETE FROM tcms.user_role WHERE id_user = ? AND id_role = ?";

	private static final String DELETE_USER = "DELETE FROM tcms.user WHERE id = ?";

	private static final String CREATE_USER = " INSERT INTO tcms.user (email, first_name, second_name, last_name, password, is_active) VALUES (?,?,?,?,?,?)";

	private static final String UPDATE_USER = "UPDATE tcms.user SET email = ?, first_name  = ?, second_name = ?, last_name = ?, password = ?, is_active = ? WHERE id = ?";

	private static final String GET_ROLES_BY_ID = "SELECT id_user, id_role FROM tcms.user_role WHERE id_user = ?";

	private static final String IS_EXIST = "SELECT EXISTS (SELECT email from tcms.user where email = ?)";

	private static final String ADD_ROLES = "insert into tcms.user_role (id_user, id_role) values (?, ?)";

	private static final String SET_STUDENT_STATUS = "insert into student_status (id_student, id_status) values (?, ?)";

	private static final String HAS_REVIEWS = "SELECT EXISTS (SELECT * FROM tcms.meeting_review WHERE id_student = ? AND id_meeting IN (SELECT id FROM tcms.meeting WHERE id_group = ?))";

	private static final String GET_BY_NAME = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE (first_name LIKE '";

	private static final String GET_BY_FIRST_NAME = "first_name LIKE '";

	private static final String GET_BY_LAST_NAME = "last_name LIKE '";

	private static final String GET_BY_SECOND_NAME = "second_name LIKE '";

	private static final String GET_STUDENTS_BY_PROJECT_ID = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT DISTINCT(id_student) FROM tcms.status_log WHERE id_group IN (SELECT id FROM tcms.group WHERE id_project = ?))";

	private static final String GET_STUDENTS_BY_GROUP_ID = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT DISTINCT(id_student) FROM tcms.status_log WHERE id_group = ?)";
	
	private static final String GET_STUDENTS_BY_PROJECT_NAME = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT id_user FROM tcms.user_group WHERE id_group IN (SELECT id FROM tcms.group WHERE id_project IN (SELECT id FROM tcms.project WHERE name LIKE '";

	private static final String GET_STUDENTS_BY_GROUP_NAME =   "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT id_user FROM tcms.user_group WHERE id_group IN (SELECT id FROM tcms.group WHERE name LIKE '";

	private static final String COUNT_GROUP = "SELECT COUNT(*) FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT id_user FROM tcms.user_group WHERE id_group IN (SELECT id FROM tcms.group WHERE name LIKE '";
	
	private static final String COUNT_PROJECT = "SELECT COUNT(*) FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT id_user FROM tcms.user_group WHERE id_group IN (SELECT id FROM tcms.group WHERE id_project IN (SELECT id FROM tcms.project WHERE name LIKE '";
	
	private static final String COUNT_NAME = "SELECT COUNT(*) FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE (first_name LIKE '";

	private static final String HAS_STUDENT_PROJECTS = "SELECT EXISTS (SELECT * FROM tcms.status_log WHERE id_student = ?)";
	
	private static final String HAS_MENTOR_PROJECTS = "SELECT EXISTS (SELECT * FROM tcms.user_group WHERE id_user = ? AND id_group NOT IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?))";
	
	private static final String HAS_HR_REVIEWS = "SELECT EXISTS (SELECT * FROM tcms.final_review WHERE id_employee = ? AND (type = 'G' OR type = 'T'))";

	private static final String GET_BY_ROLE = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id IN (SELECT id_user FROM tcms.user_role WHERE id_role = ? OR id_role = ? OR id_role = ? OR id_role = ?)";

	private static final String GET_INACTIVE_STUDENTS = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student LEFT JOIN tcms.user_role ur ON tcms.user.id=ur.id_user WHERE ss.id_status = 1 AND ur.id_role = 4";

	private static final String GET_FREE_MENTORS = "SELECT us.*, ss.id_status FROM tcms.user us LEFT JOIN tcms.student_status ss ON us.id=ss.id_student LEFT JOIN tcms.user_role ur ON us.id=ur.id_user LEFT JOIN tcms.user_group ug ON us.id = ug.id_user LEFT JOIN tcms.group gr ON ug.id_group = gr.id LEFT JOIN tcms.project pr ON pr.id = gr.id_project WHERE ur.id_role = 2 AND (pr.finish is null or pr.finish < current_timestamp)";


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
	public int count() {
		return jdbcTemplate.queryForObject(COUNT, int.class);
	}

	@Override
	public int updateUser(User user) {
		log.info("Updating user with id = {}", user.getId());
		return jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getFirstName(), user.getSecondName(),
				user.getLastName(), user.getPassword(), user.isActive(), user.getId());
	}

	@Override
	public List<User> getAll() {
		log.info("Getting all users");
		return jdbcTemplate.query(GET_ALL, new UserMapper());
	}

	@Override
	public List<User> getSome(int limit, int offset) {
		log.info("Getting all users");
		return jdbcTemplate.query(GET_ALL + " LIMIT " + limit + " OFFSET " + offset, new UserMapper());
	}

	@Override
	public int createUser(User user) {
		log.info("Create new user with email = {}", user.getEmail());
		return jdbcTemplate.update(CREATE_USER, user.getEmail(), user.getFirstName(), user.getSecondName(),
				user.getLastName(), user.getPassword(), user.isActive());
	}

	@Override
	public boolean isExist(User user) {
		log.info("Is exist this user {} ?", user);
		return jdbcTemplate.queryForObject(IS_EXIST, Boolean.class, user.getEmail());
	}

	@Override
	public int addRole(User user, Role role) {			
		return jdbcTemplate.update(ADD_ROLES, user.getId(), role.getId());
	}

	@Override
	public int setStatus(User user, Status status) {
		return jdbcTemplate.update(SET_STUDENT_STATUS, user.getId(), status.getId());
	}

	private List<Role> getRoles(Long userID) {
		log.info("Getting all roles with user id = {}", userID);
		return jdbcTemplate.query(GET_ROLES_BY_ID, new UserRoleMapper(), userID);
	}

	public class UserRoleMapper implements RowMapper<Role> {
		public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Role role = appContext.getBean(RoleProxy.class, resultSet.getLong("id_role"));
			return role;
		}
	}

	@Override
	public boolean canView(String mentorEmail, Long studentId) {
		log.info("Is exist this user {} ?");
		return jdbcTemplate.queryForObject(MENTOR_CHECK, Boolean.class, studentId, mentorEmail, mentorEmail);

	}

	@Override
	public void deleteRole(User user, Long id) {
		jdbcTemplate.update(DELETE_ROLE, user.getId(), id);
	}

	@Override
	public boolean hasReviews(Long studentId, Long groupId) {
		return jdbcTemplate.queryForObject(HAS_REVIEWS, Boolean.class, studentId, groupId);
	}

	@Override
	public List<User> getByName(String name, int limit, int offset) {
		log.info("Getting all users with name=" + name);
		return jdbcTemplate.query(
				GET_BY_NAME + name + "%' OR " + GET_BY_LAST_NAME + name + "%' OR " + GET_BY_SECOND_NAME + name + "%')"+ " LIMIT " + limit + " OFFSET " + offset,
				new UserMapper());
	}

	@Override
	public List<User> getByName(String name, String secondName, String lastName, int limit, int offset) {
		log.info("Getting all users with name=" + name);
		return jdbcTemplate.query(GET_BY_NAME + name + "%' AND " + GET_BY_LAST_NAME + lastName + "%' AND "
				+ GET_BY_SECOND_NAME + secondName + "%')"+ " LIMIT " + limit + " OFFSET " + offset, new UserMapper());
	}

	@Override
	public List<User> getByName(String value1, String value2, int limit, int offset) {
		log.info("Getting all users with name=" + value1);
		return jdbcTemplate.query(
				GET_BY_NAME + value1 + "%' AND " + GET_BY_LAST_NAME + value2 + "%') OR (" + GET_BY_FIRST_NAME + value2 +"%' AND "
						+ GET_BY_LAST_NAME + value1 + "%') OR (" + GET_BY_FIRST_NAME  + value1 +"%' AND " + GET_BY_SECOND_NAME
						+ value2 + "%') OR (" + GET_BY_FIRST_NAME + value2 +"%' AND " + GET_BY_SECOND_NAME + value1
				+"%') OR (" + GET_BY_LAST_NAME + value1 +"%' AND " + GET_BY_SECOND_NAME + value2 + "%') OR (" + GET_BY_LAST_NAME
						+ value2 +"%' AND " + GET_BY_SECOND_NAME + value1+"%')"+ " LIMIT " + limit + " OFFSET " + offset,
				new UserMapper());
	}

	public List<User> studentsByProjectId(Long projectId) {
		log.info("Getting all users from project with id=" + projectId);
		return jdbcTemplate.query(GET_STUDENTS_BY_PROJECT_ID, new UserMapper(), projectId);
	}

	@Override
	public List<User> studentsByGroupId(Long groupId) {
		log.info("Getting all users from group with id=" + groupId);
		return jdbcTemplate.query(GET_STUDENTS_BY_GROUP_ID, new UserMapper(), groupId);
	}
	
	@Override
	public List<User> studentsByProjectName(String name, int limit, int offset) {
		log.info("Getting all users from project with name=" + name);
		return jdbcTemplate.query(GET_STUDENTS_BY_PROJECT_NAME+name+"%')))"+ " LIMIT " + limit + " OFFSET " + offset, new UserMapper());
	}

	@Override

	public List<User> studentsByGroupName(String name, int limit, int offset) {
		log.info("Getting all users from group with name=" + name);
		return jdbcTemplate.query(GET_STUDENTS_BY_GROUP_NAME+name+"%'))"+ " LIMIT " + limit + " OFFSET " + offset, new UserMapper());
	}

	@Override
	public int countGroup(String name) {
		return jdbcTemplate.queryForObject(COUNT_GROUP+name+"%'))", int.class);
	}
	
	@Override
	public int countProject(String name) {
		return jdbcTemplate.queryForObject(COUNT_PROJECT+name+"%')))", int.class);
	}
	
	@Override
	public int countName(String name) {
		return jdbcTemplate.queryForObject(COUNT_NAME+name+"%')", int.class);
	}
	
	@Override
	public int countHalfName(String value1, String value2) {
		return jdbcTemplate.queryForObject(COUNT_NAME+ value1 + "%' AND " + GET_BY_LAST_NAME + value2 + "%') OR (" + GET_BY_FIRST_NAME + value2 +"%' AND "
				+ GET_BY_LAST_NAME + value1 + "%') OR (" + GET_BY_FIRST_NAME  + value1 +"%' AND " + GET_BY_SECOND_NAME
				+ value2 + "%') OR (" + GET_BY_FIRST_NAME + value2 +"%' AND " + GET_BY_SECOND_NAME + value1
		+"%') OR (" + GET_BY_LAST_NAME + value1 +"%' AND " + GET_BY_SECOND_NAME + value2 + "%') OR (" + GET_BY_LAST_NAME
				+ value2 +"%' AND " + GET_BY_SECOND_NAME + value1+"%')", int.class);
	}
	
	@Override
	public int countFullName(String name, String secondName, String lastName) {
		return jdbcTemplate.queryForObject(COUNT_NAME+ name + "%' AND " + GET_BY_LAST_NAME + lastName + "%' AND "
				+ GET_BY_SECOND_NAME + secondName + "%')", int.class);
	}
	

	public boolean hasStudentProjects(Long studentId) {
		return jdbcTemplate.queryForObject(HAS_STUDENT_PROJECTS, Boolean.class, studentId);
	}

	@Override
	public boolean hasMentorProjects(Long mentorId) {
		return jdbcTemplate.queryForObject(HAS_MENTOR_PROJECTS, Boolean.class, mentorId, mentorId);
	}

	@Override
	public boolean hasHRReviews(Long hrId) {
		return jdbcTemplate.queryForObject(HAS_HR_REVIEWS, Boolean.class, hrId);
	}

	@Override

	public List<User> getByRole(int role1, int role2, int role3, int role4){
		return jdbcTemplate.query(GET_BY_ROLE, new UserMapper(), role1, role2, role3, role4);
	}
	public List<User> getInactiveStudents() {
		log.info("Getting all inactive students");
		return jdbcTemplate.query(GET_INACTIVE_STUDENTS, new UserMapper());

	}

	@Override
	public List<User> getFreeMentors() {
		log.info("Getting all mentors");
		return jdbcTemplate.query(GET_FREE_MENTORS,new UserMapper());
	}
}
