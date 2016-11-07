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
import ua.ukma.nc.dao.MarkDao;
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.entity.impl.real.MarkImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MarkDaoTest {

    @Autowired
    private MarkDao markDao;

    @Test
    public void getByIdTest() {
        Mark mark= markDao.getByValue(5);
        Assert.assertNotNull(mark);
    }

    @Test
    public void getAllTest() {
        List<Mark> marks = markDao.getAll();
        Assert.assertNotNull(marks);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Mark mark = markDao.getByValue(5);
        mark.setDescription("newDescription");
        Assert.assertEquals(1, markDao.updateMark(mark));
        Assert.assertEquals(mark.getDescription(), markDao.getByValue(mark.getValue()).getDescription());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Mark mark = markDao.getByValue(5);
        Assert.assertEquals(1, markDao.deleteMark(mark));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Mark mark = new MarkImpl(10,"BOG!");
        Assert.assertEquals(1, markDao.createMark(mark));
    }
}
