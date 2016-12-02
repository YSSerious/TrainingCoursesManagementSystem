package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.impl.proxy.CriterionProxy;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;
import ua.ukma.nc.entity.impl.real.MeetingImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    
    private static final String UNMARKED_MEETINGS = "SELECT DISTINCT * FROM tcms.meeting as meet WHERE now() > time AND id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?) AND id_group NOT IN (SELECT id_group FROM tcms.status_log WHERE id_student = ?) AND (SELECT COUNT (id_student) FROM tcms.meeting_review WHERE id_meeting = meet.id AND id_student IN (SELECT id_student FROM tcms.status_log as st1 WHERE id_group = meet.id_group AND date = (SELECT MAX(date) FROM tcms.status_log WHERE id_student = st1.id_student AND id_group = st1.id_group) AND id_new_status NOT IN (1))) != (SELECT COUNT(id_student) FROM tcms.status_log as st1 WHERE id_group = meet.id_group AND date = (SELECT MAX(date) FROM tcms.status_log WHERE id_student = st1.id_student AND id_group = st1.id_group) AND id_new_status NOT IN (1));";
    
    private static final String GET_WITHOUT_MARKS = "SELECT * FROM tcms.meeting WHERE id_group = ? AND id NOT IN (SELECT id_meeting FROM tcms.meeting_review WHERE id_student = ?)";
    
    private static final String GET_BY_STUDENT_PROJECT_TYPE = "SELECT id, id_group, time, place, name FROM tcms.meeting WHERE (id_group IN (SELECT id FROM tcms.group WHERE id_project = ?)) AND (id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?)) AND (id IN (SELECT id_meeting FROM tcms.meeting_review WHERE id_student = ? AND type = ?)) ORDER BY time asc";
    
    private static final String GET_BY_STUDENT_PROJECT = "SELECT id, id_group, time, place, name FROM tcms.meeting WHERE (id_group IN (SELECT id FROM tcms.group WHERE id_project = ?)) AND (id_group IN (SELECT id_group FROM tcms.user_group WHERE id_user = ?)) ORDER BY time asc";

    private static final String GET_ALL = "SELECT id, id_group, name, time, place FROM tcms.meeting";

    private static final String GET_BY_ID = "SELECT id, id_group, name, time, place FROM tcms.meeting WHERE id = ?";

    private static final String GET_BY_DATA = "SELECT id, id_group, name, time, place FROM tcms.meeting WHERE name = ? and place = ? and time = ?";

    private static final String DELETE_MEETING = "DELETE FROM tcms.meeting WHERE id = ?";

    private static final String CREATE_MEETING = "INSERT INTO tcms.meeting (id_group, name, time, place) VALUES (?,?,?,?)";

    private static final String UPDATE_MEETING = "UPDATE tcms.meeting SET id_group = ?, name = ?, time = ?, place =? WHERE id = ?";

    private static final String EDIT_MEETING = "UPDATE tcms.meeting SET name = ?, time = ?, place = ? WHERE id = ?";

    private static final String GET_CRITERION_BY_ID = "SELECT id_criterion FROM tcms.meeting_criterion WHERE id_meeting = ?";
    
    private static final String GET_BY_GROUP = "SELECT  id, id_group, name, time, place FROM  tcms.meeting WHERE id_group = ?";
    
    private static final String GET_UPCOMING_BY_GROUP = "SELECT id, id_group, name, time, place FROM tcms.meeting WHERE id_group = ? AND time > CURRENT_TIMESTAMP ORDER BY time ASC LIMIT 1";

    private static final String IS_EXIST = "SELECT EXISTS (SELECT * from tcms.meeting where time = ?)";

    private static final String IS_REVIEWED = "select exists (select id from tcms.meeting_review where id_meeting = ?)";

    private static final String GET_PROJECT_BY_MEETING_ID= "select id from tcms.project where id = (select id_project from tcms.group where id = (select id_group from tcms.meeting where id=?));  ";

    private static final String ADD_CRITERION = "INSERT INTO tcms.meeting_criterion (id_meeting, id_criterion) VALUES (?,?)";

    private static final String DELETE_MEETING_CRITERION = "DELETE FROM tcms.meeting_criterion WHERE id_meeting = ? and id_criterion = ?";

    @Override
    public Meeting getById(Long id) {
        log.info("Getting meeting with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new MeetingMapper(), id);
    }

    @Override
    public List<Meeting> getByNamePlaceDate(String name, String place, Timestamp date) {
        return jdbcTemplate.query(GET_BY_DATA, new MeetingMapper(), name, place, date);
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
    public Meeting getUpcomingByGroup(Long groupId) {
        log.info("Getting all meeting with group id={}", groupId);

        List<Meeting> meetings = jdbcTemplate.query(GET_UPCOMING_BY_GROUP, new MeetingMapper(), groupId);
        if (meetings.isEmpty() == true) {
            return null;
        }
        return meetings.get(0);
    }
    
    @Override
    public int createMeeting(Meeting meeting) {
        log.info("Create new meeting with name = {}", meeting.getName());
        return jdbcTemplate.update(CREATE_MEETING, meeting.getGroup().getId(), meeting.getName(), meeting.getTime(), meeting.getPlace());
    }

    @Override
    public boolean isExist(Timestamp date) {
        return jdbcTemplate.queryForObject(IS_EXIST, Boolean.class, date);
    }

    @Override
    public boolean isReviewed(Long meetingId) {
        return jdbcTemplate.queryForObject(IS_REVIEWED,Boolean.class, meetingId);
    }

    @Override
    public Long getProjectByMeetingId(Long meetingId) {
        return jdbcTemplate.queryForObject(GET_PROJECT_BY_MEETING_ID, Long.class, meetingId);
    }

    @Override
    public int addCriteria(Long meetingId, Criterion criterion) {
        return  jdbcTemplate.update(ADD_CRITERION, meetingId, criterion.getId());
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

    @Override
    public int[] butchInsert(String name, String place, Timestamp date, List<Group> groups) {
        final String BUTCH_INSERT = "INSERT INTO tcms.meeting (id_group, name, time, place) VALUES (?,?,?,?)";

       return jdbcTemplate.batchUpdate(BUTCH_INSERT, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Group group = groups.get(i);
                ps.setLong(1, group.getId());
                ps.setString(2, name);
                ps.setTimestamp(3, date);
                ps.setString(4, place);
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
    }

    @Override
    public int[] addMeetingCriterion(Meeting meeting, List<Criterion> criterions) {
        final String BUTCH_ADD_CRITERION = "INSERT INTO tcms.meeting_criterion (id_meeting, id_criterion) VALUES (?,?)";

        return jdbcTemplate.batchUpdate(BUTCH_ADD_CRITERION, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Criterion criterion = criterions.get(i);
                ps.setLong(1, meeting.getId());
                ps.setLong(2, criterion.getId());
            }
            @Override
            public int getBatchSize() {
                return criterions.size();
            }
        });
    }

    @Override
    public int deleteMeetingCriterion(Long meetingId, Criterion criterion) {
        return jdbcTemplate.update(DELETE_MEETING_CRITERION, meetingId, criterion.getId());
    }

    @Override
    public int editMeeting(Long id, String name, Timestamp date, String place) {
        return jdbcTemplate.update(EDIT_MEETING, name, date, place, id);
    }

	@Override
	public List<Meeting> getMentorsUncheckedMeetings(Long mentorId) {
		return jdbcTemplate.query(UNMARKED_MEETINGS, new MeetingMapper(),mentorId, mentorId);
	}

}
