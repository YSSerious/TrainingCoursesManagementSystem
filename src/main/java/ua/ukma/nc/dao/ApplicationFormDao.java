package ua.ukma.nc.dao;

import ua.ukma.nc.entity.ApplicationForm;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ApplicationFormDao {

    ApplicationForm getByUserId(Long id);

    int deleteApplicationForm(ApplicationForm applicationForm);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getAll();

    int createApplicationForm(ApplicationForm applicationForm);
}
