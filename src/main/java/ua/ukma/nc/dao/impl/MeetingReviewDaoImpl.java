package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.dao.MeetingReviewDao;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.impl.proxy.MeetingProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class MeetingReviewDaoImpl implements MeetingReviewDao {

    private static Logger log = LoggerFactory.getLogger(MeetingReviewDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class MeetingReviewMapper implements RowMapper<MeetingReview> {
        public MeetingReview mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            MeetingReview meetingReview = new MeetingReviewImpl();
            meetingReview.setId(resultSet.getLong("id"));
            meetingReview.setStudent(appContext.getBean(UserProxy.class, resultSet.getLong("id_student")));
            meetingReview.setMeeting(appContext.getBean(MeetingProxy.class, resultSet.getLong("id_meeting")));
            meetingReview.setMentor(appContext.getBean(UserProxy.class, resultSet.getLong("id_mentor")));
            meetingReview.setType(resultSet.getString("type"));
            meetingReview.setCommentary(resultSet.getString("commentary"));
            return meetingReview;
        }
    }
    
    private static final String GET_BY_STUDENT_PROJECT = "SELECT * FROM tcms.meeting_review WHERE id_student = ? AND id_meeting IN (SELECT id FROM tcms.meeting WHERE id_group IN (SELECT id FROM tcms.group WHERE id_project = ?))";

    private static final String GET_ALL = "SELECT id, id_student, id_meeting, id_mentor, type, commentary FROM tcms.meeting_review";

    private static final String GET_BY_ID = "SELECT id, id_student, id_meeting, id_mentor, type, commentary FROM tcms.meeting_review WHERE id = ?";

    private static final String DELETE_MEETING_CRITERION = "DELETE FROM tcms.meeting_review WHERE id = ?";

    private static final String CREATE_MEETING_CRITERION = "INSERT INTO tcms.meeting_review (id_student, id_meeting, id_mentor, type, commentary) VALUES (?,?,?,?,?)";

    private static final String UPDATE_MEETING_CRITERION = "UPDATE tcms.meeting_review SET id_student = ?, id_meeting = ?, id_mentor = ?, type = ?, commentary = ? WHERE id = ?";

    private static final String GET_BY_MEETING_STUDENT = "SELECT * FROM tcms.meeting_review WHERE id_meeting = ? AND id_student = ?";

    @Override
    public MeetingReview getById(Long id) {
        log.info("Getting meeting review with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new MeetingReviewMapper(), id);
    }
    
    @Override
    public List<MeetingReview> getByProjectStudent(Long projectId, Long studentId) {
        return jdbcTemplate.query(GET_BY_STUDENT_PROJECT, new MeetingReviewMapper(), studentId, projectId);
    }

    @Override
    public MeetingReview getByMeetingStudent(Long meetingId, Long studentId) {
        try{
           return jdbcTemplate.queryForObject(GET_BY_MEETING_STUDENT, new MeetingReviewMapper(), meetingId, studentId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int deleteMeetingReview(MeetingReview meetingReview) {
        log.info("Deleting meeting review with id = {}", meetingReview.getId());
        return jdbcTemplate.update(DELETE_MEETING_CRITERION, meetingReview.getId());
    }

    @Override
    public int updateMeetingReview(MeetingReview meetingReview) {
        log.info("Updating meeting review with id = {}", meetingReview.getId());
        return jdbcTemplate.update(UPDATE_MEETING_CRITERION, meetingReview.getStudent().getId(), meetingReview.getMeeting().getId(), meetingReview.getMentor().getId(),
                 meetingReview.getType(), meetingReview.getCommentary(), meetingReview.getId());
    }

    @Override
    public List<MeetingReview> getAll() {
        log.info("Getting all meeting reviews");
        return jdbcTemplate.query(GET_ALL, new MeetingReviewMapper());
    }

    @Override
    public int createMeetingReview(MeetingReview meetingReview) {
        log.info("Create new meeting review with commentary = {}", meetingReview.getCommentary());
        return jdbcTemplate.update(CREATE_MEETING_CRITERION, meetingReview.getStudent().getId(), meetingReview.getMeeting().getId(), meetingReview.getMentor().getId(),
                meetingReview.getType(), meetingReview.getCommentary());
    }
}
