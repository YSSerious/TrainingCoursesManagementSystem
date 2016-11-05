package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.MeetingResultDao;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.impl.proxy.CriterionProxy;
import ua.ukma.nc.entity.impl.proxy.MarkProxy;
import ua.ukma.nc.entity.impl.proxy.MeetingReviewProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class MeetingResultDaoImpl implements MeetingResultDao{

    private static Logger log = LoggerFactory.getLogger(MeetingResultDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext context;

    public class MeetingResultMapper implements RowMapper<MeetingResult> {
        public MeetingResult mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MeetingResult meetingResult= new MeetingResult();
            meetingResult.setId(resultSet.getLong("id"));
            meetingResult.setCriterion(context.getBean(CriterionProxy.class,resultSet.getLong("id_criterion")));
            meetingResult.setMeetingReview(new MeetingReviewProxy(resultSet.getLong("id_meeting_review")));
            meetingResult.setMark(context.getBean(MarkProxy.class, resultSet.getInt("id_mark")));
            meetingResult.setCommentary(resultSet.getString("commentary"));
            return meetingResult;
        }
    }

    private static final String GET_ALL = "SELECT id, id_criterion, id_meeting_review, id_mark, commentary FROM tcms.meeting_result";

    private static final String GET_BY_ID = "SELECT id, id_criterion, id_meeting_review, id_mark, commentary FROM tcms.meeting_result WHERE id = ?";

    private static final String DELETE_MEETING_RESULT = "DELETE FROM tcms.meeting_result WHERE id = ?";

    private static final String CREATE_MEETING_RESULT = "INSERT INTO tcms.meeting_result (id_criterion, id_meeting_review, id_mark, commentary) VALUES (?,?,?,?)";

    private static final String UPDATE_MEETING_RESULT = "UPDATE tcms.meeting_result SET id_criterion = ?, id_meeting_review = ?, id_mark = ?, commentary = ? WHERE id = ?";

    @Override
    public MeetingResult getById(Long id) {
        log.info("Getting meeting result with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new MeetingResultMapper(), id);
    }

    @Override
    public int deleteMeetingResult(MeetingResult meetingResult) {
        log.info("Deleting meeting result with id = {}", meetingResult.getId());
        return jdbcTemplate.update(DELETE_MEETING_RESULT, meetingResult.getId());
    }

    @Override
    public int updateMeetingResult(MeetingResult meetingResult) {
        log.info("Updating meeting result with id = {}", meetingResult.getId());
        return jdbcTemplate.update(UPDATE_MEETING_RESULT, meetingResult.getCriterion().getId(), meetingResult.getMeetingReview().getId(),
                meetingResult.getMark().getValue(), meetingResult.getCommentary(), meetingResult.getId());
    }

    @Override
    public List<MeetingResult> getAll() {
        log.info("Getting all meeting results");
        return jdbcTemplate.query(GET_ALL, new MeetingResultMapper());
    }

    @Override
    public int createMeetingResult(MeetingResult meetingResult) {
        log.info("Create new meeting result with commentary = {}", meetingResult.getCommentary());
        return jdbcTemplate.update(CREATE_MEETING_RESULT, meetingResult.getCriterion().getId(), meetingResult.getMeetingReview().getId(),
                meetingResult.getMark().getValue(), meetingResult.getCommentary());
    }
}
