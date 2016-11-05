package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.StudentStatusDao;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.service.StudentStatusService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class StudentStatusServiceImpl implements StudentStatusService{

    @Autowired
    private StudentStatusDao studentStatusDao;

    @Override
    public StudentStatus getByUserId(Long id) {
        return studentStatusDao.getByUserId(id);
    }

    @Override
    public int deleteStudentStatus(StudentStatus studentStatus) {
        return studentStatusDao.deleteStudentStatus(studentStatus);
    }

    @Override
    public int updateStudentStatus(StudentStatus studentStatus) {
        return studentStatusDao.updateStudentStatus(studentStatus);
    }

    @Override
    public List<StudentStatus> getAll() {
        return studentStatusDao.getAll();
    }

    @Override
    public int createStudentStatus(StudentStatus studentStatus) {
        return studentStatusDao.createStudentStatus(studentStatus);
    }
}
