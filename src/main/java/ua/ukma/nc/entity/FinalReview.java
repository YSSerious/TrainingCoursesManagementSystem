package ua.ukma.nc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReview extends Serializable{
     Long getId();

     void setId(Long id);

     Timestamp getDate();

     void setDate(Timestamp date);

     User getStudent();

     void setStudent(User student);

     User getEmployee();

     void setEmployee(User employee);

     String getType();

     void setType(String type);

     Project getProject();

     void setProject(Project project);

     String getCommentary();

     void setCommentary(String commentary);
}
