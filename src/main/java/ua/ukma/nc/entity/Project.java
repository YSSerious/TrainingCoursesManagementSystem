package ua.ukma.nc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Project extends Serializable{
     Long getId();

     void setId(Long id);

     String getName();

     void setName(String name);

     String getDescription();

     void setDescription(String description);

     Timestamp getStartDate();

     void setStartDate(Timestamp startDate);

     Timestamp getFinishDate();

     void setFinishDate(Timestamp finishDate);
}
