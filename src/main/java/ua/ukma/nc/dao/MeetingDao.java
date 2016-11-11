package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Meeting;

import java.util.List;

/**
 * Created by Ð�Ð»ÐµÐºÑ�ÐµÐ¹ on 30.10.2016.
 */
public interface MeetingDao {
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();
    
    List<Meeting> getByStudentProject(Long studentId, Long projectId);
    
    List<Meeting> getByGroup(Long groupId);

    int createMeeting(Meeting meeting);
}
