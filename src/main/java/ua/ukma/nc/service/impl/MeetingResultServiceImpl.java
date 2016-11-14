package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.MeetingResultDao;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.StudentMeetingReview;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.StudentStatusLog;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.StatusLogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MeetingResultServiceImpl implements MeetingResultService{
    @Autowired
    private MeetingResultDao meetingResultDao;
    
    @Autowired
    private StatusLogService statusLogService;
    
    @Autowired
    private MeetingReviewService meetingReviewService;
    
    @Override
    public MeetingResult getById(Long id) {
        return meetingResultDao.getById(id);
    }

    @Override
    public int deleteMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.deleteMeetingResult(meetingResult);
    }

    @Override
    public int updateMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.updateMeetingResult(meetingResult);
    }

    @Override
    public List<MeetingResult> getAll() {
        return meetingResultDao.getAll();
    }

    @Override
    public int createMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.createMeetingResult(meetingResult);
    }

	@Override
	public List<MarkInformation> generateMarkInformation(long studentId, long projectId) {
		return meetingResultDao.generateMarkInformation(studentId, projectId);
	}

    @Override
    public List<MeetingResult> getByReview(long review) {
        return meetingResultDao.getByReview(review);
    }


}
