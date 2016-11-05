package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.ApplicationFormDao;
import ua.ukma.nc.entity.ApplicationForm;
import ua.ukma.nc.entity.impl.proxy.UserProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class ApplicationFormDaoImpl implements ApplicationFormDao{

    private static Logger log = LoggerFactory.getLogger(ApplicationFormDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class ApplicationFormMapper implements RowMapper<ApplicationForm> {
        public ApplicationForm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ApplicationForm applicationForm = new ApplicationForm();
            applicationForm.setUser(appContext.getBean(UserProxy.class, resultSet.getLong("id_user")));
            applicationForm.setPhotoScope(resultSet.getString("photo_scope"));
            return applicationForm;
        }
    }

    private static final String GET_ALL = "SELECT id_user, photo_scope FROM tcms.application_form";

    private static final String GET_BY_ID = "SELECT id_user, photo_scope FROM tcms.application_form WHERE id_user = ?";

    private static final String DELETE_APPLICATION_FORM = "DELETE FROM tcms.application_form WHERE id_user = ?";

    private static final String CREATE_APPLICATION_FORM = "INSERT INTO tcms.application_form (id_user, photo_scope) VALUES (?,?)";

    private static final String UPDATE_APPLICATION_FORM = "UPDATE tcms.application_form SET photo_scope = ? WHERE id_user = ?";

    @Override
    public ApplicationForm getByUserId(Long id) {
        log.info("Getting app form with user_id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new ApplicationFormMapper(), id);
    }

    @Override
    public int deleteApplicationForm(ApplicationForm applicationForm) {
        log.info("Deleting app form with user id = {}", applicationForm.getUser().getId());
        return jdbcTemplate.update(DELETE_APPLICATION_FORM, applicationForm.getUser().getId());
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm) {
        log.info("Updating app form with user id = {}", applicationForm.getUser().getId());
        return jdbcTemplate.update(UPDATE_APPLICATION_FORM, applicationForm.getPhotoScope(), applicationForm.getUser().getId());
    }

    @Override
    public List<ApplicationForm> getAll() {
        log.info("Getting all app form");
        return jdbcTemplate.query(GET_ALL, new ApplicationFormMapper());
    }

    @Override
    public int createApplicationForm(ApplicationForm applicationForm) {
        log.info("Create new app form with user id = {}", applicationForm.getUser().getId());
        return jdbcTemplate.update(CREATE_APPLICATION_FORM, applicationForm.getUser().getId(), applicationForm.getPhotoScope());
    }




}
