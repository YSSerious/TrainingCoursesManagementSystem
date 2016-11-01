package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.CategoryDao;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.service.CategoryService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category getById(Long id) {
        return categoryDao.getById(id);
    }

    @Override
    public boolean isExist(Category category) {
        return false;
    }

    @Override
    public int deleteCategory(Category category) {
        return 0;
    }

    @Override
    public int updateCategory(Category category) {
        return 0;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public int createCategory(Category category) {
        return 0;
    }
}
