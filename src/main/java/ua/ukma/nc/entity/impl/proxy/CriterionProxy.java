package ua.ukma.nc.entity.impl.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.CriterionImpl;
import ua.ukma.nc.service.CriterionService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Component
@Scope("prototype")
public class CriterionProxy implements Criterion{

    private static final long serialVersionUID = 8371761992165080298L;
    private Long id;
    private CriterionImpl criterion;
    private CriterionService criterionService;

    public CriterionProxy() {
    }

    public CriterionProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public String getTitle() {
        downloadCriterion();
        return criterion.getTitle();
    }

    @Override
    public void setTitle(String title) {
        downloadCriterion();
        criterion.setTitle(title);
    }

    @Override
    public Category getCategory() {
        downloadCriterion();
        return criterion.getCategory();
    }

    @Override
    public void setCategory(Category category) {
        downloadCriterion();
        criterion.setCategory(category);
    }

    @Override
    public List<Project> getProjectList() {
        downloadCriterion();
        return criterion.getProjectList();
    }

    @Override
    public void setProjectList(List<Project> projectList) {
        downloadCriterion();
        criterion.setProjectList(projectList);
    }

    private void downloadCriterion() {
        if (criterion == null) {
            criterion = (CriterionImpl) criterionService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy "+id;
    }
}
