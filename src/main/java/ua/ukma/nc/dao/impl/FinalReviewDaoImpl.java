package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.FinalReviewDao;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class FinalReviewDaoImpl implements FinalReviewDao{

    private static Logger log = LoggerFactory.getLogger(FinalReviewDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class FinalReviewMapper implements RowMapper<FinalReview> {
        public FinalReview mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            FinalReview finalReview = new FinalReviewImpl();
            finalReview.setId(resultSet.getLong("id"));
            finalReview.setDate(resultSet.getTimestamp("date"));
            finalReview.setStudent(appContext.getBean(UserProxy.class, resultSet.getLong("id_student")));
            finalReview.setEmployee(appContext.getBean(UserProxy.class, resultSet.getLong("id_employee")));
            finalReview.setType(resultSet.getString("type"));
            finalReview.setProject(appContext.getBean(ProjectProxy.class, resultSet.getLong("id_project")));
            finalReview.setCommentary(resultSet.getString("commentary"));
            return finalReview;
        }
    }
    
    private static final String GET_BY_STUDENT = "SELECT * FROM tcms.final_review WHERE id_student = ? AND id_project = ? AND type = ?";
    
    private static final String EXISTS_BY_STUDENT_PROJECT_TYPE = "SELECT (EXISTS (SELECT * FROM tcms.final_review WHERE id_student = ? AND id_project = ? AND type = ?))";
    
    private static final String EXISTS_STUDENT_GROUP = "SELECT (EXISTS (SELECT * FROM tcms.final_review WHERE id_student = ? AND id_project = (SELECT id_project FROM tcms.group WHERE id = ?) AND type=?))";

    private static final String GET_ALL = "SELECT id, date, id_student, id_employee, type, id_project, commentary FROM tcms.final_review";

    private static final String GET_BY_ID = "SELECT id, date, id_student, id_employee, type, id_project, commentary FROM tcms.final_review WHERE id = ?";

    private static final String DELETE_FINAL_REVIEW = "DELETE FROM tcms.final_review WHERE id = ?";

    private static final String CREATE_FINAL_REVIEW = "INSERT INTO tcms.final_review (date, id_student, id_employee, type, id_project, commentary) VALUES (?,?,?,?,?,?)";

    private static final String UPDATE_FINAL_REVIEW = "UPDATE tcms.final_review SET date = ?, id_student = ?, id_employee = ?, type = ?, id_project = ?, commentary = ? WHERE id = ?";

    private static final String GET_NUM_BY_PROJECT_AND_TYPE = "SELECT COUNT(*) FROM tcms.final_review WHERE id_project=? AND type=?";
    @Override
    public FinalReview getById(Long id) {
        log.info("Getting final review with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new FinalReviewMapper(), id);
    }

    @Override
    public int deleteFinalReview(FinalReview finalReview) {
        log.info("Deleting final review with id = {}", finalReview.getId());
        return jdbcTemplate.update(DELETE_FINAL_REVIEW, finalReview.getId());
    }

    @Override
    public int updateFinalReview(FinalReview finalReview) {
        log.info("Updating final review with id = {}", finalReview.getId());
        return jdbcTemplate.update(UPDATE_FINAL_REVIEW, finalReview.getDate(), finalReview.getStudent().getId(), finalReview.getEmployee().getId(),
                finalReview.getType(), finalReview.getProject().getId(), finalReview.getCommentary(), finalReview.getId());
    }

    @Override
    public List<FinalReview> getAll() {
        log.info("Getting all final reviews");
        return jdbcTemplate.query(GET_ALL, new FinalReviewMapper());
    }

    @Override
    public int createFinalReview(FinalReview finalReview) {
        log.info("Create new final review at = {}", finalReview.getDate());
        return jdbcTemplate.update(CREATE_FINAL_REVIEW, finalReview.getDate(), finalReview.getStudent().getId(), finalReview.getEmployee().getId(),
                finalReview.getType(), finalReview.getProject().getId(), finalReview.getCommentary());
    }

	@Override
	public boolean exists(Long studentId, Long groupId, String type) {
		return jdbcTemplate.queryForObject(EXISTS_STUDENT_GROUP, Boolean.class, studentId, groupId, type);
	}

	@Override
	public FinalReview getByStudent(Long projectId, Long studentId, String type) {
		return jdbcTemplate.queryForObject(GET_BY_STUDENT, new FinalReviewMapper(), studentId, projectId, type);
	}

	@Override
	public boolean existsForProject(Long studentId, Long projectId, String type) {
		return jdbcTemplate.queryForObject(EXISTS_BY_STUDENT_PROJECT_TYPE, Boolean.class, studentId, projectId, type);
	}

	public int getNumOfFinalReviewsByProjectAndType(Long projectId, String type){
        return jdbcTemplate.queryForObject(GET_NUM_BY_PROJECT_AND_TYPE, new Object[]{projectId, type}, Integer.class);
    }
}
