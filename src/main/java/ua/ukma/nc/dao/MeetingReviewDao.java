package ua.ukma.nc.dao;

import ua.ukma.nc.entity.MeetingReview;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingReviewDao {
    MeetingReview getById(Long id);

    int deleteMeetingReview(MeetingReview meetingReview);

    int updateMeetingReview(MeetingReview meetingReview);

    List<MeetingReview> getAll();

    int createMeetingReview(MeetingReview meetingReview);

	List<MeetingReview> getByProjectStudent(Long projectId, Long studentId);
}
