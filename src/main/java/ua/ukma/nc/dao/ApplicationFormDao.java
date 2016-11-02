package ua.ukma.nc.dao;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ApplicationFormDao {

    ApplicationFormDao getById(Long id);

    int deleteApplicationForm(ApplicationFormDao applicationForm);

    int updateApplicationForm(ApplicationFormDao applicationForm);

    List<ApplicationFormDao> getAll();

    int createApplicationForm(ApplicationFormDao applicationForm);
}
