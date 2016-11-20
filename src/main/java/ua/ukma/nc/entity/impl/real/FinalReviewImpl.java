package ua.ukma.nc.entity.impl.real;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;

import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public class FinalReviewImpl implements FinalReview {

    private static final long serialVersionUID = 975397601976126588L;
    private Long id;
    private Timestamp date;
    private User student;
    private User employee;
    private String type;
    private Project project;
    private String commentary;

    public FinalReviewImpl(Long id, Timestamp date, User student, User employee, String type, Project project, String commentary) {
        this.id = id;
        this.date = date;
        this.student = student;
        this.employee = employee;
        this.type = type;
        this.project = project;
        this.commentary = commentary;
    }

    public FinalReviewImpl(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalReviewImpl that = (FinalReviewImpl) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        return commentary != null ? commentary.equals(that.commentary) : that.commentary == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FinalReviewImpl{" +
                "id=" + id +
                ", date=" + date +
                ", student=" + student +
                ", employee=" + employee +
                ", type='" + type + '\'' +
                ", project=" + project +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
