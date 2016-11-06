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
import ua.ukma.nc.dao.CriterionDao;
import ua.ukma.nc.dao.MarkDao;
import ua.ukma.nc.dao.MeetingResultDao;
import ua.ukma.nc.dao.MeetingReviewDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MeetingResultDaoTest {

    @Autowired
    private MeetingResultDao meetingResultDao;
    @Autowired
    private CriterionDao criterionDao;
    @Autowired
    private MeetingReviewDao meetingReviewDao;
    @Autowired
    private MarkDao markDao;

    @Test
    public void getByIdTest() {
        MeetingResult meetingResult= meetingResultDao.getById(1L);
        Assert.assertNotNull(meetingResult);
    }

    @Test
    public void getAllTest() {
        List<MeetingResult> meetingResults= meetingResultDao.getAll();
        Assert.assertNotNull(meetingResults);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        MeetingResult meetingResult= meetingResultDao.getById(1L);
        meetingResult.setCommentary("newComment");
        Assert.assertEquals(1, meetingResultDao.updateMeetingResult(meetingResult));
        Assert.assertEquals(meetingResult.getCommentary(), meetingResultDao.getById(meetingResult.getId()).getCommentary());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        MeetingResult meetingResult= meetingResultDao.getById(1L);
        Assert.assertEquals(1, meetingResultDao.deleteMeetingResult(meetingResult));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        MeetingResult meetingResult= new MeetingResult();
        Criterion criterion = criterionDao.getById(1L);
        MeetingReview meetingReview= meetingReviewDao.getById(1L);
        Mark mark = markDao.getByValue(1);
        meetingResult.setMark(mark);
        meetingResult.setMeetingReview(meetingReview);
        meetingResult.setCriterion(criterion);
        meetingResult.setCommentary("Commentary");
        Assert.assertEquals(1, meetingResultDao.createMeetingResult(meetingResult));
    }
}
