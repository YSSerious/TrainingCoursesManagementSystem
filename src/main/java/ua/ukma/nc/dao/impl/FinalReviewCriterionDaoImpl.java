package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.FinalReviewCriterionDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.entity.impl.proxy.CriterionProxy;
import ua.ukma.nc.entity.impl.proxy.FinalReviewProxy;
import ua.ukma.nc.entity.impl.proxy.MarkProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class FinalReviewCriterionDaoImpl implements FinalReviewCriterionDao {

	private static Logger log = LoggerFactory.getLogger(FinalReviewCriterionDaoImpl.class.getName());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ApplicationContext context;

	public class FinalReviewCriterionMapper implements RowMapper<FinalReviewCriterion> {
		public FinalReviewCriterion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			FinalReviewCriterion finalReviewCriterion = new FinalReviewCriterion();
			finalReviewCriterion.setId(resultSet.getLong("id"));
			finalReviewCriterion
					.setFinalReview(context.getBean(FinalReviewProxy.class, resultSet.getLong("id_final_review")));
			finalReviewCriterion.setCriterion(context.getBean(CriterionProxy.class, resultSet.getLong("id_criterion")));
			finalReviewCriterion.setMark(context.getBean(MarkProxy.class, resultSet.getInt("id_mark")));
			finalReviewCriterion.setCommentary(resultSet.getString("commentary"));
			return finalReviewCriterion;
		}
	}

	private static final String GET_BY_FINAL_REVIEW = "SELECT * FROM tcms.final_review_criterion WHERE id_final_review = ?";

	private static final String GET_ALL = "SELECT id, id_final_review, id_criterion, id_mark, commentary FROM tcms.final_review_criterion";

	private static final String GET_BY_ID = "SELECT id, id_final_review, id_criterion, id_mark, commentary FROM tcms.final_review_criterion WHERE id = ?";

	private static final String DELETE_FRC = "DELETE FROM tcms.final_review_criterion WHERE id = ?";

	private static final String CREATE_FRC = "INSERT INTO tcms.final_review_criterion (id_final_review, id_criterion, id_mark, commentary) VALUES (?,?,?,?)";

	private static final String UPDATE_FRC = "UPDATE tcms.final_review_criterion SET id_final_review = ?, id_criterion = ?, id_mark = ?, commentary =? WHERE id = ?";

	private static final String IS_EXISTS = "select exists (select * from tcms.final_review_criterion where id_criterion = ? and id_final_review = (select id from tcms.final_review where id_project = ?))";

	@Override
	public FinalReviewCriterion getById(Long id) {
		log.info("Getting FRC with id = {}", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, new FinalReviewCriterionMapper(), id);
	}

	@Override
	public int deleteFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
		log.info("Deleting FRC with id = {}", finalReviewCriterion.getId());
		return jdbcTemplate.update(DELETE_FRC, finalReviewCriterion.getId());
	}

	@Override
	public int updateFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
		log.info("Updating FRC with id = {}", finalReviewCriterion.getId());
		return jdbcTemplate.update(UPDATE_FRC, finalReviewCriterion.getFinalReview().getId(),
				finalReviewCriterion.getCriterion().getId(), finalReviewCriterion.getMark().getValue(),
				finalReviewCriterion.getCommentary(), finalReviewCriterion.getId());
	}

	@Override
	public List<FinalReviewCriterion> getAll() {
		log.info("Getting all FRC");
		return jdbcTemplate.query(GET_ALL, new FinalReviewCriterionMapper());
	}

	@Override
	public int createFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
		log.info("Create new FRC with commentary = {}", finalReviewCriterion.getCommentary());
		return jdbcTemplate.update(CREATE_FRC, finalReviewCriterion.getFinalReview().getId(),
				finalReviewCriterion.getCriterion().getId(), finalReviewCriterion.getMark().getValue(),
				finalReviewCriterion.getCommentary());
	}

	@Override
	public boolean isExists(Criterion criterion, Long projectId) {
		log.info("Is criterion with id = {} has final_review throughout the project {}", criterion.getId(), projectId);
		return jdbcTemplate.queryForObject(IS_EXISTS, boolean.class, criterion.getId(), projectId);
	}

	@Override
	public List<FinalReviewCriterion> getByFinalReview(Long id) {
		return jdbcTemplate.query(GET_BY_FINAL_REVIEW, new FinalReviewCriterionMapper(), id);
	}
}
