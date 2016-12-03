package ua.ukma.nc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.vo.GroupVo;

/**
 *
 * @author michael
 */
@Component
public class GroupFormValidator implements Validator{

    @Autowired
    GroupService groupService;
    
    @Override
    public boolean supports(Class<?> arg0) {
        return GroupVo.class.equals(arg0);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        GroupVo group = (GroupVo) arg0;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.groupForm.name");
        if ((group.getName() != null) && (group.getName().length() > 255)) {
            errors.rejectValue("name", "TooLong.groupForm.name");
        }
        if ((group.getName() != null) && (groupService.getByName(group.getName()) != null)) {
            errors.rejectValue("name", "NotAvailable.groupForm.name");
        }
    }
    
}
