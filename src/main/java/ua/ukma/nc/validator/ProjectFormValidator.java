package ua.ukma.nc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.ProjectService;

@Component
public class ProjectFormValidator implements Validator {

	@Autowired
	ProjectService projectService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return ProjectImpl.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		Project project = (Project) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.projectForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty.projectForm.startDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "finishDate", "NotEmpty.projectForm.finishDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.projectForm.description");
		if ((project.getName() != null) && (projectService.getByName(project.getName()) != null)) {
			errors.rejectValue("name", "NotAvailable.projectForm.name");
		}
	}
	
}