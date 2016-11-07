package ua.ukma.nc.service;

import ua.ukma.nc.entity.Status;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StatusService {
    Status getById(Long id);

    Status getByTitle(String title);

    int deleteStatus(Status status);

    int updateStatus(Status status);

    List<Status> getAll();

    int createStatus(Status status);
}
