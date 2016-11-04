package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Category;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CategoryDao {
    Category getById(Long id);

    boolean isExist(Category category);

    int deleteCategory(Category category);

    int updateCategory(Category category);

    List<Category> getAll();

    int createCategory(Category category);

	List<Category> getAllAjax();
}
