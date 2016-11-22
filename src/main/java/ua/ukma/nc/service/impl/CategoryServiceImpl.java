package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.dao.CategoryDao;
import ua.ukma.nc.dao.CriterionDao;
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
    @Autowired
    private CriterionDao criterionDao;


    @Override
    public Category getById(Long id) {
        return categoryDao.getById(id);
    }

    @Override
    public Category getByName(String name) {
        return categoryDao.getByName(name);
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
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public void deleteCategory(Long id) {
        categoryDao.deleteCategory(id);
        criterionDao.deleteByCategoryId(id);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public int createCategory(Category category) {
        return categoryDao.createCategory(category);
    }

	@Override
	public List<Category> getByProjectId(Long projectId) {
		return categoryDao.getByProjectId(projectId);
	}

	@Override
	public List<Category> getByProjects(List<Long> projects) {
		return categoryDao.getByProjects(projects);
	}
}
