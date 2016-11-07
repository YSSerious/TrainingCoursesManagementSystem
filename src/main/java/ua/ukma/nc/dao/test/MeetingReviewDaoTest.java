package ua.ukma.nc.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.config.TestConfig;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.dao.MeetingReviewDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MeetingReviewDaoTest {

    @Autowired
    private MeetingReviewDao meetingReviewDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MeetingDao meetingDao;

    @Test
    public void getByIdTest() {
        MeetingReview meetingReview= meetingReviewDao.getById(1L);
        Assert.assertNotNull(meetingReview);
    }

    @Test
    public void getAllTest() {
        List<MeetingReview> meetingReviews= meetingReviewDao.getAll();
        Assert.assertNotNull(meetingReviews);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        MeetingReview meetingReview= meetingReviewDao.getById(1L);
        meetingReview.setCommentary("newComment");
        Assert.assertEquals(1, meetingReviewDao.updateMeetingReview(meetingReview));
        Assert.assertEquals(meetingReview.getCommentary(), meetingReviewDao.getById(meetingReview.getId()).getCommentary());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        MeetingReview meetingReview= meetingReviewDao.getById(1L);
        Assert.assertEquals(1, meetingReviewDao.deleteMeetingReview(meetingReview));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        MeetingReview meetingReview = new MeetingReviewImpl();
        User student = userDao.getById(50L);
        User mentor = userDao.getById(51L);
        Meeting meeting = meetingDao.getById(1L);
        meetingReview.setStudent(student);
        meetingReview.setMentor(mentor);
        meetingReview.setMeeting(meeting);
        meetingReview.setType("1");
        meetingReview.setCommentary("Commentary");
        Assert.assertEquals(1, meetingReviewDao.createMeetingReview(meetingReview));
    }

}
