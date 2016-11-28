package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.CriterionDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.proxy.CategoryProxy;
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;
import ua.ukma.nc.entity.impl.real.CriterionImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class CriterionDaoImpl implements CriterionDao{

    private static Logger log = LoggerFactory.getLogger(CriterionDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class CriterionMapper implements RowMapper<Criterion> {
        public Criterion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Criterion criterion = new CriterionImpl();
            criterion.setId(resultSet.getLong("id"));
            criterion.setTitle(resultSet.getString("name"));
            criterion.setCategory(appContext.getBean(CategoryProxy.class, resultSet.getLong("id_category")));
            return criterion;
        }
    }
    
    private static final String GET_BY_PROJECTS = "SELECT DISTINCT id, name, id_category FROM tcms.criterion WHERE id IN (SELECT id_criterion FROM tcms.project_criterion WHERE id_project IN ?)";
    
    private static final String GET_BY_PROJECT = "SELECT id, name, id_category FROM tcms.criterion WHERE id IN (SELECT id_criterion FROM tcms.project_criterion WHERE id_project = ?)";

    private static final String GET_ALL = "SELECT id, name, id_category FROM tcms.criterion";

    private static final String GET_BY_ID = "SELECT id, name, id_category FROM tcms.criterion WHERE id = ?";

    private static final String GET_BY_NAME = "SELECT id, name, id_category FROM tcms.criterion WHERE name = ?";

    private static final String DELETE_CRITERION = "DELETE FROM tcms.criterion WHERE id = ?";

    private static final String DELETE_BY_CATEGORY_ID = "DELETE FROM tcms.criterion WHERE id_category = ?";

    private static final String CREATE_CRITERION = "INSERT INTO tcms.criterion (name, id_category) VALUES (?,?)";

    private static final String UPDATE_CRITERION = "UPDATE tcms.criterion SET name = ?, id_category = ? WHERE id = ?";

    private static final String GET_PROJECTS_BY_ID = "SELECT id_project FROM tcms.project_criterion WHERE id_criterion = ?";
    
    private static final String GET_BY_MEETING = "SELECT id, name, id_category FROM tcms.criterion WHERE id IN (SELECT id_criterion FROM tcms.meeting_criterion WHERE id_meeting = ?)";

    private static final String IS_USED_IN_PROJECTS = "SELECT EXISTS (SELECT * from tcms.project_criterion where id_criterion = ?)";

    private static final String GET_PROJECT_UNUSED_CRITERIA = "select id, name, id_category FROM tcms.criterion " +
                                                                "where id not in (select id_criterion from tcms.project_criterion where id_project =?) order by name Asc";

    private static final String GET_MEETING_UNUSED_CRITERIA = "select id, name, id_category FROM tcms.criterion " +
            "where id not in (select id_criterion from tcms.meeting_criterion where id_meeting =?) and id " +
            " in (select id_criterion from tcms.project_criterion where id_project =?) order by name Asc";

    private static final String IS_RATED_IN_PROJECT = "select exists " +
                                                        "(select * from tcms.meeting_result where id_criterion = ? and id_meeting_review in \n" +
                                                        "(select id from tcms.meeting_review where id_meeting in \n" +
                                                        "(select id from tcms.meeting where id_group in \n" +
                                                        "(select id from tcms.group where id_project = ?))))";

    private static final String IS_RATED_IN_MEETING = "select exists (select * from tcms.meeting_result where id_criterion = ? and id_meeting_review in " +
                                                        "(select id from tcms.meeting_review where id_meeting =?))";

    private static final String IS_EXIST = "select exists (select * from tcms.criterion where name=?)";

    @Override
    public Criterion getById(Long id) {
        log.info("Getting criterion with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new CriterionMapper(), id);
    }

    @Override
    public Criterion getByName(String name) {
        log.info("Getting criterion with name = {}", name);
        return jdbcTemplate.queryForObject(GET_BY_NAME, new CriterionMapper(), name);
    }

    @Override
    public List<Criterion> getProjectUnusedCriteria(Long projectId) {
        log.info("Getting unused criterion in project = {}", projectId);
        return jdbcTemplate.query(GET_PROJECT_UNUSED_CRITERIA, new CriterionMapper(), projectId);
    }

    @Override
    public List<Criterion> getMeetingUnusedCriteria(Long meetingId, Long projectId) {
        log.info("Getting unused criterion in meeting = {}", meetingId);
        return jdbcTemplate.query(GET_MEETING_UNUSED_CRITERIA, new CriterionMapper(), meetingId, projectId);
    }

    @Override
    public boolean isRatedInProject(Long projectId, Criterion criterion) {
        log.info("Is criterion with id = {} rated throughout the project {}", criterion.getId(), projectId);
        return jdbcTemplate.queryForObject(IS_RATED_IN_PROJECT, boolean.class, criterion.getId(), projectId);
    }

    @Override
    public boolean isRatedInMeeting(Long meetingId, Criterion criterion) {
        log.info("Is criterion with id = {} rated throughout the meeting {}", criterion.getId(), meetingId);
        return jdbcTemplate.queryForObject(IS_RATED_IN_MEETING, boolean.class, criterion.getId(), meetingId);
    }

    @Override
    public int deleteCriterion(Criterion criterion) {
        log.info("Deleting criterion with id = {}", criterion.getId());
        return jdbcTemplate.update(DELETE_CRITERION, criterion.getId());
    }

    @Override
    public int deleteCriterion(Long id) {
        return jdbcTemplate.update(DELETE_CRITERION, id);
    }

    @Override
    public int deleteByCategoryId(Long id) {
        return jdbcTemplate.update(DELETE_BY_CATEGORY_ID, id);
    }

    @Override
    public int updateCriterion(Criterion criterion) {
        log.info("Updating criterion with id = {}", criterion.getId());
        return jdbcTemplate.update(UPDATE_CRITERION, criterion.getTitle(),criterion.getCategory().getId(), criterion.getId());
    }

    @Override
    public List<Criterion> getAll() {
        log.info("Getting all criterion");
        return jdbcTemplate.query(GET_ALL, new CriterionMapper());
    }

    @Override
    public int createCriterion(Criterion criterion) {
        log.info("Create new criterion with title = {}", criterion.getTitle());
        return jdbcTemplate.update(CREATE_CRITERION, criterion.getTitle(), criterion.getCategory().getId());
    }

	@Override
	public List<Criterion> getByProject(Long projectId) {
        log.info("Getting all criterion");
        return jdbcTemplate.query(GET_BY_PROJECT, new CriterionMapper(), projectId);
	}
	
	@Override
	public List<Criterion> getByMeeting(Long meetingId) {
        log.info("Getting all criterion");
        return jdbcTemplate.query(GET_BY_MEETING, new CriterionMapper(), meetingId);
	}


    @Override
    public int createCriterion(String title, Long categoryId) {
        log.info("Create new criterion with title = {}", title);
        return jdbcTemplate.update(CREATE_CRITERION, title, categoryId);
    }

    @Override
    public boolean isExist(String title) {
        return jdbcTemplate.queryForObject(IS_EXIST, Boolean.class, title);
    }

    @Override
    public boolean isExistInProjects(Long id) {
        return jdbcTemplate.queryForObject(IS_USED_IN_PROJECTS, Boolean.class, id);
    }

	@Override
	public List<Criterion> getByProjects(List<Long> projects) {
		log.info("Getting all criterion");
		String set =  projects.toString().replace('[', '(').replace(']', ')');
		String query = GET_BY_PROJECTS.replace("?", set);
        return jdbcTemplate.query(query, new CriterionMapper());
	
	}


//    private List<Project> getProjects(Long criterionID) {
//        log.info("Getting all projects with criterion id = {}", criterionID);
//        return jdbcTemplate.query(GET_PROJECTS_BY_ID, new ProjectCriterionMapper(), criterionID);
//    }
//
//    public class ProjectCriterionMapper implements RowMapper<Project> {
//        public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//            Project project = appContext.getBean(ProjectProxy.class, resultSet.getLong("id_project"));
//            return project;
//        }
//    }
}
