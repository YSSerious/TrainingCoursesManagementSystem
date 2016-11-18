package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Meeting;

import java.util.List;

/**
 * Created by Ð�Ð»ÐµÐºÑ�ÐµÐ¹ on 30.10.2016.
 */
public interface MeetingDao {
	List<Meeting> getWithoutReview(Long groupId, Long studentId);
	
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();
    
    List<Meeting> getByStudentProject(Long studentId, Long projectId);
    
    List<Meeting> getByStudentProjectType(Long studentId, Long projectId, Character type);
    
    List<Meeting> getByGroup(Long groupId);
    
    Meeting getUpcomingByGroup(Long groupId);

    int createMeeting(Meeting meeting);
}
