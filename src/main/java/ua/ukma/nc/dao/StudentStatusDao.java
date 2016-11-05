package ua.ukma.nc.dao;

import ua.ukma.nc.entity.StudentStatus;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface StudentStatusDao {

    StudentStatus getByUserId(Long id);

    int deleteStudentStatus(StudentStatus studentStatus);

    int updateStudentStatus(StudentStatus studentStatus);

    List<StudentStatus> getAll();

    int createStudentStatus(StudentStatus studentStatus);
}
