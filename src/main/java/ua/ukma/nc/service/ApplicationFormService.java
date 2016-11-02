package ua.ukma.nc.service;

import ua.ukma.nc.dao.ApplicationFormDao;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ApplicationFormService {
    ApplicationFormDao getById(Long id);

    int deleteApplicationForm(ApplicationFormDao applicationForm);

    int updateApplicationForm(ApplicationFormDao applicationForm);

    List<ApplicationFormDao> getAll();

    int createApplicationForm(ApplicationFormDao applicationForm);
}
