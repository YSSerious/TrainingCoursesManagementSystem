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
import ua.ukma.nc.dao.FinalReviewDao;
import ua.ukma.nc.dao.ProjectDao;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class FinalReviewDaoTest {

    @Autowired
    private FinalReviewDao finalReviewDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;

    @Test
    public void getByIdTest() {
        FinalReview finalReview= finalReviewDao.getById(5L);
        Assert.assertNotNull(finalReview);
    }

    @Test
    public void getAllTest() {
        List<FinalReview> finalReviews= finalReviewDao.getAll();
        Assert.assertNotNull(finalReviews);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        FinalReview finalReview= finalReviewDao.getById(5L);
        finalReview.setCommentary("newComment");
        Assert.assertEquals(1, finalReviewDao.updateFinalReview(finalReview));
        Assert.assertEquals(finalReview.getCommentary(), finalReviewDao.getById(finalReview.getId()).getCommentary());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        FinalReview finalReview= finalReviewDao.getById(5L);
        Assert.assertEquals(1, finalReviewDao.deleteFinalReview(finalReview));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        FinalReview finalReview= new FinalReviewImpl();
        User student = userDao.getById(41L);
        User empl = userDao.getById(42L);
        Project project = projectDao.getById(1L);
        finalReview.setStudent(student);
        finalReview.setEmployee(empl);
        finalReview.setType("1");
        finalReview.setProject(project);
        finalReview.setCommentary("newComment");
        finalReview.setDate(new Timestamp(1L));
        Assert.assertEquals(1, finalReviewDao.createFinalReview(finalReview));
    }
}
