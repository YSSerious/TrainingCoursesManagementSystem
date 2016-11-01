package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.impl.real.StudentStatusImpl;
import ua.ukma.nc.service.StudentStatusService;

/**
 * Created by Алексей on 30.10.2016.
 */
public class StudentStatusProxy implements StudentStatus{

    private static final long serialVersionUID = 6690362285837763813L;

    private Long statusId;

    private StudentStatusImpl studentStatus;

    @Autowired
    private StudentStatusService studentStatusService;

    public StudentStatusProxy() {
    }

    public StudentStatusProxy(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public Long getStatusId() {
        return statusId;
    }

    @Override
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public Long getStudentId() {
        return studentStatus.getStudentId();
    }

    @Override
    public void setStudentId(Long studentId) {
        downloadStudentStatus();
        studentStatus.setStudentId(studentId);
    }

    private void downloadStudentStatus() {
        if (studentStatus == null) {
            studentStatus = (StudentStatusImpl) studentStatusService.getByStatusId(statusId);
        }
    }

    @Override
    public String toString() {
        return ""+statusId;
    }
}
