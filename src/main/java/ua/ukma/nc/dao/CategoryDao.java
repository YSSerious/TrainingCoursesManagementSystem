package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Category;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CategoryDao {
    Category getById(Long id);

    Category getByName(String name);

    boolean isExist(Category category);

    int deleteCategory(Category category);

    int deleteCategory(Long id);

    int updateCategory(Category category);

    List<Category> getAll();
    
    List<Category> getByProjectId(Long projectId);
    
    List<Category> getByProjects(List<Long> projects);

    int createCategory(Category category);
}
