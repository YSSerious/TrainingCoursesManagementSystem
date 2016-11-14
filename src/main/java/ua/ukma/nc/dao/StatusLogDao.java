package ua.ukma.nc.dao;

import ua.ukma.nc.entity.StatusLog;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StatusLogDao {
	
	Long getNewestGroup(Long userId);
	
    StatusLog getById(Long id);

    int deleteStatusLog(StatusLog statusLog);

    int updateStatusLog(StatusLog statusLog);

    List<StatusLog> getAll();

    int createStatusLog(StatusLog statusLog);

	List<StatusLog> getByProjectStudent(Long projectId, Long studentId);
}
