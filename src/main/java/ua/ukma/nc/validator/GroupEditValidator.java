package ua.ukma.nc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.ukma.nc.dto.GroupDto;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.ProjectService;

@Component
public class GroupEditValidator implements Validator {

	@Autowired
	GroupService groupService;

	@Autowired
	ProjectService projectService;

	@Override
	public boolean supports(Class<?> arg0) {
		return GroupDto.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		GroupDto group = (GroupDto) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.groupForm.name");

		if ((group.getId() <= 0) || (group.getId() > 0) && (groupService.getById(group.getId()) == null)) {
			errors.reject("NotFound.groupForm.id");
		}
		if ((group.getName() != null) && (group.getName().length() > 255)) {
			errors.rejectValue("name", "TooLong.groupForm.name");
		}
		if ((group.getName() != null) && (groupService.getByName(group.getName()) != null)) {
			errors.rejectValue("name", "NotAvailable.groupForm.name");
		}
		if ((group.getProjectId() <= 0)
			|| (projectService.exist(group.getProjectId()) == false)) {
			errors.reject("NotFound.groupForm.project");
		}
	}

}
