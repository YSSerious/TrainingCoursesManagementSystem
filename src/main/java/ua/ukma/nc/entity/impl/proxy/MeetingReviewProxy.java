package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;
import ua.ukma.nc.service.MeetingReviewService;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MeetingReviewProxy implements MeetingReview {

    private static final long serialVersionUID = -7649589179537002324L;
    private Long id;
    private MeetingReviewImpl meetingReview;
    @Autowired
    private MeetingReviewService meetingReviewService;

    public MeetingReviewProxy() {
    }

    public MeetingReviewProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public User getStudent() {
        downloadMeetingReview();
        return meetingReview.getStudent();
    }

    @Override
    public void setStudent(User student) {
        downloadMeetingReview();
        meetingReview.setStudent(student);
    }

    @Override
    public Meeting getMeeting() {
        downloadMeetingReview();
        return meetingReview.getMeeting();
    }

    @Override
    public void setMeeting(Meeting meeting) {
        downloadMeetingReview();
        meetingReview.setMeeting(meeting);
    }

    @Override
    public User getMentor() {
        downloadMeetingReview();
        return meetingReview.getMentor();
    }

    @Override
    public void setMentor(User mentor) {
        downloadMeetingReview();
        meetingReview.setMentor(mentor);
    }

    @Override
    public String getType() {
        downloadMeetingReview();
        return meetingReview.getType();
    }

    @Override
    public void setType(String type) {
        downloadMeetingReview();
        meetingReview.setType(type);
    }

    @Override
    public String getCommentary() {
        downloadMeetingReview();
        return meetingReview.getCommentary();
    }

    @Override
    public void setCommentary(String commentary) {
        downloadMeetingReview();
        meetingReview.setCommentary(commentary);
    }

    private void downloadMeetingReview() {
        if (meetingReview == null) {
            meetingReview = (MeetingReviewImpl) meetingReviewService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + id;
    }
}
