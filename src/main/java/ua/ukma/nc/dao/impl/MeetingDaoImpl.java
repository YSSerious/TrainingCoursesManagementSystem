package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.impl.proxy.CriterionProxy;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;
import ua.ukma.nc.entity.impl.real.MeetingImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ð�Ð»ÐµÐºÑ�ÐµÐ¹ on 30.10.2016.
 */
@Repository
public class MeetingDaoImpl implements MeetingDao{

    private static Logger log = LoggerFactory.getLogger(MeetingDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext context;

    public class MeetingMapper implements RowMapper<Meeting> {
        public Meeting mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Meeting meeting = new MeetingImpl();
            meeting.setId(resultSet.getLong("id"));
            meeting.setGroup(context.getBean(GroupProxy.class, resultSet.getLong("id_group")));
            meeting.setName(resultSet.getString("name"));
            meeting.setTime(resultSet.getTimestamp("time"));
            meeting.setPlace(resultSet.getString("place"));
            meeting.setCriterions(getCriterions(resultSet.getLong("id")));
            return meeting;
        }
    }
    
    private static final String GET_WITHOUT_MARKS = "SELECT * FROM tcms.meeting WHERE id_group = ? AND id NOT IN (SELECT id_meeting FROM tcms.meeting_review WHERE id_student = ?)";
    
    private static final String GET_BY_STUDENT_PROJECT_TYPE = "SELECT id, id_group, time, place, name FROM tcms.meeting WHERE (id_group IN (SELECT id FROM tcms.group WHERE id_project = ?)) AND (id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?)) AND (id IN (SELECT id_meeting FROM tcms.meeting_review WHERE id_student = ? AND type = ?)) ORDER BY time asc";
    
    private static final String GET_BY_STUDENT_PROJECT = "SELECT id, id_group, time, place, name FROM tcms.meeting WHERE (id_group IN (SELECT id FROM tcms.group WHERE id_project = ?)) AND (id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?)) ORDER BY time asc";

    private static final String GET_ALL = "SELECT id, id_group, name, time, place FROM tcms.meeting";

    private static final String GET_BY_ID = "SELECT id, id_group, name, time, place FROM tcms.meeting WHERE id = ?";

    private static final String DELETE_MEETING = "DELETE FROM tcms.meeting WHERE id = ?";

    private static final String CREATE_MEETING = "INSERT INTO tcms.meeting (id_group, name, time, place) VALUES (?,?,?,?)";

    private static final String UPDATE_MEETING = "UPDATE tcms.meeting SET id_group = ?, name = ?, time = ?, place =? WHERE id = ?";

    private static final String GET_CRITERION_BY_ID = "SELECT id_criterion FROM tcms.meeting_criterion WHERE id_meeting = ?";
    
    private static final String GET_BY_GROUP = "SELECT  id, id_group, name, time, place FROM  tcms.meeting WHERE id_group = ?";
    
    @Override
    public Meeting getById(Long id) {
        log.info("Getting meeting with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new MeetingMapper(), id);
    }

    @Override
    public int deleteMeeting(Meeting meeting) {
        log.info("Deleting meeting with id = {}", meeting.getId());
        return jdbcTemplate.update(DELETE_MEETING, meeting.getId());
    }

    @Override
    public int updateMeeting(Meeting meeting) {
        log.info("Updating meeting with id = {}", meeting.getId());
        return jdbcTemplate.update(UPDATE_MEETING, meeting.getGroup().getId(), meeting.getName(), meeting.getTime(), meeting.getPlace(), meeting.getId());
    }

    @Override
    public List<Meeting> getAll() {
        log.info("Getting all meetings");
        return jdbcTemplate.query(GET_ALL, new MeetingMapper());
    }
    
    @Override
	public List<Meeting> getByGroup(Long groupId) {
		log.info("Getting all meeting with group id={}",groupId);
		
		return jdbcTemplate.query(GET_BY_GROUP,new MeetingMapper(),groupId);
	}
    
    @Override
    public int createMeeting(Meeting meeting) {
        log.info("Create new meeting with name = {}", meeting.getName());
        return jdbcTemplate.update(CREATE_MEETING, meeting.getGroup().getId(), meeting.getName(), meeting.getTime(), meeting.getPlace());
    }

    private List<Criterion> getCriterions(Long meetingId) {
        log.info("Getting all criterions with meeting id = {}", meetingId);
        return jdbcTemplate.query(GET_CRITERION_BY_ID, new MeetingCriterionMapper(), meetingId);
    }

    public class MeetingCriterionMapper implements RowMapper<Criterion> {
        public Criterion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Criterion criterion = context.getBean(CriterionProxy.class);
            criterion.setId(resultSet.getLong("id_criterion"));
            return criterion;
        }
    }

	@Override
	public List<Meeting> getByStudentProject(Long studentId, Long projectId) {
		log.info("Getting meetings");
        return jdbcTemplate.query(GET_BY_STUDENT_PROJECT, new MeetingMapper(),projectId, studentId);
	}

	@Override
	public List<Meeting> getByStudentProjectType(Long studentId, Long projectId, Character type) {
		log.info("Getting meetings");
        return jdbcTemplate.query(GET_BY_STUDENT_PROJECT_TYPE, new MeetingMapper(),projectId, studentId, studentId, type);
	}

	@Override
	public List<Meeting> getWithoutReview(Long groupId, Long studentId) {
        log.info("Getting all meetings");
        return jdbcTemplate.query(GET_WITHOUT_MARKS, new MeetingMapper(), groupId, studentId);
	}

	
}
