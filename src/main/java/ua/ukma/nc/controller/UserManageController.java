package ua.ukma.nc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.UserService;
import ua.ukma.nc.util.exception.RoleManageException;
import ua.ukma.nc.util.exception.StatusSwitchException;

@Controller
public class UserManageController {
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());
	
	@Autowired  
    private MessageSource messageSource;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@ExceptionHandler(StatusSwitchException.class)
	public ModelAndView handleStatusSwitchException(StatusSwitchException ex) {
		log.error("StatusSwitch("+ex.getUserId()+"): "+ex.getErrMsg());
		
		ModelAndView model = new ModelAndView("error/statusSwitch");
		model.addObject("userId", ex.getUserId());
		model.addObject("errMsg", ex.getErrMsg());

		return model;
	}
	
	@ExceptionHandler(RoleManageException.class)
	public ModelAndView handleRoleManageException(RoleManageException ex) {
		log.error("RoleManage("+ex.getUserId()+"): "+ex.getErrMsg());
		
		ModelAndView model = new ModelAndView("error/statusSwitch");
		model.addObject("userId", ex.getUserId());
		model.addObject("errMsg", ex.getErrMsg());
		model.addObject("title", "User Management");
		return model;
	}
	
	@RequestMapping(value = "/manageRoles", method = RequestMethod.POST)
	public ModelAndView changeRoles(@RequestParam("student") Long student, @RequestParam("roles") List<Long> chRoles){
		User user = userService.getById(student);
		
		if(chRoles.size() == 0)
			throw new RoleManageException(student, messageSource.getMessage("error.role.zero", null, LocaleContextHolder.getLocale()));
		
		roleService.changeRoles(chRoles, user);
		
		return new ModelAndView("redirect:" + "users/"+student);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public ModelAndView changeStatus(@PathVariable("id") Long id, @RequestParam("commentary") String commentary,
			@RequestParam("status") Long statusId) throws Exception{

		ModelAndView model = new ModelAndView("user");

		userService.changeStatus(id, statusId, commentary);
		
		User user = userService.getById(id);
		UserDto userDto = new UserDto(user);
		
		model.addObject("success", messageSource.getMessage("status.success",
                null, LocaleContextHolder.getLocale()));
		model.addObject("user", userDto);
		model.addObject("title", userDto.getLastName()+" "+userDto.getFirstName());
		model.addObject("roles", roleService.getRolesDto(user));
		
		return model;
	}
}
