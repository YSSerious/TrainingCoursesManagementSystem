package ua.ukma.nc.service;

import ua.ukma.nc.entity.StatusLog;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StatusLogService {
	
	StatusLog getLast(Long groupId, Long studentId);
	
	boolean exists(Long userId);
	
	Long getNewestGroup(Long userId);
	
    StatusLog getById(Long id);

    int deleteStatusLog(StatusLog statusLog);

    int updateStatusLog(StatusLog statusLog);

    List<StatusLog> getAll();

    int createStatusLog(StatusLog statusLog);
    
    List<StatusLog> getByProjectStudent(Long projectId, Long studentId);

    int getNumOfStartedProject(Long projectId);

    int getNumOfInvitedByProject(Long projectId);

    int getNumOfJobOffersByProject(Long projectId);
}
