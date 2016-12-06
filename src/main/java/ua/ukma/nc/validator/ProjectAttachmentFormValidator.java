package ua.ukma.nc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.ukma.nc.dto.ProjectAttachmentFormDto;

@Component
public class ProjectAttachmentFormValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return ProjectAttachmentFormDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		ProjectAttachmentFormDto attachment = (ProjectAttachmentFormDto) obj;
		if (attachment.getFile() == null || attachment.getFile().getSize() == 0) {
			errors.rejectValue("file", "missing.file");
		} else if (attachment.getFile().getSize() >= 5242880l) {
			errors.rejectValue("file", "fail.size");
		} else if (attachment.getFile().getOriginalFilename() == null
				|| attachment.getFile().getOriginalFilename().length() > 50) {
			errors.rejectValue("file", "fail.file.name");
		}

		if (attachment.getName() == null || attachment.getName().isEmpty()) {
			errors.rejectValue("name", "missing.name");
		} else if (attachment.getName().length() > 50) {
			errors.rejectValue("name", "fail.name");
		}

		if (attachment.getProjectId() == null || attachment.getProjectId() <= 0) {
			errors.rejectValue("project", "missing.project");
		}

	}
}