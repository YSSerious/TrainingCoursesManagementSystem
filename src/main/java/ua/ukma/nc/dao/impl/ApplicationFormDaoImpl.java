package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.ukma.nc.dao.ApplicationFormDao;
import ua.ukma.nc.entity.ApplicationForm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ApplicationFormDaoImpl implements ApplicationFormDao{

    private static Logger log = LoggerFactory.getLogger(ApplicationFormDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class ApplicationFormMapper implements RowMapper<ApplicationForm> {
        public ApplicationForm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ApplicationForm applicationForm = new ApplicationForm();
            return applicationForm;
        }
    }

    @Override
    public ApplicationFormDao getById(Long id) {
        return null;
    }

    @Override
    public int deleteApplicationFormDao(ApplicationFormDao applicationFormDao) {
        return 0;
    }

    @Override
    public int updateApplicationFormDao(ApplicationFormDao applicationFormDao) {
        return 0;
    }

    @Override
    public List<ApplicationFormDao> getAll() {
        return null;
    }

    @Override
    public int createApplicationFormDao(ApplicationFormDao applicationFormDao) {
        return 0;
    }
}
