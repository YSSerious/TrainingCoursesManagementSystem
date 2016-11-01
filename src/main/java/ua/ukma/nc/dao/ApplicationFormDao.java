package ua.ukma.nc.dao;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface ApplicationFormDao {

    ApplicationFormDao getById(Long id);

    int deleteApplicationFormDao(ApplicationFormDao applicationFormDao);

    int updateApplicationFormDao(ApplicationFormDao applicationFormDao);

    List<ApplicationFormDao> getAll();

    int createApplicationFormDao(ApplicationFormDao applicationFormDao);
}
