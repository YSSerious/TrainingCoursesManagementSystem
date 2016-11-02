package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Status;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StatusDao {
    Status getById(Long id);

    int deleteStatus(Status status);

    int updateStatus(Status status);

    List<Status> getAll();

    int createStatus(Status status);
}
