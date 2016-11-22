package ua.ukma.nc.service;

import ua.ukma.nc.dto.AddCriteriaDto;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingService {
	
	List<Meeting> getWithoutReview(Long groupId, Long studentId);
	
	List<Meeting> getByStudentProject(Long studentId, Long projectId);
	
	List<Meeting> getByStudentProjectType(Long studentId, Long projectId, Character type);

    List<Meeting> getByNamePlaceDate(String name, String place, Timestamp date);
	
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();
    
    List<Meeting> getByGroup(Long groupId);
    
    Meeting getUpcomingByGroup(Long groupId);

    int createMeeting(Meeting meeting);

    int[] butchInsert(String name, String place, Timestamp date, List<Group> groups);

    int addMeetings(AddCriteriaDto dto);

    boolean isExist(Timestamp date);
}
