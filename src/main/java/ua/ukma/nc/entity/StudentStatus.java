package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StudentStatus extends Serializable {

     Long getStatusId();

     void setStatusId(Long statusId);

     Long getStudentId();

     void setStudentId(Long studentId);

}
