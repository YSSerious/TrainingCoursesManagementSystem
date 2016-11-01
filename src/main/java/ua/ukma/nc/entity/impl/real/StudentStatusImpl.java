package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.StudentStatus;

/**
 * Created by Алексей on 30.10.2016.
 */
public class StudentStatusImpl implements StudentStatus{

    private static final long serialVersionUID = 168691225977399727L;
    private Long statusId;
    private Long studentId;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentStatusImpl that = (StudentStatusImpl) o;

        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        return studentId != null ? studentId.equals(that.studentId) : that.studentId == null;

    }

    @Override
    public int hashCode() {
        int result = statusId != null ? statusId.hashCode() : 0;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentStatusImpl{" +
                "statusId=" + statusId +
                ", studentId=" + studentId +
                '}';
    }
}
