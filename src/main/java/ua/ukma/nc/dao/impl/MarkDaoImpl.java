package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.MarkDao;
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.entity.impl.real.MarkImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class MarkDaoImpl implements MarkDao{

    private static Logger log = LoggerFactory.getLogger(MarkDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class MarkMapper implements RowMapper<Mark> {
        public Mark mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Mark mark = new MarkImpl();
            mark.setValue(resultSet.getInt("value"));
            mark.setDescription(resultSet.getString("description"));
            return mark;
        }
    }

    private static final String GET_ALL = "SELECT value, description FROM tcms.mark";

    private static final String GET_BY_VALUE = "SELECT value, description FROM tcms.mark WHERE value = ?";

    private static final String DELETE_MARK = "DELETE FROM tcms.mark WHERE value = ?";

    private static final String CREATE_MARK = "INSERT INTO tcms.mark (value, description) VALUES (?,?)";

    private static final String UPDATE_MARK = "UPDATE tcms.mark SET value = ?, description = ? WHERE value = ?";

    @Override
    public Mark getByValue(int value) {
        log.info("Getting mark with value = {}", value);
        return jdbcTemplate.queryForObject(GET_BY_VALUE, new MarkMapper(), value);
    }

    @Override
    public int deleteMark(Mark mark) {
        log.info("Deleting mark with value = {}", mark.getValue());
        return jdbcTemplate.update(DELETE_MARK, mark.getValue());
    }

    @Override
    public int updateMark(Mark mark) {
        log.info("Updating mark with value = {}", mark.getValue());
        return jdbcTemplate.update(UPDATE_MARK, mark.getValue(), mark.getDescription(), mark.getValue());
    }

    @Override
    public List<Mark> getAll() {
        log.info("Getting all marks");
        return jdbcTemplate.query(GET_ALL, new MarkMapper());
    }

    @Override
    public int createMark(Mark mark) {
        log.info("Create new mark with value = {}", mark.getValue());
        return jdbcTemplate.update(CREATE_MARK, mark.getValue(), mark.getDescription());
    }
}
