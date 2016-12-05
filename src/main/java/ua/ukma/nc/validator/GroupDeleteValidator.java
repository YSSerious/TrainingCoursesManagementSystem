package ua.ukma.nc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.ukma.nc.dto.GroupDto;
import ua.ukma.nc.service.GroupService;

@Component
public class GroupDeleteValidator implements Validator {

    @Autowired
    GroupService groupService;

    @Override
    public boolean supports(Class<?> arg0) {
        return GroupDto.class.equals(arg0);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        GroupDto group = (GroupDto) arg0;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.groupForm.id");
        if ((group.getId() <= 0) || (group.getId() > 0) && (groupService.getById(group.getId()) == null)) {
                errors.reject("NotFound.groupForm.id");
            }
        if ((group.getId() > 0) && (groupService.getStudentsAmount(group.getId()) != 0)) {
                errors.reject("HasStudents.groupForm.general");
            }
        }
    }
