package ua.ukma.nc.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.ukma.nc.dao.impl.MeetingReviewDaoImpl;

import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.ProjectService;

@Component
public class ProjectFormValidator implements Validator {

    private static Logger log = LoggerFactory.getLogger(MeetingReviewDaoImpl.class.getName());
    
    @Autowired
    ProjectService projectService;

    @Override
    public boolean supports(Class<?> arg0) {
        return ProjectImpl.class.equals(arg0);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        log.info("validate before");
        System.out.println("validate before");
        Project project = (Project) arg0;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.projectForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty.projectForm.startDate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "finishDate", "NotEmpty.projectForm.finishDate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.projectForm.description");
        if ((project.getName() != null) && (project.getName().length() > 255)) {
            errors.rejectValue("name", "TooLong.projectForm.name");
        }
        if ((project.getDescription()!= null) && (project.getDescription().length() > 255)) {
            errors.rejectValue("description", "TooLong.projectForm.description");
        }
        if ((project.getName() != null) && (projectService.getByName(project.getName()) != null)) {
            errors.rejectValue("name", "NotAvailable.projectForm.name");
        }
        if ((project.getStartDate() != null) && (project.getFinishDate() != null) && (project.getFinishDate().compareTo(project.getStartDate()) < 0)) {
            errors.rejectValue("finishDate", "NotValid.projectForm.finishDate");
        }
    }

}
