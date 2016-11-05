package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.StatusLogDao;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;
import ua.ukma.nc.entity.impl.proxy.StatusProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class StatusLogDaoImpl implements StatusLogDao{

    private static Logger log = LoggerFactory.getLogger(StatusLogDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class StatusLogMapper implements RowMapper<StatusLog> {
        public StatusLog mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            StatusLog statusLog = new StatusLog();
            statusLog.setId(resultSet.getLong("id"));
            statusLog.setOldStatus(appContext.getBean(StatusProxy.class, resultSet.getLong("id_old_status")));
            statusLog.setNewStatus(appContext.getBean(StatusProxy.class, resultSet.getLong("id_new_status")));
            statusLog.setCommentary(resultSet.getString("commentary"));
            statusLog.setStudent(appContext.getBean(UserProxy.class, resultSet.getLong("id_student")));
            statusLog.setEmployee(appContext.getBean(UserProxy.class, resultSet.getLong("id_employee")));
            statusLog.setDate(resultSet.getTimestamp("date"));
            statusLog.setGroup(appContext.getBean(GroupProxy.class, resultSet.getLong("id_group")));
            return statusLog;
        }
    }

    private static final String GET_ALL = "SELECT id, id_old_status, id_new_status, commentary, id_student, id_employee, date, id_group FROM tcms.status_log";

    private static final String GET_BY_ID = "SELECT id, id_old_status, id_new_status, commentary, id_student, id_employee, date, id_group FROM tcms.status_log WHERE id = ?";

    private static final String DELETE_STATUS_LOG = "DELETE FROM tcms.status_log WHERE id = ?";

    private static final String CREATE_STATUS_LOG = "INSERT INTO tcms.status_log (id_old_status, id_new_status, commentary, id_student, id_employee, date, id_group) VALUES (?,?,?,?,?,?,?,?)";

    private static final String UPDATE_STATUS_LOG = "UPDATE tcms.status_log SET id_old_status = ?, id_new_status = ?, commentary = ?, id_student = ?, id_employee = ?, date = ?, id_group = ? WHERE id = ?";

    @Override
    public StatusLog getById(Long id) {
        log.info("Getting status log with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new StatusLogMapper(), id);
    }

    @Override
    public int deleteStatusLog(StatusLog statusLog) {
        log.info("Deleting status log with id = {}", statusLog.getId());
        return jdbcTemplate.update(DELETE_STATUS_LOG, statusLog.getId());
    }

    @Override
    public int updateStatusLog(StatusLog statusLog) {
        log.info("Updating status log with id = {}", statusLog.getId());
        return jdbcTemplate.update(UPDATE_STATUS_LOG, statusLog.getOldStatus().getId(), statusLog.getNewStatus().getId(), statusLog.getCommentary(),
                statusLog.getStudent().getId(), statusLog.getEmployee().getId(), statusLog.getDate(), statusLog.getGroup().getId(), statusLog.getId());
    }

    @Override
    public List<StatusLog> getAll() {
        log.info("Getting all status log");
        return jdbcTemplate.query(GET_ALL, new StatusLogMapper());
    }

    @Override
    public int createStatusLog(StatusLog statusLog) {
        log.info("Create new status log with commentary = {}", statusLog.getCommentary());
        return jdbcTemplate.update(CREATE_STATUS_LOG, statusLog.getOldStatus().getId(), statusLog.getNewStatus().getId(), statusLog.getCommentary(),
                statusLog.getStudent().getId(), statusLog.getEmployee().getId(), statusLog.getDate(), statusLog.getGroup().getId());
    }
}
