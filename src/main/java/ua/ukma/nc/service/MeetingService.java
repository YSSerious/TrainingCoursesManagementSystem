package ua.ukma.nc.service;

import ua.ukma.nc.entity.Meeting;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingService {
	List<Meeting> getByStudentProject(Long studentId, Long projectId);
	
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();
    
    List<Meeting> getByGroup(Long groupId);

    int createMeeting(Meeting meeting);
}
