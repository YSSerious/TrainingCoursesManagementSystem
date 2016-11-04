package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.service.MeetingService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MeetingServiceImpl implements MeetingService{

    @Autowired
    private MeetingDao meetingDao;

    @Override
    public Meeting getById(Long id) {
        return meetingDao.getById(id);
    }

    @Override
    public int deleteMeeting(Meeting meeting) {
        return meetingDao.deleteMeeting(meeting);
    }

    @Override
    public int updateMeeting(Meeting meeting) {
        return meetingDao.updateMeeting(meeting);
    }

    @Override
    public List<Meeting> getAll() {
        return meetingDao.getAll();
    }

    @Override
    public int createMeeting(Meeting meeting) {
        return meetingDao.createMeeting(meeting);
    }
}
