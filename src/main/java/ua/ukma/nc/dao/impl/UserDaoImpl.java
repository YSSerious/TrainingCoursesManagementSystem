package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.proxy.RoleProxy;
import ua.ukma.nc.entity.impl.proxy.StatusProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.RoleImpl;
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

	private static final String GET_ALL = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student";

	private static final String GET_BY_ID = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE id = ?";

	private static final String GET_BY_EMAIL = "SELECT id, email, first_name, second_name, last_name, password, is_active, ss.id_status FROM tcms.user LEFT JOIN tcms.student_status ss ON tcms.user.id=ss.id_student WHERE email=?";

	private static final String COUNT = "SELECT COUNT(*) FROM tcms.user";
	
	private static final String DELETE_USER = "DELETE FROM tcms.user WHERE id = ?";

	private static final String CREATE_USER = " INSERT INTO tcms.user (email, first_name, second_name, last_name, password, is_active) VALUES (?,?,?,?,?,?)";

	private static final String UPDATE_USER = "UPDATE tcms.user SET email = ?, first_name  = ?, second_name = ?, last_name = ?, password = ?, is_active = ? WHERE id = ?";

	private static final String GET_ROLES_BY_ID = "SELECT id_user, id_role FROM tcms.user_role WHERE id_user = ?";

	private static final String IS_EXIST = "SELECT EXISTS (SELECT email from tcms.user where email = ?)";

	private static final String ADD_ROLES = "insert into user_role (id_user, id_role) values (?, ?)";

	private static final String SET_STUDENT_STATUS = "insert into student_status (id_student, id_status) values (?, ?)";

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
	public int count(){
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

}
