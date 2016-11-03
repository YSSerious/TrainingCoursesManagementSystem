package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.ApplicationFormDao;
import ua.ukma.nc.entity.ApplicationForm;
import ua.ukma.nc.service.ApplicationFormService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class ApplicationFormServiceImpl implements ApplicationFormService{

    @Autowired
    private ApplicationFormDao applicationFormDao;

    @Override
    public ApplicationForm getByUserId(Long id) {
        return applicationFormDao.getByUserId(id);
    }

    @Override
    public int deleteApplicationForm(ApplicationForm applicationForm) {
        return applicationFormDao.deleteApplicationForm(applicationForm);
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm) {
        return applicationFormDao.updateApplicationForm(applicationForm);
    }

    @Override
    public List<ApplicationForm> getAll() {
        return applicationFormDao.getAll();
    }

    @Override
    public int createApplicationForm(ApplicationForm applicationForm) {
        return applicationFormDao.createApplicationForm(applicationForm);
    }
}
