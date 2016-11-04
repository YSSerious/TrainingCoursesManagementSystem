package ua.ukma.nc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public class StatusLog implements Serializable{

    private static final long serialVersionUID = 4974305203490266410L;
    private Long id;
    private Status oldStatus;
    private Status newStatus;
    private String commentary;
    private User student;
    private User employee;
    private Timestamp date;
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Status oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusLog statusLog = (StatusLog) o;

        if (id != null ? !id.equals(statusLog.id) : statusLog.id != null) return false;
        if (oldStatus != null ? !oldStatus.equals(statusLog.oldStatus) : statusLog.oldStatus != null) return false;
        if (newStatus != null ? !newStatus.equals(statusLog.newStatus) : statusLog.newStatus != null) return false;
        if (commentary != null ? !commentary.equals(statusLog.commentary) : statusLog.commentary != null) return false;
        if (student != null ? !student.equals(statusLog.student) : statusLog.student != null) return false;
        if (employee != null ? !employee.equals(statusLog.employee) : statusLog.employee != null) return false;
        if (date != null ? !date.equals(statusLog.date) : statusLog.date != null) return false;
        return group != null ? group.equals(statusLog.group) : statusLog.group == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (oldStatus != null ? oldStatus.hashCode() : 0);
        result = 31 * result + (newStatus != null ? newStatus.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatusLog{" +
                "id=" + id +
                ", oldStatus=" + oldStatus +
                ", newStatus=" + newStatus +
                ", commentary='" + commentary + '\'' +
                ", student=" + student +
                ", employee=" + employee +
                ", date=" + date +
                ", group=" + group +
                '}';
    }
}
