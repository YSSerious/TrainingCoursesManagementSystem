package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.impl.real.StatusImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class StatusDaoImpl implements StatusDao{

    private static Logger log = LoggerFactory.getLogger(StatusDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class StatusMapper implements RowMapper<Status> {
        public Status mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Status status = new StatusImpl();
            status.setId(resultSet.getLong("id"));
            status.setTitle(resultSet.getString("name"));
            status.setDescription(resultSet.getString("description"));
            return status;
        }
    }

    private static final String GET_ALL = "SELECT id, name, description FROM tcms.status";

    private static final String GET_BY_ID = "SELECT id, name, description FROM tcms.status WHERE id = ?";

    private static final String DELETE_STATUS = "DELETE FROM tcms.status WHERE id = ?";

    private static final String CREATE_STATUS = "INSERT INTO tcms.status (name, description) VALUES (?,?)";

    private static final String UPDATE_STATUS = "UPDATE tcms.status SET name = ?, description = ? WHERE id = ?";

    @Override
    public Status getById(Long id) {
        log.info("Getting status with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new StatusMapper(), id);
    }

    @Override
    public int deleteStatus(Status status) {
        log.info("Deleting status with id = {}", status.getId());
        return jdbcTemplate.update(DELETE_STATUS, status.getId());
    }

    @Override
    public int updateStatus(Status status) {
        log.info("Updating status with id = {}", status.getId());
        return jdbcTemplate.update(UPDATE_STATUS, status.getTitle(), status.getDescription(), status.getId());
    }

    @Override
    public List<Status> getAll() {
        log.info("Getting all statuses");
        return jdbcTemplate.query(GET_ALL, new StatusMapper());
    }

    @Override
    public int createStatus(Status status) {
        log.info("Create new status with title = {}", status.getTitle());
        return jdbcTemplate.update(CREATE_STATUS, status.getTitle(), status.getDescription());
    }
}
