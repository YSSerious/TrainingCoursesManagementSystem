package ua.ukma.nc.service;

import ua.ukma.nc.entity.Meeting;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingService {
	
	List<Meeting> getWithoutReview(Long groupId, Long studentId);
	
	List<Meeting> getByStudentProject(Long studentId, Long projectId);
	
	List<Meeting> getByStudentProjectType(Long studentId, Long projectId, Character type);
	
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();
    
    List<Meeting> getByGroup(Long groupId);
    
    Meeting getUpcomingByGroup(Long groupId);

    int createMeeting(Meeting meeting);
}
