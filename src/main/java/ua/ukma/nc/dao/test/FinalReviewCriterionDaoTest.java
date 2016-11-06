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
import ua.ukma.nc.dao.FinalReviewCriterionDao;
import ua.ukma.nc.dao.FinalReviewDao;
import ua.ukma.nc.dao.MarkDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.entity.Mark;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class FinalReviewCriterionDaoTest {

    @Autowired
    private FinalReviewCriterionDao finalReviewCriterionDao;
    @Autowired
    private FinalReviewDao finalReviewDao;
    @Autowired
    private CriterionDao criterionDao;
    @Autowired
    private MarkDao markDao;


    @Test
    public void getByIdTest() {
        FinalReviewCriterion finalReviewCriterion= finalReviewCriterionDao.getById(2L);
        Assert.assertNotNull(finalReviewCriterion);
    }

    @Test
    public void getAllTest() {
        List<FinalReviewCriterion> finalReviewCriterions = finalReviewCriterionDao.getAll();
        Assert.assertNotNull(finalReviewCriterions);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        FinalReviewCriterion finalReviewCriterion = finalReviewCriterionDao.getById(2L);
        finalReviewCriterion.setCommentary("newComment");
        Assert.assertEquals(1, finalReviewCriterionDao.updateFinalReviewCriterion(finalReviewCriterion));
        Assert.assertEquals(finalReviewCriterion.getCommentary(), finalReviewCriterionDao.getById(finalReviewCriterion.getId()).getCommentary());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        FinalReviewCriterion finalReviewCriterion = finalReviewCriterionDao.getById(2L);
        Assert.assertEquals(1, finalReviewCriterionDao.deleteFinalReviewCriterion(finalReviewCriterion));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        FinalReviewCriterion finalReviewCriterion= new FinalReviewCriterion();
        FinalReview finalReview = finalReviewDao.getById(5L);
        Criterion criterion = criterionDao.getById(1L);
        Mark mark = markDao.getByValue(1);
        finalReviewCriterion.setFinalReview(finalReview);
        finalReviewCriterion.setCriterion(criterion);
        finalReviewCriterion.setMark(mark);
        finalReviewCriterion.setCommentary("newComment");
        Assert.assertEquals(1, finalReviewCriterionDao.createFinalReviewCriterion(finalReviewCriterion));
    }

}
