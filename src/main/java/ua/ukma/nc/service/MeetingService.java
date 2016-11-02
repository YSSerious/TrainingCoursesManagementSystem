package ua.ukma.nc.service;

import ua.ukma.nc.entity.Meeting;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingService {
    Meeting getById(Long id);

    int deleteMeeting(Meeting meeting);

    int updateMeeting(Meeting meeting);

    List<Meeting> getAll();

    int createMeeting(Meeting meeting);
}
