package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.exception.StatusSwitchException;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.UserService;

@Controller
public class StatusSwitch {
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());
	
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
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		log.error(ex.getMessage());
		return new ModelAndView("error/defaultError");
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public ModelAndView changeStatus(@PathVariable("id") Long id, @RequestParam("commentary") String commentary,
			@RequestParam("status") Long statusId) throws Exception{

		ModelAndView model = new ModelAndView();

		userService.changeStatus(id, statusId, commentary);
		
		User user = userService.getById(id);
		UserDto userDto = new UserDto(user);
		
		model.addObject("success", "Successfully changed!");
		model.addObject("user", userDto);
		model.addObject("title", userDto.getLastName()+" "+userDto.getFirstName());
		model.addObject("roles", roleService.getRolesDto(user));
		
		return model;
	}
}
