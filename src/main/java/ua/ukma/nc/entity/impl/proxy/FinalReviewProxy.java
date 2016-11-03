package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;
import ua.ukma.nc.service.FinalReviewService;

import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public class FinalReviewProxy implements FinalReview {

    private static final long serialVersionUID = -7681458788626650265L;
    private Long id;
    private FinalReviewImpl finalReview;
    @Autowired
    private FinalReviewService finalReviewService;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Timestamp getDate() {
        downloadFinalReview();
        return finalReview.getDate();
    }

    @Override
    public void setDate(Timestamp date) {
        downloadFinalReview();
        finalReview.setDate(date);
    }

    @Override
    public User getStudent() {
        downloadFinalReview();
        return finalReview.getStudent();
    }

    @Override
    public void setStudent(User student) {
        downloadFinalReview();
        finalReview.setStudent(student);
    }

    @Override
    public User getEmployee() {
        downloadFinalReview();
        return finalReview.getEmployee();
    }

    @Override
    public void setEmployee(User employee) {
        downloadFinalReview();
        finalReview.setEmployee(employee);
    }

    @Override
    public String getType() {
        downloadFinalReview();
        return finalReview.getType();
    }

    @Override
    public void setType(String type) {
        downloadFinalReview();
        finalReview.setType(type);
    }

    @Override
    public Project getProject() {
        downloadFinalReview();
        return finalReview.getProject();
    }

    @Override
    public void setProject(Project project) {
        downloadFinalReview();
        finalReview.setProject(project);
    }

    @Override
    public String getCommentary() {
        downloadFinalReview();
        return finalReview.getCommentary();
    }

    @Override
    public void setCommentary(String commentary) {
        downloadFinalReview();
        finalReview.setCommentary(commentary);
    }

    private void downloadFinalReview() {
        if (finalReview == null) {
            finalReview = (FinalReviewImpl) finalReviewService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + id;
    }
}
