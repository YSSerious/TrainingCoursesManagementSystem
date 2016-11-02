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

    @Override
    public ApplicationFormDao getById(Long id) {
        return null;
    }

    @Override
    public int deleteApplicationForm(ApplicationFormDao applicationForm) {
        return 0;
    }

    @Override
    public int updateApplicationForm(ApplicationFormDao applicationForm) {
        return 0;
    }

    @Override
    public List<ApplicationFormDao> getAll() {
        return null;
    }

    @Override
    public int createApplicationForm(ApplicationFormDao applicationForm) {
        return 0;
    }

    public class ApplicationFormMapper implements RowMapper<ApplicationForm> {
        public ApplicationForm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ApplicationForm applicationForm = new ApplicationForm();
            return applicationForm;
        }
    }


}
