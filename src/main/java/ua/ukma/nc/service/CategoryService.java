package ua.ukma.nc.service;

import ua.ukma.nc.entity.Category;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CategoryService {

    Category getById(Long id);

    boolean isExist(Category category);

    int deleteCategory(Category category);

    int updateCategory(Category category);

    List<Category> getAll();
    
    List<Category> getAllAjax();

    int createCategory(Category category);
}
