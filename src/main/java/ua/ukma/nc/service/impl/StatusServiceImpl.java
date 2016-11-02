package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.StatusDao;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.service.StatusService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    private StatusDao statusDao;

    @Override
    public Status getById(Long id) {
        return statusDao.getById(id);
    }

    @Override
    public int deleteStatus(Status status) {
        return statusDao.deleteStatus(status);
    }

    @Override
    public int updateStatus(Status status) {
        return statusDao.updateStatus(status);
    }

    @Override
    public List<Status> getAll() {
        return statusDao.getAll();
    }

    @Override
    public int createStatus(Status status) {
        return statusDao.createStatus(status);
    }
}
