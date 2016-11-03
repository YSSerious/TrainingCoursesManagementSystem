package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public class FinalReviewMapper implements RowMapper<FinalReview> {
        public FinalReview mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            FinalReview finalReview = new FinalReviewImpl();
            finalReview.setId(resultSet.getLong("id"));
            finalReview.setDate(resultSet.getTimestamp("date"));
            finalReview.setStudent(new UserProxy(resultSet.getLong("id_student")));
            finalReview.setEmployee(new UserProxy(resultSet.getLong("id_employee")));
            finalReview.setType(resultSet.getString("type"));
            finalReview.setProject(new ProjectProxy(resultSet.getLong("id_project")));
            finalReview.setCommentary(resultSet.getString("commentary"));
            return finalReview;
        }
    }

    private static final String GET_ALL = "SELECT id, date, id_student, id_employee, type, id_project, commentary FROM tcms.final_review";

    private static final String GET_BY_ID = "SELECT id, date, id_student, id_employee, type, id_project, commentary FROM tcms.final_review WHERE id = ?";

    private static final String DELETE_FINAL_REVIEW = "DELETE FROM tcms.final_review WHERE id = ?";

    private static final String CREATE_FINAL_REVIEW = "INSERT INTO tcms.final_review (date, id_student, id_employee, type, id_project, commentary) VALUES (?,?,?,?,?)";

    private static final String UPDATE_FINAL_REVIEW = "UPDATE tcms.final_review SET date = ?, id_student = ?, id_employee = ?, type = ?, id_project = ?, commentary = ? WHERE id = ?";

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
}
