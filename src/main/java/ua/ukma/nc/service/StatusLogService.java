package ua.ukma.nc.service;

import ua.ukma.nc.entity.StatusLog;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StatusLogService {
    StatusLog getById(Long id);

    int deleteStatusLog(StatusLog statusLog);

    int updateStatusLog(StatusLog statusLog);

    List<StatusLog> getAll();

    int createStatusLog(StatusLog statusLog);
}
