package ua.ukma.nc.service;

import ua.ukma.nc.entity.Category;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CategoryService {

    Category getById(Long id);

    Category getByName(String name);

    boolean isExist(Category category);

    int deleteCategory(Category category);

    void deleteCategory(Long id);

    int updateCategory(Category category);

    List<Category> getAll();
    
    List<Category> getByProjectId(Long projectId);

    int createCategory(Category category);
}
