package ua.ukma.nc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Role extends Serializable{
     Long getId();

     void setId(Long id);

     String getTitle();

     void setTitle(String title);

     List<User> getUsers();

     void setUsers(List<User> users);
}
