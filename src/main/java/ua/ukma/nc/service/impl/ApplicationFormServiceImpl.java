package ua.ukma.nc.service.impl;

import ua.ukma.nc.dao.ApplicationFormDao;
import ua.ukma.nc.service.ApplicationFormService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ApplicationFormServiceImpl implements ApplicationFormService{
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
}
