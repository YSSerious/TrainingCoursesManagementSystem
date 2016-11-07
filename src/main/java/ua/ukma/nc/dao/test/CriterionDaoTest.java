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
import ua.ukma.nc.dao.CategoryDao;
import ua.ukma.nc.dao.CriterionDao;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.impl.real.CriterionImpl;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CriterionDaoTest {

    @Autowired
    private CriterionDao criterionDao;
    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void getByIdTest() {
        Criterion criterion= criterionDao.getById(1L);
        Assert.assertNotNull(criterion);
    }

    @Test
    public void getAllTest() {
        List<Criterion> criterions= criterionDao.getAll();
        Assert.assertNotNull(criterions);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Criterion criterion= criterionDao.getById(1L);
        criterion.setTitle("newTitle");
        Assert.assertEquals(1, criterionDao.updateCriterion(criterion));
        Assert.assertEquals(criterion.getTitle(), criterionDao.getById(criterion.getId()).getTitle());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Criterion criterion= criterionDao.getById(1L);
        Assert.assertEquals(1, criterionDao.deleteCriterion(criterion));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Category category= categoryDao.getById(1L);
        Criterion criterion= new CriterionImpl("newTitle");
        criterion.setCategory(category);
        Assert.assertEquals(1, criterionDao.createCriterion(criterion));
    }
}
