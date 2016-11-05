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
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.impl.real.CategoryImpl;

import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CategoryDaoTest {
    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void getByIdTest() {
        Category category= categoryDao.getById(1L);
        Assert.assertNotNull(category);
    }

    @Test
    public void getAllTest() {
        List<Category> categories = categoryDao.getAll();
        Assert.assertNotNull(categories);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Category category = categoryDao.getById(1L);
        category.setName("newName");
        Assert.assertEquals(1, categoryDao.updateCategory(category));
        Assert.assertEquals(category.getName(), categoryDao.getById(category.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Category category = categoryDao.getById(1L);
        Assert.assertEquals(1, categoryDao.deleteCategory(category));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Category category = new CategoryImpl("newName","newDescription");
        Assert.assertEquals(1, categoryDao.createCategory(category));
    }
}
