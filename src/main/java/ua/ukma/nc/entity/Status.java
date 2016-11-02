package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Status extends Serializable{
     Long getId();

     void setId(Long id);

     String getTitle();

     void setTitle(String title);

     String getDescription();

     void setDescription(String description);
}
