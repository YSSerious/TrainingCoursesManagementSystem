package ua.ukma.nc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.ukma.nc.dto.ProjectDto;
import ua.ukma.nc.service.ProjectService;

@Component
public class ProjectFormValidator implements Validator {

	@Autowired
	ProjectService projectService;

	@Override
	public boolean supports(Class<?> arg0) {
		return ProjectDto.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ProjectDto project = (ProjectDto) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.projectForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty.projectForm.startDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "finishDate", "NotEmpty.projectForm.finishDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.projectForm.description");
		if ((project.getName() != null) && (project.getName().length() > 255)) {
			errors.rejectValue("name", "TooLong.projectForm.name");
		}
		if ((project.getDescription() != null) && (project.getDescription().length() > 255)) {
			errors.rejectValue("description", "TooLong.projectForm.description");
		}
		if ((project.getName() != null)
				&& (((project.getId() > 0) 
				&& !projectService.getByName(project.getName()).equals(projectService.getById(project.getId()))) 
				|| ((project.getId() <= 0) && (projectService.getByName(project.getName()) != null)))) {
			errors.rejectValue("name", "NotAvailable.projectForm.name");
		}
		if ((project.getStartDate() != null) && (project.getFinishDate() != null) && (project.getFinishDate().compareTo(project.getStartDate()) < 0)) {
			errors.rejectValue("finishDate", "NotValid.projectForm.finishDate");
		}
	}

}
