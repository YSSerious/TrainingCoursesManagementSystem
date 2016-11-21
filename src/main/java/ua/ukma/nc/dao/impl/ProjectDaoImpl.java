package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {

	private static Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class.getName());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public class ProjectMapper implements RowMapper<Project> {
		public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Project project = new ProjectImpl();
			project.setId(resultSet.getLong("id"));
			project.setName(resultSet.getString("name"));
			project.setDescription(resultSet.getString("description"));
			project.setStartDate(resultSet.getDate("start"));
			project.setFinishDate(resultSet.getDate("finish"));
			return project;
		}
	}

	public class IntegerMapper implements RowMapper<Integer> {
		public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return resultSet.getInt("QUANTITY");
		}
	}

	private static final String GET_MENTOR_PROJECTS = "SELECT * FROM tcms.project WHERE id IN (SELECT id_project FROM tcms.group WHERE tcms.group.id IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?)) AND id NOT IN (SELECT id_project FROM tcms.group WHERE tcms.group.id IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?)) ORDER by start DESC";
	
	private static final String GET_STUDENT_PROJECTS = "SELECT * FROM tcms.project WHERE id IN (SELECT id_project FROM tcms.group WHERE tcms.group.id IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?)) ORDER by start DESC";

	private static final String GET_ALL = "SELECT id, name, description, start, finish FROM tcms.project";

	private static final String GET_BY_ID = "SELECT id, name, description, start, finish FROM tcms.project WHERE id = ?";

	private static final String GET_BY_NAME = "SELECT id, name, description, start, finish FROM tcms.project WHERE name=trim(?)";

	private static final String DELETE_PROJECT = "DELETE FROM tcms.project WHERE id = ?";

	private static final String CREATE_PROJECT = "INSERT INTO tcms.project (name, description, start, finish) VALUES (?,?,?,?)";

	private static final String UPDATE_PROJECT = "UPDATE tcms.project SET name = ?, description = ?, start = ?, finish = ? WHERE id = ?";

	private static final String ADD_CRITERION = "INSERT INTO tcms.project_criterion (id_project, id_criterion) VALUES (?,?)";

	private static final String DELETE_PROJECT_CRITERION = "DELETE FROM tcms.project_criterion WHERE id_project = ? and id_criterion = ?";

	@Override
	public Project getById(Long id) {
		log.info("Getting project with id = {}", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, new ProjectMapper(), id);
	}

	@Override
	public Project getByName(String name) {
		log.info("Getting project with name = {}", name);
		List<Project> resultSet = jdbcTemplate.query(GET_BY_NAME, new Object[] { name }, new ProjectMapper());
		log.info("resultSet: " + resultSet);
		if (resultSet.isEmpty()) {
			return null;
		}
		return resultSet.get(0);
	}

	@Override
	public int deleteProject(Project project) {
		log.info("Deleting project with id = {}", project.getId());
		return jdbcTemplate.update(DELETE_PROJECT, project.getId());
	}

	@Override
	public int deleteProjectCriterion(Long projectId, Criterion criterion) {
		return jdbcTemplate.update(DELETE_PROJECT_CRITERION, projectId, criterion.getId());
	}

	@Override
	public int updateProject(Project project) {
		log.info("Updating project with id = {}", project.getId());
		return jdbcTemplate.update(UPDATE_PROJECT, project.getName(), project.getDescription(), project.getStartDate(),
				project.getFinishDate(), project.getId());
	}

	@Override
	public List<Project> getAll() {
		log.info("Getting all projects");
		return jdbcTemplate.query(GET_ALL, new ProjectMapper());
	}

	@Override
	public int createProject(Project project) {
		log.info("Create new project with name = {}", project.getName());
		return jdbcTemplate.update(CREATE_PROJECT, project.getName(), project.getDescription(), project.getStartDate(),
				project.getFinishDate());
	}

	@Override
	public int addCriteria(Long projectId, Criterion criterion) {
		return jdbcTemplate.update(ADD_CRITERION, projectId, criterion.getId());
	}

	@Override
	public List<Project> query(String query) {
		log.info("Getting query projects");
		return jdbcTemplate.query(query, new ProjectMapper());
	}

	@Override
	public Integer count(String query) {
		log.info("Getting query projects");
		return jdbcTemplate.queryForObject(query, new IntegerMapper());
	}

	@Override
	public List<Project> getStudentProjects(Long userId) {
		return jdbcTemplate.query(GET_STUDENT_PROJECTS, new ProjectMapper(), userId);
	}
	
	@Override
	public List<Project> getMentorProjects(Long userId) {
		return jdbcTemplate.query(GET_MENTOR_PROJECTS, new ProjectMapper(), userId, userId);
	}
}
