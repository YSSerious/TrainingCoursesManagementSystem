package ua.ukma.nc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Criterion extends Serializable{
     Long getId();

     void setId(Long id);

     String getTitle();

     void setTitle(String title);

     Category getCategory();

     void setCategory(Category category);

      List<Project> getProjectList();

      void setProjectList(List<Project> projectList);
}
