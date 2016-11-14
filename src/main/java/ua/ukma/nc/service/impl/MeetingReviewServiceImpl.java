package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.MeetingReviewDao;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.service.MeetingReviewService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MeetingReviewServiceImpl implements MeetingReviewService{

    @Autowired
    private MeetingReviewDao meetingReviewDao;

    @Override
    public MeetingReview getById(Long id) {
        return meetingReviewDao.getById(id);
    }

    @Override
    public int deleteMeetingReview(MeetingReview meetingReview) {
        return meetingReviewDao.deleteMeetingReview(meetingReview);
    }

    @Override
    public int updateMeetingReview(MeetingReview meetingReview) {
        return meetingReviewDao.updateMeetingReview(meetingReview);
    }

    @Override
    public List<MeetingReview> getAll() {
        return meetingReviewDao.getAll();
    }

    @Override
    public int createMeetingReview(MeetingReview meetingReview) {
        return meetingReviewDao.createMeetingReview(meetingReview);
    }

	@Override
	public List<MeetingReview> getByProjectStudent(Long projectId, Long studentId) {
		return meetingReviewDao.getByProjectStudent(projectId, studentId);
	}

    @Override
    public MeetingReview getByMeetingStudent(Long meetingId, Long studentId) {
        return meetingReviewDao.getByMeetingStudent(meetingId, studentId);
    }
}
