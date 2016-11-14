package ua.ukma.nc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Group extends Serializable{
     Long getId();

     void setId(Long id);

     Project getProject();

     void setProject(Project project);

     String getName();

     void setName(String name);

     List<User> getUsers();
     

     void setUsers(List<User> users);
}
